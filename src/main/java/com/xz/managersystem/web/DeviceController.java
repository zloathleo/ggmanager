package com.xz.managersystem.web;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.dto.res.TPageDto;
import com.xz.managersystem.entity.*;
import com.xz.managersystem.service.DeviceService;
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

/**
 * 页面
 */
@Controller
@RequestMapping("/devices")
public class DeviceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DeviceService devService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getDeviceList(@Authorization TUserInfo userInfo,
                                      @Valid BasicTableReq tr) {
        ConditionParams params = new ConditionParams();
        if (tr.getRows() != null && tr.getPage() != null) {
            params.setStart((tr.getPage() - 1) * tr.getRows());
            params.setRows(tr.getRows());
        }
        params.setGroup(userInfo.getGroup());

        int devCount = devService.getCount(userInfo.getGroup());
        List<TDeviceInfo> devList = devService.getDeviceList(params);
        return new BasicTableRes<>(devCount, devList);
    }

    @RequestMapping(value = "/{label}", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getDevice(@Authorization TUserInfo userInfo,
                                  @PathVariable("label") String label) {
        return devService.getDevice(label);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity addDevice(@Authorization TUserInfo userInfo,
                                  @Valid TDeviceInfo devInfo) {
        devInfo.setGroup(userInfo.getGroup());
        devService.addDevice(devInfo);
        return new BasicRes("添加设备成功");
    }

    @RequestMapping(value = "/{label}/update", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity updateDevice(@Authorization TUserInfo userInfo,
                                     @Valid TDeviceInfo devInfo) {
        devService.updateDevice(devInfo);
        return new BasicRes("修改设备成功");
    }

    @RequestMapping(value = "/{label}/delete", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity deleteDevice(@Authorization TUserInfo userInfo,
                                   @PathVariable("label") String label) {
        devService.deleteDevice(label);
        return new BasicRes("删除设备成功");
    }
}
