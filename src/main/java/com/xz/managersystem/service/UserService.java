package com.xz.managersystem.service;

import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.dao.UserMapper;
import com.xz.managersystem.dao.GroupMapper;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.req.TLoginReq;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.dto.res.TLoginRes;
import com.xz.managersystem.dto.res.TUserDto;
import com.xz.managersystem.entity.TUserInfo;
import com.xz.managersystem.entity.TGroupInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private Map<String, TUserInfo> tokenMap = new HashMap<>();

    public int getCount(String type) {
        if (type == null || type.isEmpty() || "all".equalsIgnoreCase(type)) {
            return userMapper.getCount();
        } else {
            return userMapper.getTypeCount(type);
        }
    }

    public TUserDto getUser(String user){
        TUserInfo userInfo = userMapper.selectByName(user);
        if (userInfo == null){
            throw new EntityNotFoundException("用户" + user + "不存在");
        }

        TUserDto userDto = new TUserDto();
        TGroupInfo groupInfo = groupMapper.selectByName(userInfo.getUser());
        userDto.setId(userInfo.getId());
        userDto.setUser(userInfo.getUser());
        userDto.setUpdateTime(userInfo.getUpdateTime());
        if (groupInfo != null){
            userDto.setGroup(groupInfo.getLabel());
        }
        return userDto;
    }

    public List<TUserDto> getUserList(BasicTableReq tr){
        List<TUserInfo> userInfoList;
        TablePageParams pageParams = new TablePageParams();
        pageParams.setStart((tr.getPage() - 1) * tr.getRows());
        pageParams.setRows(tr.getRows());
        pageParams.setType(tr.getType());
        if (tr.getType() == null || "all".equalsIgnoreCase(tr.getType())) {
            userInfoList = userMapper.selectPage(pageParams);
        } else {
            userInfoList = userMapper.selectTypePage(pageParams);
        }

        List<TUserDto> userDtoList = userInfoList.stream().map(userInfo -> {
            TGroupInfo groupInfo = groupMapper.selectByName(userInfo.getUser());
            TUserDto userDto = new TUserDto();
            userDto.setId(userInfo.getId());
            userDto.setUser(userInfo.getUser());
            if (groupInfo != null){
                userDto.setGroup(groupInfo.getLabel());
            }
            return userDto;
        }).collect(Collectors.toList());
        return userDtoList;
    }

    public int addUser(TUserDto userDto) {
        TUserInfo userInfo = userMapper.selectByName(userDto.getUser());
        if (userInfo != null) {
            throw new EntityExistsException("用户" + userDto.getUser() + "已存在");
        }

        TUserInfo userNew = new TUserInfo();
        userNew.setUser(userDto.getUser());
        userNew.setPassword(MD5(userDto.getUser()));
        userNew.setGroup(userDto.getGroup());
        return userMapper.insert(userNew);
    }

    public int updateUser(TUserDto userDto) {
        return 1;
    }

    public int deleteUser(String user) {
        TUserInfo userInfo = userMapper.selectByName(user);
        if (userInfo == null) {
            throw new EntityNotFoundException("用户" + user + "不存在");
        }
        return userMapper.deleteByName(user);
    }

    public TLoginRes login(TLoginReq loginReq) {
        TUserInfo userInfo = userMapper.selectByName(loginReq.getUser());
        if (userInfo == null || userInfo.getPassword() != loginReq.getPassword()) {
            throw new RuntimeException("用户名或密码错误");
        }
        String md5 = MD5(loginReq.getUser() + new Date().getTime());
        tokenMap.put(md5, userInfo);
        return new TLoginRes(md5, userInfo.getType());
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
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}
