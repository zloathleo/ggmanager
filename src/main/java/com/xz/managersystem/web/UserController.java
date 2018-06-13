package com.xz.managersystem.web;

import com.xz.managersystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user") // url:/模块/资源/{id}/细分 /seckill/list
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private String list(Model model) {
        Integer count = userService.getCount();
        logger.info("aaa:" + count);
        model.addAttribute("count", count);
        // list.jsp + model = ModelAndView
        // WEB-INF/jsp/"list".jsp
        return "index";
    }

}
