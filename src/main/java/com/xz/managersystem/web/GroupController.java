package com.xz.managersystem.web;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.entity.BasicEntity;
import com.xz.managersystem.entity.TGroupInfo;
import com.xz.managersystem.entity.TUserInfo;
import com.xz.managersystem.service.GroupService;
import com.xz.managersystem.service.UtilTools;
import com.xz.managersystem.web.resolver.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 分组
 */
@Controller
@RequestMapping("/groups")
public class GroupController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getGroupList(@Authorization TUserInfo userInfo,
                                     @Valid BasicTableReq tr) {
        ConditionParams params = UtilTools.convertFromTabelReq(tr, null);
        int groupCount = groupService.getCount();
        List<TGroupInfo> groupList = groupService.getGroupList(params);
        return new BasicTableRes<>(groupCount, groupList);
    }

    @RequestMapping(value = "/{label}", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getGroup(@Authorization TUserInfo userInfo,
                                 @PathVariable("label") String label) {
        return groupService.getGroup(label);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes addGroup(@Authorization TUserInfo userInfo,
                              @Valid TGroupInfo groupInfo){
        groupService.addGroup(groupInfo);
        return new BasicRes("添加分组成功");
    }

    @RequestMapping(value = "/{label}/update", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes updateGroup(@Authorization TUserInfo userInfo,
                                 @Valid TGroupInfo groupInfo) {
        groupService.updateGroup(groupInfo);
        return new BasicRes("修改分组成功");
    }

    @RequestMapping(value = "/{label}/delete", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes deleteGroup(@Authorization TUserInfo userInfo,
                                 @PathVariable("label") String label) {
        groupService.deleteGroup(label);
        return new BasicRes("删除分组成功");
    }
}
