package com.xz.managersystem.web;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dto.res.*;
import com.xz.managersystem.entity.*;
import com.xz.managersystem.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class ApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PageService pageService;

    @Autowired
    GroupService groupService;

    @Autowired
    MessageService msgService;

    @Autowired
    UserService userService;

    @Autowired
    ResourceService resService;

    @RequestMapping(value = "/page/{label}", method = RequestMethod.GET)
    @ResponseBody
    private TPageDto getPageByPage(@PathVariable("label") String label) {
        TPageInfo pageInfo = pageService.getPage(label);
        TPageDto pageDto = new TPageDto();
        pageDto.setId(pageInfo.getId());
        pageDto.setLabel(pageInfo.getLabel());
        pageDto.setName(pageInfo.getName());
        pageDto.setContent(pageInfo.getContent());
        pageDto.setGroup(pageInfo.getGroup());
        pageDto.setUpdateTime(pageInfo.getUpdateTime());
        return pageDto;
    }

    @RequestMapping(value = "/group/{label}", method = RequestMethod.GET)
    @ResponseBody
    private TPageDto getPageByGroup(@PathVariable("label") String label) {
        TGroupInfo groupInfo = groupService.getGroup(label);
        TPageInfo pageInfo = pageService.getPage(groupInfo.getPage());
        TPageDto pageDto = new TPageDto();
        pageDto.setId(pageInfo.getId());
        pageDto.setLabel(pageInfo.getLabel());
        pageDto.setName(pageInfo.getName());
        pageDto.setContent(pageInfo.getContent());
        pageDto.setGroup(pageInfo.getGroup());
        pageDto.setUpdateTime(pageInfo.getUpdateTime());
        return pageDto;
    }

    @RequestMapping(value = "/url", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getUrlByLabel(@RequestParam(name = "name") String name) {
        List<TUrlDto> urlDtoList = new ArrayList<>();
        String [] nameList = name.split(",");
        for (int i = 0; i < nameList.length; ++i)
        {
            TUrlDto urlDto = new TUrlDto();
            int pos = nameList[i].lastIndexOf('.');
            String label = pos == -1 ? nameList[i] : nameList[i].substring(0, pos);
            TResourceInfo resInfo = resService.getResource(label);
            urlDto.setName(nameList[i]);
            urlDto.setUrl(resInfo.getUrl());
            urlDtoList.add(urlDto);
        }
        return new BasicTableRes<>(urlDtoList.size(), urlDtoList);
    }

    @RequestMapping(value = "/group/{label}/messages", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getMessages(@PathVariable("label") String label) {
        ConditionParams params = new ConditionParams();
        params.setGroup(label);
        params.setFilter(true);
        List<TMessageInfo> msgInfoList = msgService.getMessageList(params);
        List<TMessageDto> msgDtoList = new ArrayList<>();
        Boolean hasTextMsg = false;
        Boolean hasAudioMsg = false;
        for (int i = 0; i < msgInfoList.size(); ++i) {
            if (("text".equalsIgnoreCase(msgInfoList.get(i).getType()) && !hasTextMsg) ||
                    ("audio".equalsIgnoreCase(msgInfoList.get(i).getType()) && !hasAudioMsg)) {
                TMessageDto msgDto = new TMessageDto();
                msgDto.setId(msgInfoList.get(i).getId());
                msgDto.setLabel(msgInfoList.get(i).getLabel());
                msgDto.setName(msgInfoList.get(i).getName());
                msgDto.setType(msgInfoList.get(i).getType());
                msgDto.setStatus(1);
                msgDto.setStartTime(msgInfoList.get(i).getStartTime());
                msgDto.setEndTime(msgInfoList.get(i).getEndTime());
                msgDto.setUpdateTime(msgInfoList.get(i).getUpdateTime());
                msgDtoList.add(msgDto);

                if ("text".equalsIgnoreCase(msgInfoList.get(i).getType())) {
                    hasTextMsg = true;
                } else if ("audio".equalsIgnoreCase(msgInfoList.get(i).getType())) {
                    hasAudioMsg = true;
                }
            }
        }

        return new BasicTableRes<>(msgDtoList.size(), msgDtoList);
    }

    @RequestMapping(value = "/live/update", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getToken(@RequestParam(name = "group", required = true) String group) {
        TGroupInfo groupInfo = groupService.getGroup(group);
        TPageInfo pageInfo = pageService.getPage(groupInfo.getPage());
        pageService.updatePage(pageInfo);
        return new BasicRes("更新页面成功");
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    private Object getToken() {
        Map<String, String> tokenMap = userService.getTokenMap();
        Map<String, String> userMap = userService.getUserMap();
        Map<String, Map<String, String>> tokenInfo = new HashMap<>();
        tokenInfo.put("token", tokenMap);
        tokenInfo.put("user", userMap);
        return tokenInfo;
    }
}
