package com.xz.managersystem.web;

import com.xz.managersystem.entity.TDevice;
import com.xz.managersystem.entity.TGgym;
import com.xz.managersystem.service.GgymService;
import com.xz.managersystem.service.SbglService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class ApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SbglService sbglService;

    @Autowired
    GgymService ggymService;

    //页面
    @RequestMapping(value = "/ym/{name}", method = RequestMethod.GET)
    @ResponseBody
    private TGgym getYm(@PathVariable("name") String name) {
        return ggymService.findOneByName(name);
    }

    //设备
    @RequestMapping(value = "/sb/{name}", method = RequestMethod.GET)
    @ResponseBody
    private TGgym getSb(@PathVariable("name") String name) {
        TDevice device = sbglService.findOneByName(name);
        if(device == null){
            throw new NullPointerException("设备不存在");
        }
        Integer ymId = device.getGgymId();
        return ggymService.findOne(ymId);
    }

}
