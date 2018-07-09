package com.xz.managersystem.web;

import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.req.TLoginReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.dto.res.TUserDto;
import com.xz.managersystem.entity.BasicEntity;
import com.xz.managersystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    private BasicEntity getUserList(@RequestParam(name = "token", required = false) String token,
                                    @Valid BasicTableReq tr) {
        int userCount = userService.getCount(tr.getType());
        List<TUserDto> userDtoList = userService.getUserList(tr);
        return new BasicTableRes<>(userCount, userDtoList);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private BasicRes addUser(@RequestParam(name = "token", required = false) String token,
                             @Valid TUserDto userDto){
        userService.addUser(userDto);
        return new BasicRes("添加用户成功");
    }

    @RequestMapping(value = "/{user}/update", method = RequestMethod.GET)
    private BasicRes getUser(@RequestParam(name = "token", required = false) String token,
                             @Valid TUserDto userDto){
        userService.updateUser(userDto);
        return new BasicRes("修改用户成功");
    }

    @RequestMapping(value = "/{user}/delete", method = RequestMethod.POST)
    private BasicRes deleteUser(@PathVariable("user") String user){
        userService.deleteUser(user);
        return new BasicRes("删除用户成功");
    }

    @RequestMapping(value = "/{user}/login", method = RequestMethod.POST)
    private BasicRes login(@PathVariable("name") String user,
                         @RequestParam(name = "password") String password,
                         @RequestParam(name = "token", required = false) String token) {
        TLoginReq req = new TLoginReq();
        req.setUser(user);
        req.setPassword(password);
        userService.login(req);
        return new BasicRes("登录成功");
    }
}
