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

    public int getCount(String group) {
        return msgMapper.getCount(group);
    }

    public List<TMessageInfo> getMessageList(ConditionParams params) {
        if (params.getStart() == null || params.getRows() == null) {
            return msgMapper.selectList(params.getGroup());
        } else {
            return msgMapper.selectPage(params);
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
        String label = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        msgInfo.setLabel(label.substring(0, 8));

        if (StringUtils.isNotBlank(msgInfo.getName())) {
            msgInfo.setType("text");
        } else if (multipartFile == null) {
            throw new RuntimeException("消息内容不能为空");
        } else {
            String fileName = multipartFile.getOriginalFilename();
            String fileType =fileName.substring(fileName.lastIndexOf('.'));
            if (!".mp3".equalsIgnoreCase(fileType)) {
                throw new RuntimeException("不支持的音频格式");
            }

            String destPath = resourcePath + msgInfo.getGroup() + "\\";
            String name = "AUDIO_" + new Date().getTime() + ".mp3";
            try {
                Path path = Paths.get(destPath, name);
                File file = path.toFile();
                //该方法首先进行重命名，如果不成功则进行流拷贝，如果成功则可以省下一次读、写操作
                multipartFile.transferTo(file);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            msgInfo.setType("audio");
            msgInfo.setName(name);
        }

        if (msgMapper.insert(msgInfo) <= 0) {
            throw new RuntimeException("添加消息失败");
        }
    }

    public void updateMessage(TMessageInfo msgInfo) {
        TMessageInfo msgBase = getMessage(msgInfo.getLabel());
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
