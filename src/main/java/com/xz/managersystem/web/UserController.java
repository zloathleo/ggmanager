package com.xz.managersystem.web;

import com.xz.managersystem.dto.req.TUserListReq;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getUserList(@Authorization TUserInfo userInfo,
                                    @Valid TUserListReq tr) {
        int userCount = userService.getCount(tr);
        List<TUserInfo> userInfoList = userService.getUserList(tr);
        List<TUserDto> userDtoList = userInfoList.stream().map(userItem -> {
            TGroupInfo groupInfo = groupService.getGroup2(userItem.getGroup());
            TUserDto userDto = new TUserDto();
            userDto.setId(userItem.getId());
            userDto.setName(userItem.getName());
            userDto.setType(userItem.getType());
            userDto.setUpdateTime(userItem.getUpdateTime());
            userDto.setGroup(groupInfo != null ? groupInfo.getName() : "");
            return userDto;
        }).collect(Collectors.toList());
        return new BasicTableRes<>(userCount, userDtoList);
    }

    @RequestMapping(value = "/{user}", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getUser(@Authorization TUserInfo userInfo,
                                @PathVariable("user") String user) {
        TUserInfo userItem = userService.getUser(user);
        TUserDto userDto = new TUserDto();
        TGroupInfo groupInfo = groupService.getGroup2(userItem.getGroup());
        userDto.setId(userItem.getId());
        userDto.setName(userItem.getName());
        userDto.setType(userItem.getType());
        userDto.setUpdateTime(userItem.getUpdateTime());
        userDto.setGroup(groupInfo != null ? groupInfo.getName() : "");
        return userDto;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes addUser(@Authorization TUserInfo userInfo,
                             @Valid TUserDto userDto){
        userService.addUser(userDto);
        return new BasicRes("添加用户成功");
    }

    @RequestMapping(value = "/{user}/reset", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes resetUser(@Authorization TUserInfo userInfo,
                               @PathVariable("user") String user){
        if ("admin".equalsIgnoreCase(user)) {
            throw new RuntimeException("Admin不能重置自身密码");
        }
        userService.resetPassword(user);
        return new BasicRes("重置用户密码成功");
    }

    @RequestMapping(value = "/{user}/delete", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes deleteUser(@Authorization TUserInfo userInfo,
                                @PathVariable("user") String user){
        userService.deleteUser(user);
        return new BasicRes("删除用户成功");
    }
}
