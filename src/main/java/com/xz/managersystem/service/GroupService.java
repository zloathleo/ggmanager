package com.xz.managersystem.service;

import com.xz.managersystem.dao.GroupMapper;
import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.entity.TGroupInfo;
import com.xz.managersystem.entity.TUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    @Value("#{config['resource.path']}")
    private String resourcePath;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    UserService userService;

    public int getCount() {
        return groupMapper.getCount();
    }

    public List<TGroupInfo> getGroupList(ConditionParams params) {
        if (params.getStart() == null || params.getRows() == null) {
            return groupMapper.selectList();
        } else {
            return groupMapper.selectPage(params);
        }
    }

    public TGroupInfo getGroup(String label) {
        if (StringUtils.isBlank(label)) {
            throw new RuntimeException("无效的分组名");
        }

        TGroupInfo groupInfo = groupMapper.selectByLabel(label);
        if (groupInfo == null) {
            throw new RuntimeException("分组" + label +"不存在");
        }
        return groupInfo;
    }

    public TGroupInfo getGroup2(String label) {
        return groupMapper.selectByLabel(label);
    }

    public void addGroup(TGroupInfo groupInfo) {
        groupInfo.setLabel(UtilTools.getUUID(8));

        if (StringUtils.isNotBlank(groupInfo.getUser())) {
            TUserInfo userInfo = userService.getUser(groupInfo.getUser());
            if ("admin".equalsIgnoreCase(userInfo.getType())) {
                throw new RuntimeException("未能为admin用户分配组");
            } else if (StringUtils.isNotBlank(userInfo.getGroup())) {
                throw new RuntimeException("用户" + groupInfo.getUser() + "已有分组");
            }
            userService.assignGroup(groupInfo.getUser(), groupInfo.getLabel());
        }

        if (groupInfo.getName() == null)
            groupInfo.setName("");
        if (groupInfo.getWidth() == null)
            groupInfo.setWidth(0);
        if (groupInfo.getHeight() == null)
            groupInfo.setHeight(0);
        if (groupInfo.getType() == null)
            groupInfo.setType("");
        if (groupInfo.getUser() == null)
            groupInfo.setUser("");
        if (groupInfo.getPage() == null)
            groupInfo.setPage("");
        if (groupMapper.insert(groupInfo) <= 0) {
            throw new RuntimeException("添加分组失败");
        }

        File groupDir = new File(resourcePath + groupInfo.getLabel());
        groupDir.mkdirs();
    }

    public void updateGroup(TGroupInfo groupInfo) {
        TGroupInfo groupBase = getGroup(groupInfo.getLabel());
        if (groupInfo.getUser() != null)
        {
            if (StringUtils.isNotBlank(groupInfo.getUser())) {
                TUserInfo userInfo = userService.getUser(groupInfo.getUser());
                if ("admin".equalsIgnoreCase(userInfo.getType())) {
                    throw new RuntimeException("不能为admin用户分配组");
                } else if (StringUtils.isNotBlank(userInfo.getGroup()) &&
                        !userInfo.getGroup().equalsIgnoreCase(groupInfo.getLabel())) {
                    throw new RuntimeException("用户" + groupInfo.getUser() + "已有分组");
                }
            }

            if (!groupBase.getUser().equalsIgnoreCase(groupInfo.getUser())) {
                if (StringUtils.isNotBlank(groupBase.getUser())) {
                    userService.assignGroup(groupBase.getUser(), "");
                }
                if (StringUtils.isNotBlank(groupInfo.getUser())) {
                    userService.assignGroup(groupInfo.getUser(), groupInfo.getLabel());
                }
                groupBase.setUser(groupInfo.getUser());
            }
        }

        if (groupInfo.getName() != null)
            groupBase.setName(groupInfo.getName());
        if (groupInfo.getWidth() != null)
            groupBase.setWidth(groupInfo.getWidth());
        if (groupInfo.getHeight() != null)
            groupBase.setHeight(groupInfo.getHeight());
        if (groupInfo.getType() != null)
            groupBase.setType(groupInfo.getType());
        if (groupInfo.getPage() != null)
            groupBase.setPage(groupInfo.getPage());
        if (groupMapper.updateByLabel(groupBase) <= 0) {
            throw new RuntimeException("更新分组" + groupBase.getLabel() + "失败");
        }
    }

    public void deleteGroup(String label){
        TGroupInfo groupInfo = getGroup(label);
        if (groupMapper.deleteByLabel(label) <= 0) {
            throw new RuntimeException("删除分组" + label + "失败");
        }
    }

    public void publishPage(String label, String page) {
        TGroupInfo groupInfo = getGroup(label);
        groupInfo.setPage(page);
        updateGroup(groupInfo);
    }

    public void assignUser(String label, String name) {
        TGroupInfo groupInfo = getGroup(label);
        if (StringUtils.isNotBlank(groupInfo.getUser()) &&
                StringUtils.isNotBlank(name) &&
                !name.equalsIgnoreCase(groupInfo.getUser())) {
            throw new RuntimeException("分组" + label + "已有维护用户");
        }

        groupInfo.setUser(name);
        if (groupMapper.updateByLabel(groupInfo) <= 0) {
            throw new RuntimeException("更新分组" + groupInfo.getLabel() + "失败");
        }
    }

    public TGroupInfo findGroup(String label) {
        if (StringUtils.isBlank(label))
            return null;
        else
            return groupMapper.selectByLabel(label);
    }
}
