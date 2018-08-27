package com.xz.managersystem.web;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.req.TMessageReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.dto.res.TMessageDto;
import com.xz.managersystem.entity.*;
import com.xz.managersystem.service.MessageService;
import com.xz.managersystem.service.UtilTools;
import com.xz.managersystem.web.resolver.Authorization;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 页面
 */
@Controller
@RequestMapping("/messages")
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessageService msgService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getMessageList(@Authorization TUserInfo userInfo,
                                       @Valid BasicTableReq tr) {
        ConditionParams params = UtilTools.convertFromTabelReq(tr, userInfo.getGroup());
        int msgCount = msgService.getCount(params);
        List<TMessageInfo> msgInfoList = msgService.getMessageList(params);
        List<TMessageDto> msgDtoList = msgInfoList.stream().map(msgInfo -> {
            TMessageDto msgDto = new TMessageDto();
            msgDto.setId(msgInfo.getId());
            msgDto.setLabel(msgInfo.getLabel());
            msgDto.setName(msgInfo.getName());
            msgDto.setType(msgInfo.getType());
            msgDto.setStatus(getMessageStatus(msgInfo.getStartTime(), msgInfo.getEndTime()));
            msgDto.setStartTime(msgInfo.getStartTime());
            msgDto.setEndTime(msgInfo.getEndTime());
            msgDto.setUpdateTime(msgInfo.getUpdateTime());
            return msgDto;
        }).collect(Collectors.toList());
        return new BasicTableRes<>(msgCount, msgDtoList);
    }

    @RequestMapping(value = "/{label}", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity getMessage(@Authorization TUserInfo userInfo,
                                   @PathVariable("label") String label) {
        return msgService.getMessage(label);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity addMessage(@Authorization TUserInfo userInfo,
                                   @RequestParam(name = "file", required = false) MultipartFile multipartFile,
                                   @Valid TMessageReq msgReq) {
        TMessageInfo msgInfo = new TMessageInfo();
        msgInfo.setName(msgReq.getName());
        msgInfo.setType(msgReq.getType());
        msgInfo.setGroup(userInfo.getGroup());
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            msgInfo.setStartTime(sDateFormat.parse(msgReq.getStartTime()));
            msgInfo.setEndTime(sDateFormat.parse(msgReq.getEndTime()));
        } catch(ParseException px) {
            throw new RuntimeException(px.getMessage());
        }
        msgService.addMessage(multipartFile, msgInfo);
        return new BasicRes("添加消息成功");
    }

    @RequestMapping(value = "/{label}/update", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity updateMessage(@Authorization TUserInfo userInfo,
                                      @Valid TMessageReq msgReq) {
        TMessageInfo msgInfo = new TMessageInfo();
        msgInfo.setLabel(msgReq.getLabel());
        msgInfo.setName(msgReq.getName());
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (StringUtils.isNotBlank(msgReq.getStartTime()))
                msgInfo.setStartTime(sDateFormat.parse(msgReq.getStartTime()));
            if (StringUtils.isNotBlank(msgReq.getEndTime()))
                msgInfo.setEndTime(sDateFormat.parse(msgReq.getEndTime()));
        } catch(ParseException px) {
            throw new RuntimeException(px.getMessage());
        }
        msgService.updateMessage(msgInfo);
        return new BasicRes("修改消息成功");
    }

    @RequestMapping(value = "/{label}/delete", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity deleteMessage(@Authorization TUserInfo userInfo,
                                      @PathVariable("label") String label) {
        msgService.deleteMessage(label);
        return new BasicRes("删除消息成功");
    }

    private Integer getMessageStatus(Date startTime, Date endTime) {
        Date now = new Date();
        if (now.getTime() < startTime.getTime()) {
            return 0;
        } else if (now.getTime() > endTime.getTime()) {
            return -1;
        } else {
            return 1;
        }
    }
}
