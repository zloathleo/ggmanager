package com.xz.managersystem.service;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dao.MessageMapper;
import com.xz.managersystem.entity.TMessageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    @Value("#{config['resource.path']}")
    private String resourcePath;

    @Autowired
    MessageMapper msgMapper;

    public int getCount(ConditionParams params) {
        if (params.getFilter() != null && params.getFilter()) {
            return msgMapper.getFilterCount(params.getGroup());
        } else {
            return msgMapper.getCount(params.getGroup());
        }
    }

    public List<TMessageInfo> getMessageList(ConditionParams params) {
        if (params.getFilter() != null && params.getFilter()) {
            if (params.getStart() == null || params.getRows() == null) {
                return msgMapper.selectFilterList(params.getGroup());
            } else {
                return msgMapper.selectFilterPage(params);
            }
        } else {
            if (params.getStart() == null || params.getRows() == null) {
                return msgMapper.selectList(params.getGroup());
            } else {
                return msgMapper.selectPage(params);
            }
        }
    }

    public TMessageInfo getMessage(String label) {
        if (StringUtils.isBlank(label)) {
            throw new RuntimeException("无效的消息名");
        }

        TMessageInfo msgInfo = msgMapper.selectByLabel(label);
        if (msgInfo == null){
            throw new EntityNotFoundException("消息" + label + "不存在");
        }
        return msgInfo;
    }

    public void addMessage(MultipartFile multipartFile, TMessageInfo msgInfo) {
        msgInfo.setLabel(UtilTools.getUUID(8));
        if (StringUtils.isBlank(msgInfo.getType())) {
                throw new RuntimeException("不支持的消息类型");
            } else if ("audio".equalsIgnoreCase(msgInfo.getType())) {
                String fileName = multipartFile.getOriginalFilename();
                String fileSuffix = UtilTools.getFileSuffix(fileName);
                if (!"mp3".equalsIgnoreCase(fileSuffix)) {
                    throw new RuntimeException("不支持的音频格式");
                }

            String destPath = resourcePath + msgInfo.getGroup() + "\\";
            String name = "AUDIO_" + new Date().getTime() + ".mp3";
            UtilTools.transferFile(multipartFile, destPath + name);
            msgInfo.setName(name);
        } else if (!"text".equalsIgnoreCase(msgInfo.getType())) {
            throw new RuntimeException("不支持的消息类型");
        }

        if (msgMapper.insert(msgInfo) <= 0) {
            throw new RuntimeException("添加消息失败");
        }
    }

    public void updateMessage(TMessageInfo msgInfo) {
        TMessageInfo msgBase = getMessage(msgInfo.getLabel());
        if (msgInfo.getName() != null)
            msgBase.setName(msgInfo.getName());
        if (msgInfo.getStartTime() != null)
            msgBase.setStartTime(msgInfo.getStartTime());
        if (msgInfo.getEndTime() != null)
            msgBase.setEndTime(msgInfo.getEndTime());
        if (msgMapper.updateByLabel(msgBase) <= 0) {
            throw new RuntimeException("更新消息" + msgBase.getLabel() + "失败");
        }
    }

    public void deleteMessage(String label){
        TMessageInfo msgInfo = getMessage(label);
        if (msgMapper.deleteByLabel(label) <= 0) {
            throw new RuntimeException("删除消息" + label + "失败");
        }
    }
}
