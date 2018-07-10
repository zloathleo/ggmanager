package com.xz.managersystem.web;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.entity.*;
import com.xz.managersystem.service.MessageService;
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
        ConditionParams params = new ConditionParams();
        if (tr.getRows() != null && tr.getPage() != null) {
            params.setStart((tr.getPage() - 1) * tr.getRows());
            params.setRows(tr.getRows());
        }
        params.setGroup(userInfo.getGroup());

        int msgCount = msgService.getCount(userInfo.getGroup());
        List<TMessageInfo> msgList = msgService.getMessageList(params);
        return new BasicTableRes<>(msgCount, msgList);
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
                                   @Valid TMessageInfo msgInfo) {
        msgInfo.setGroup(userInfo.getGroup());
        msgService.addMessage(multipartFile, msgInfo);
        return new BasicRes("添加消息成功");
    }

    @RequestMapping(value = "/{label}/update", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity updateMessage(@Authorization TUserInfo userInfo,
                                      @Valid TMessageInfo msgInfo) {
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
}
