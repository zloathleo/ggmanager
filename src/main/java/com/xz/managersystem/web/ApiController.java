package com.xz.managersystem.web;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.dto.res.TMessageDto;
import com.xz.managersystem.dto.res.TPageDto;
import com.xz.managersystem.entity.BasicEntity;
import com.xz.managersystem.entity.TGroupInfo;
import com.xz.managersystem.entity.TMessageInfo;
import com.xz.managersystem.entity.TPageInfo;
import com.xz.managersystem.service.GroupService;
import com.xz.managersystem.service.MessageService;
import com.xz.managersystem.service.PageService;
import com.xz.managersystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/group/{label}/messages", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getMessages(@PathVariable("label") String label) {
        ConditionParams params = new ConditionParams();
        params.setGroup(label);
        params.setFilter(true);
        int msgCount = msgService.getCount(params);
        List<TMessageInfo> msgInfoList = msgService.getMessageList(params);
        List<TMessageDto> msgDtoList = msgInfoList.stream().map(msgInfo -> {
            TMessageDto msgDto = new TMessageDto();
            msgDto.setId(msgInfo.getId());
            msgDto.setLabel(msgInfo.getLabel());
            msgDto.setName(msgInfo.getName());
            msgDto.setType(msgInfo.getType());
            msgDto.setStatus(1);
            msgDto.setStartTime(msgInfo.getStartTime());
            msgDto.setEndTime(msgInfo.getEndTime());
            msgDto.setUpdateTime(msgInfo.getUpdateTime());
            return msgDto;
        }).collect(Collectors.toList());
        return new BasicTableRes<>(msgCount, msgDtoList);
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
