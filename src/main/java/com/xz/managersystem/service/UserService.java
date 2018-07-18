package com.xz.managersystem.service;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dao.UserMapper;
import com.xz.managersystem.dto.req.TChangePswdReq;
import com.xz.managersystem.dto.req.TLoginReq;
import com.xz.managersystem.dto.res.TLoginRes;
import com.xz.managersystem.entity.TUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.security.MessageDigest;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    GroupService groupService;

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    private Map<String, String> tokenMap = new HashMap<>();
    private Map<String, String> userMap = new HashMap<>();

    public int getCount(ConditionParams params) {
        if (params.getFilter() != null && params.getFilter()) {
            return userMapper.getFilterCount();
        } else if (StringUtils.isBlank(params.getType())) {
            return userMapper.getCount();
        } else {
            return userMapper.getTypeCount(params.getType());
        }
    }

    public List<TUserInfo> getUserList(ConditionParams params){
        if (params.getFilter() != null && params.getFilter()) {
            if (params.getRows() != null && params.getStart() != null) {
                return userMapper.selectFilterPage(params);
            } else {
                return userMapper.selectFilterList();
            }
        } else if (params.getRows() != null && params.getStart() != null) {
            if (StringUtils.isBlank(params.getType())) {
                return userMapper.selectPage(params);
            } else {
                return userMapper.selectTypePage(params);
            }
        } else {
            if (StringUtils.isBlank(params.getType())) {
                return userMapper.selectList();
            } else {
                return userMapper.selectTypeList(params.getType());
            }
        }
    }

    public TUserInfo getUser(String name){
        if (StringUtils.isBlank(name)) {
            throw new RuntimeException("无效的用户名");
        }

        TUserInfo userInfo = userMapper.selectByName(name);
        if (userInfo == null){
            throw new EntityNotFoundException("用户" + name + "不存在");
        }
        return userInfo;
    }

    public void addUser(TUserInfo userInfo) {
        TUserInfo userExist = userMapper.selectByName(userInfo.getName());
        if (userExist != null) {
            throw new EntityExistsException("用户" + userInfo.getName() + "已存在");
        }

        userInfo.setPassword(MD5(userInfo.getName()));
        if (StringUtils.isBlank(userInfo.getType())) {
            userInfo.setType("operator");
        }
        if (userInfo.getGroup() == null) {
            userInfo.setGroup("");
        }
        if ("admin".equalsIgnoreCase(userInfo.getType()) && StringUtils.isNotBlank(userInfo.getGroup())) {
            throw new RuntimeException("不能为管理用户分配组");
        }

        if (StringUtils.isNotBlank(userInfo.getGroup())) {
            groupService.assignUser(userInfo.getGroup(), userInfo.getName());
        }
        if (userMapper.insert(userInfo) <= 0) {
            throw new RuntimeException("添加用户失败");
        }
    }

    public void updateUser(TUserInfo userInfo) {
        TUserInfo userBase = getUser(userInfo.getName());
        Boolean relogin = false;
        if (userInfo.getGroup() != null) {
            if ("admin".equalsIgnoreCase(userBase.getType()) && StringUtils.isNotBlank(userInfo.getGroup())) {
                throw new RuntimeException("不能为管理用户分配组");
            }

            if (!userBase.getGroup().equalsIgnoreCase(userInfo.getGroup())) {
                if (StringUtils.isNotBlank(userBase.getGroup())) {
                    groupService.assignUser(userBase.getGroup(), "");
                }
                if (StringUtils.isNotBlank(userInfo.getGroup())) {
                    groupService.assignUser(userInfo.getGroup(), userInfo.getName());
                }
                userBase.setGroup(userInfo.getGroup());
            }
        }

        if (userInfo.getPassword() != null && !userBase.getPassword().equalsIgnoreCase(userInfo.getPassword())) {
            userBase.setPassword(userInfo.getPassword());
            relogin = true;
        }

        if (userMapper.updateByName(userBase) <= 0) {
            throw new RuntimeException("更新用户" + userBase.getName() + "失败");
        }

        if (relogin) {
            removeToken(userInfo.getName());
        }
    }

    public void deleteUser(String name) {
        if ("admin".equalsIgnoreCase(name)) {
            throw new RuntimeException("用户admin不能被删除");
        }
        TUserInfo userInfo = getUser(name);
        if (StringUtils.isNotBlank(userInfo.getGroup())) {
            groupService.assignUser(userInfo.getGroup(), "");
        }
        if (userMapper.deleteByName(name) <= 0) {
            throw new RuntimeException("删除用户" + name + "失败");
        }
        removeToken(name);
    }

    public TLoginRes login(TLoginReq loginReq) {
        TUserInfo userInfo = userMapper.selectByName(loginReq.getName());
        if (userInfo == null || !userInfo.getPassword().equalsIgnoreCase(loginReq.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        if ("operator".equalsIgnoreCase(userInfo.getType()) && StringUtils.isBlank(userInfo.getGroup())) {
            throw new RuntimeException("用户未分配组");
        }

//        removeToken(userInfo.getName());
//        String token = MD5(loginReq.getName() + new Date().getTime());
//        tokenMap.put(token, userInfo.getName());
//        userMap.put(userInfo.getName(), token);
        String token;
        if (userMap.containsKey(userInfo.getName())) {
            token = userMap.get(userInfo.getName());
        } else {
            token = MD5(loginReq.getName() + new Date().getTime());
            tokenMap.put(token, userInfo.getName());
            userMap.put(userInfo.getName(), token);
        }
        return new TLoginRes(userInfo.getName(), token, userInfo.getType(), userInfo.getGroup());
    }

    public void logout(String name) {
        removeToken(name);
    }

    public TUserInfo getUserByToken(String token) {
        String user = tokenMap.get(token);
        return userMapper.selectByName(user);
    }

    public void resetPassword(String name) {
        TUserInfo userInfo = new TUserInfo();
        userInfo.setName(name);
        userInfo.setPassword(MD5(name));
        updateUser(userInfo);
    }

    public void changePassword(String name, TChangePswdReq changePswdReq){
        TUserInfo userInfo = getUser(name);
        if (!userInfo.getPassword().equalsIgnoreCase(changePswdReq.getOldpassword())) {
            throw new RuntimeException("密码错误");
        }

        userInfo.setPassword(changePswdReq.getNewpassword());
        updateUser(userInfo);
    }

    public void assignGroup(String name, String group) {
        TUserInfo userInfo = getUser(name);
        if (StringUtils.isNotBlank(userInfo.getGroup()) &&
                StringUtils.isNotBlank(group) &&
                !group.equalsIgnoreCase(userInfo.getGroup())) {
            throw new RuntimeException("用户" + name + "已有分组");
        }

        userInfo.setGroup(group);
        if (userMapper.updateByName(userInfo) <= 0) {
            throw new RuntimeException("更新用户" + userInfo.getName() + "失败");
        }
    }

    public TUserInfo findUser(String name) {
        return userMapper.selectByName(name);
    }

    public Map<String, String> getTokenMap() {
        return tokenMap;
    }

    public Map<String, String> getUserMap() {
        return userMap;
    }

    private void removeToken(String name){
        String token = userMap.get(name);
        userMap.remove(name);
        if (token != null)
            tokenMap.remove(token);
    }

    private String MD5(String source) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}
