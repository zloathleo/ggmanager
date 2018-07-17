package com.xz.managersystem.web;

import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.req.TChangePswdReq;
import com.xz.managersystem.dto.req.TLoginReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.dto.res.TUserDto;
import com.xz.managersystem.entity.BasicEntity;
import com.xz.managersystem.entity.TGroupInfo;
import com.xz.managersystem.entity.TUserInfo;
import com.xz.managersystem.service.GroupService;
import com.xz.managersystem.service.UserService;
import com.xz.managersystem.web.resolver.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.acl.Group;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/account")
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity login(@Valid TLoginReq loginReq) {
        return userService.login(loginReq);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes logout(@Authorization TUserInfo userInfo) {
        userService.logout(userInfo.getName());
        return new BasicRes("登出成功");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes updateUser(@Authorization TUserInfo userInfo,
                                @Valid TChangePswdReq changePswdReq) {
        userService.changePassword(userInfo.getName(), changePswdReq);
        return new BasicRes("修改密码成功");
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getInfo(@Authorization TUserInfo userInfo) {
        Map<String, Object> mapInfo = new HashMap<>();
        TGroupInfo groupInfo = groupService.findGroup(userInfo.getGroup());
        mapInfo.put("group", groupInfo);
        return mapInfo;
    }
}
