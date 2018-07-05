package com.xz.managersystem.service;

import com.xz.managersystem.entity.TYmxx;
import com.xz.managersystem.dao.YmMapper;
import com.xz.managersystem.dao.SbMapper;
import com.xz.managersystem.dao.FzMapper;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.dto.req.BasicTableReq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;

@Service
public class YmglService {

    @Autowired
    YmMapper ymMapper;

    @Autowired
    SbMapper sbMapper;

    public int selectYmCount() {
        return ymMapper.getVisibleCount();
    }

    public List<TYmxx> selectYmList() {
        return ymMapper.selectVisibleAll();
    }

    public List<TYmxx> selectYmPage(BasicTableReq tr) {
        return ymMapper.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows()));
    }

    public int insertYm(TYmxx ymxx) {
        if (ymxx.getLabel() == null || ymxx.getLabel().trim().isEmpty()) {
            throw new RuntimeException("页面名称不能为空");
        } else if (ymMapper.selectByName(ymxx.getLabel()) != null) {
            throw new EntityExistsException("页面" + ymxx.getLabel() + "已经存在");
        }
        return ymMapper.insert(ymxx);
    }

    public int updateYm(TYmxx ymxx) {
        if (ymxx.getLabel() == null || ymxx.getLabel().trim().isEmpty()) {
            throw new RuntimeException("页面名称不能为空");
        }

        TYmxx ymExist = ymMapper.selectByName(ymxx.getLabel());
        if (ymExist == null) {
            throw new EntityNotFoundException("页面" + ymxx.getLabel() + "不存在");
        }

        if (ymxx.getDes() != null) {
            ymExist.setDes(ymxx.getDes());
        }
        if (ymxx.getContent() != null) {
            ymExist.setContent(ymxx.getContent());
        }
        return ymMapper.updateByName(ymExist);
    }

    public int deleteYm(TYmxx ymxx) {
        if (ymxx.getLabel() == null || ymxx.getLabel().trim().isEmpty()) {
            throw new RuntimeException("页面名称不能为空");
        }
        return ymMapper.deleteByName(ymxx.getLabel());
    }

    public TYmxx selectYmByName(String label) {
        TYmxx ymxx = ymMapper.selectByName(label);
        if (ymxx == null) {
            throw new EntityNotFoundException("页面" + label + "不存在");
        }
        return ymxx;
    }

    public void publishYm(String label, String sbList, String fzList) {
        if (ymMapper.selectByName(label) == null) {
            throw new EntityNotFoundException("页面" + label + "不存在");
        }

        if (sbList != null) {
            Arrays.asList(sbList.split(",")).forEach(sbLabel ->{
                sbMapper.publishByName(label, sbLabel);
            });
        }
    }
}
