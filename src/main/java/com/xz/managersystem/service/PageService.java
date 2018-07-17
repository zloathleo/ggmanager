package com.xz.managersystem.service;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dao.PageMapper;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.entity.TGroupInfo;
import com.xz.managersystem.entity.TPageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class PageService {

    @Autowired
    PageMapper pageMapper;

    @Autowired
    GroupService groupService;

    public int getCount(String group) {
        return pageMapper.getCount(group);
    }

    public List<TPageInfo> getPageList(ConditionParams params) {
        if (params.getStart() == null || params.getRows() == null) {
            return pageMapper.selectList(params.getGroup());
        } else {
            return pageMapper.selectPage(params);
        }
    }

    public TPageInfo getPage(String label) {
        if (StringUtils.isBlank(label)) {
            throw new RuntimeException("无效的页面名");
        }

        TPageInfo pageInfo = pageMapper.selectByLabel(label);
        if (pageInfo == null){
            throw new EntityNotFoundException("页面" + label + "不存在");
        }
        return pageInfo;
    }

    public void addPage(TPageInfo pageInfo) {
        pageInfo.setLabel(UtilTools.getUUID(8));
        if (pageInfo.getName() == null)
            pageInfo.setName("");
        if (pageInfo.getContent() == null)
            pageInfo.setContent("");
        if (pageMapper.insert(pageInfo) <= 0) {
            throw new RuntimeException("添加页面失败");
        }
    }

    public void updatePage(TPageInfo pageInfo) {
        TPageInfo pageBase = getPage(pageInfo.getLabel());
        if (pageInfo.getName() != null)
            pageBase.setName(pageInfo.getName());
        if (pageInfo.getContent() != null)
            pageBase.setContent(pageInfo.getContent());
        if (pageMapper.updateByLabel(pageBase) <= 0) {
            throw new RuntimeException("更新页面" + pageBase.getLabel() + "失败");
        }
    }

    public void deletePage(String label){
        TPageInfo pageInfo = getPage(label);
        if (pageMapper.deleteByLabel(label) <= 0) {
            throw new RuntimeException("删除页面" + label + "失败");
        }

        TGroupInfo groupInfo = groupService.getGroup(pageInfo.getGroup());
        if (StringUtils.isNotBlank(groupInfo.getPage()) &&
                groupInfo.getPage().equalsIgnoreCase(label))
            groupService.publishPage(pageInfo.getGroup(), "");
    }

    public void publishPage(String label) {
        TPageInfo pageInfo = getPage(label);
        groupService.publishPage(pageInfo.getGroup(), label);
    }

}
