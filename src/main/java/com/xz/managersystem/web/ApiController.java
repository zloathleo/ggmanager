package com.xz.managersystem.web;

import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.entity.*;
import com.xz.managersystem.service.YmglService;
import com.xz.managersystem.service.SbglService;
import com.xz.managersystem.service.RmsgService;
import com.xz.managersystem.service.ZyglService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SbglService sbglService;

    @Autowired
    YmglService ymglService;

    @Autowired
    RmsgService rmsgService;

    @Autowired
    ZyglService zyglService;

}
