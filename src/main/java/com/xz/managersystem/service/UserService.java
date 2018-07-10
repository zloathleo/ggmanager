package com.xz.managersystem.service;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dao.UserMapper;
import com.xz.managersystem.dao.GroupMapper;
import com.xz.managersystem.dto.req.TChangePswdReq;
import com.xz.managersystem.dto.req.TLoginReq;
import com.xz.managersystem.dto.req.TUserListReq;
import com.xz.managersystem.dto.res.TLoginRes;
import com.xz.managersystem.dto.res.TUserDto;
import com.xz.managersystem.entity.TUserInfo;
import com.xz.managersystem.entity.TGroupInfo;
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
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    GroupMapper groupMapper;

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    private Map<String, String> tokenMap = new HashMap<>();
    private Map<String, String> userMap = new HashMap<>();

    public int getCount(TUserListReq tr) {
        if (tr.getFilterGroup() != null && tr.getFilterGroup()) {
            return userMapper.getFilterCount();
        } else if (StringUtils.isBlank(tr.getType()) || "all".equalsIgnoreCase(tr.getType())) {
            return userMapper.getCount();
        } else {
            return userMapper.getTypeCount(tr.getType());
        }
    }

    public List<TUserInfo> getUserList(TUserListReq tr){
        ConditionParams pageParams = new ConditionParams();
        if (tr.getRows() != null && tr.getPage() != null) {
            pageParams.setStart((tr.getPage() - 1) * tr.getRows());
            pageParams.setRows(tr.getRows());
        }
        pageParams.setType(tr.getType());

        if (tr.getFilterGroup() != null && tr.getFilterGroup()) {
            if (tr.getRows() != null && tr.getPage() != null) {
                return userMapper.selectFilterPage(pageParams);
            } else {
                return userMapper.selectFilterList();
            }
        } else if (tr.getRows() != null && tr.getPage() != null) {
            if (tr.getType() == null || "all".equalsIgnoreCase(tr.getType())) {
                return userMapper.selectPage(pageParams);
            } else {
                return userMapper.selectTypePage(pageParams);
            }
        } else {
            if (tr.getType() == null || "all".equalsIgnoreCase(tr.getType())) {
                return userMapper.selectList();
            } else {
                return userMapper.selectTypeList(tr.getType());
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

    public void addUser(TUserDto userDto) {
        TUserInfo userInfo = userMapper.selectByName(userDto.getName());
        if (userInfo != null) {
            throw new EntityExistsException("用户" + userDto.getName() + "已存在");
        }

        TUserInfo userNew = new TUserInfo();
        userNew.setName(userDto.getName());
        userNew.setPassword(MD5(userDto.getName()));
        userNew.setType(userDto.getType() == null ? "operator" : userDto.getType());
        userNew.setGroup(userDto.getGroup() == null ? "" : userDto.getGroup());
        if (userMapper.insert(userNew) <= 0) {
            throw new RuntimeException("添加用户失败");
        }
    }

    public void updateUser(TUserInfo userInfo) {
        TUserInfo userBase = getUser(userInfo.getName());
        if (userInfo.getPassword() != null)
            userBase.setPassword(userInfo.getPassword());
        if (userInfo.getGroup() != null)
            userBase.setGroup(userInfo.getGroup());
        if (userMapper.updateByName(userBase) <= 0) {
            throw new RuntimeException("更新用户" + userBase.getName() + "失败");
        }
    }

    public void deleteUser(String name) {
        TUserInfo userInfo = getUser(name);
        if (userMapper.deleteByName(name) <= 0) {
            throw new RuntimeException("删除用户" + name + "失败");
        }
    }

    public TLoginRes login(TLoginReq loginReq) {
        TUserInfo userInfo = userMapper.selectByName(loginReq.getName());
        if (userInfo == null || !userInfo.getPassword().equalsIgnoreCase(loginReq.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        String token = MD5(loginReq.getName() + new Date().getTime());
        tokenMap.put(token, userInfo.getName());
        userMap.put(userInfo.getName(), token);
        return new TLoginRes(token, userInfo.getType(), userInfo.getGroup());
    }

    public void logout(String name) {
        String token = userMap.get(name);
        userMap.remove(name);
        if (token != null)
            tokenMap.remove(token);
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
        if (userInfo.getPassword().equalsIgnoreCase(changePswdReq.getOldPassword())) {
            throw new RuntimeException("密码错误");
        }

        userInfo.setPassword(changePswdReq.getNewPassword());
        updateUser(userInfo);
    }

    public void assignGroup(String name, String group) {
        TUserInfo userInfo = new TUserInfo();
        userInfo.setName(name);
        userInfo.setGroup(group);
        updateUser(userInfo);
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
