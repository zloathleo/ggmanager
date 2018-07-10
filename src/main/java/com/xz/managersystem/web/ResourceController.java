package com.xz.managersystem.web;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.entity.BasicEntity;
import com.xz.managersystem.entity.TResourceInfo;
import com.xz.managersystem.entity.TUserInfo;
import com.xz.managersystem.service.ResourceService;
import com.xz.managersystem.web.resolver.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * 分组
 */
@Controller
@RequestMapping("/resources")
public class ResourceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ResourceService resService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getResourceList(@Authorization TUserInfo userInfo,
                                        @Valid BasicTableReq tr) {
        ConditionParams params = new ConditionParams();
        if (tr.getRows() != null && tr.getPage() != null) {
            params.setStart((tr.getPage() - 1) * tr.getRows());
            params.setRows(tr.getRows());
        }
        params.setType(tr.getType());
        params.setGroup(userInfo.getGroup());

        int resCount = resService.getCount(params);
        List<TResourceInfo> resList = resService.getResourceList(params);
        return new BasicTableRes<>(resCount, resList);
    }

    @RequestMapping(value = "/{label}", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getResource(@Authorization TUserInfo userInfo,
                                    @PathVariable("label") String label) {
        return resService.getResource(label);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes addResource(@Authorization TUserInfo userInfo,
                                 @RequestParam(name = "file") MultipartFile multipartFile,
                                 @Valid TResourceInfo resInfo){
        resInfo.setGroup(userInfo.getGroup());
        resService.addResource(multipartFile, resInfo);
        return new BasicRes("添加资源成功");
    }

    @RequestMapping(value = "/{label}/update", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes updateResource(@Authorization TUserInfo userInfo,
                                 @Valid TResourceInfo resInfo) {
        resService.updateResource(resInfo);
        return new BasicRes("修改资源成功");
    }

    @RequestMapping(value = "/{label}/delete", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes deleteResource(@Authorization TUserInfo userInfo,
                                 @PathVariable("label") String label) {
        resService.deleteResource(label);
        return new BasicRes("删除分组成功");
    }
}
