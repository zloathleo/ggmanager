package com.xz.managersystem.web;

import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.dto.res.TPageDto;
import com.xz.managersystem.entity.*;
import com.xz.managersystem.service.GroupService;
import com.xz.managersystem.service.PageService;
import com.xz.managersystem.web.resolver.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 页面
 */
@Controller
@RequestMapping("/pages")
public class PageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PageService pageService;

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getPageList(@Authorization TUserInfo userInfo,
                                    @Valid BasicTableReq tr) {
        TGroupInfo groupInfo = groupService.getGroup(userInfo.getGroup());
        int pageCount = pageService.getCount(userInfo.getGroup());
        List<TPageInfo> pageInfoList = pageService.getPageList(userInfo.getGroup(), tr);
        List<TPageDto> pageDtoList = pageInfoList.stream().map(pageInfo -> {
            TPageDto pageDto = new TPageDto();
            pageDto.setId(pageInfo.getId());
            pageDto.setLabel(pageInfo.getLabel());
            pageDto.setName(pageInfo.getName());
            pageDto.setContent(pageInfo.getContent());
            pageDto.setActive(groupInfo.getPage().equalsIgnoreCase(pageInfo.getLabel()));
            pageDto.setUpdateTime(pageInfo.getUpdateTime());
            return pageDto;
        }).collect(Collectors.toList());
        return new BasicTableRes<>(pageCount, pageDtoList);
    }

    @RequestMapping(value = "/{label}", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getPage(@Authorization TUserInfo userInfo,
                                @PathVariable("label") String label) {
        TGroupInfo groupInfo = groupService.getGroup(userInfo.getGroup());
        TPageInfo pageInfo = pageService.getPage(label);
        TPageDto pageDto = new TPageDto();
        pageDto.setId(pageInfo.getId());
        pageDto.setLabel(pageInfo.getLabel());
        pageDto.setName(pageInfo.getName());
        pageDto.setActive(groupInfo.getPage().equalsIgnoreCase(label));
        pageDto.setUpdateTime(pageInfo.getUpdateTime());
        return pageDto;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity addPage(@Authorization TUserInfo userInfo,
                                @Valid TPageDto pageDto) {
        TPageInfo pageInfo = new TPageInfo();
        pageInfo.setName(pageDto.getName());
        pageInfo.setContent(pageDto.getContent());
        pageInfo.setGroup(userInfo.getGroup());
        pageService.addPage(pageInfo);
        return new BasicRes("添加页面成功");
    }

    @RequestMapping(value = "/{label}/update", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity updatePage(@Authorization TUserInfo userInfo,
                                   @Valid TPageDto pageDto) {
        TPageInfo pageInfo = new TPageInfo();
        pageInfo.setLabel(pageDto.getLabel());
        pageInfo.setName(pageDto.getName());
        pageInfo.setContent(pageDto.getContent());
        pageService.updatePage(pageInfo);
        return new BasicRes("修改页面成功");
    }

    @RequestMapping(value = "/{label}/delete", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity deletePage(@Authorization TUserInfo userInfo,
                                   @PathVariable("label") String label) {
        pageService.deletePage(label);
        return new BasicRes("删除页面成功");
    }

    @RequestMapping(value = "/{label}/publish", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity deleteYm(@Authorization TUserInfo userInfo,
                                 @PathVariable("label") String label) {
        pageService.publishPage(label);
        return new BasicRes("发布页面成功");
    }
}
