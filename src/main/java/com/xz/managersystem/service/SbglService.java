package com.xz.managersystem.service;

import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.entity.TSbxx;
import com.xz.managersystem.entity.TFzxx;
import com.xz.managersystem.dao.SbMapper;
import com.xz.managersystem.dao.FzMapper;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.entity.TYmxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SbglService {

    @Autowired
    SbMapper sbMapper;

    @Autowired
    FzMapper fzMapper;

    public int insertSb(TSbxx sbxx) {
        if (sbxx.getLabel() == null || sbxx.getLabel().trim().isEmpty()) {
            throw new RuntimeException("设备名称不能为空");
        } else if (sbMapper.selectByName(sbxx.getLabel()) != null) {
            throw new EntityExistsException("设备" + sbxx.getLabel() + "已经存在");
        }
        return sbMapper.insert(sbxx);
    }

    public int updateSb(TSbxx sbxx) {
        if (sbxx.getLabel() == null || sbxx.getLabel().trim().isEmpty()) {
            throw new RuntimeException("设备名称不能为空");
        }

        TSbxx sbExist = sbMapper.selectByName(sbxx.getLabel());
        if (sbExist == null) {
            throw new EntityNotFoundException("设备" + sbxx.getLabel() + "不存在");
        }

        if (sbxx.getDes() != null) {
            sbExist.setDes(sbxx.getDes());
        }
        if (sbxx.getLocation() != null) {
            sbExist.setLocation(sbxx.getLocation());
        }
        if (sbxx.getFzLabel() != null) {
            sbExist.setFzLabel(sbxx.getFzLabel());
        }
        if (sbxx.getYmLabel() != null) {
            sbExist.setYmLabel(sbxx.getYmLabel());
        }
        return sbMapper.updateByName(sbExist);
    }

    public int deleteSb(TSbxx sbxx) {
        if (sbxx.getLabel() == null || sbxx.getLabel().trim().isEmpty()) {
            throw new RuntimeException("页面名称不能为空");
        }
        return sbMapper.deleteByName(sbxx.getLabel());
    }

    public TSbxx selectSbByName(String label) {
        return sbMapper.selectByName(label);
    }

    public int deleteSbByName(String label) {
        return sbMapper.deleteByName(label);
    }

    public List<TSbxx> selectSbList() {
        return sbMapper.selectVisibleAll();
    }

    public List<TSbxx> selectSbPage(BasicTableReq tr) {
        return sbMapper.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows()));
    }

    public int selectSbCount() {
        return sbMapper.getVisibleCount();
    }
}
