package com.xz.managersystem.service;

import com.xz.managersystem.dao.RmsgMapper;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.entity.TRmsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RmsgService {

    @Autowired
    RmsgMapper mapper;

    public List<TRmsg> selectVisibleAll() {
        return mapper.selectVisibleAll();
    }

    public List<TRmsg> selectPage(TablePageParams params) {
        return mapper.selectPage(params);
    }

    public int getAllCount() {
        return mapper.getAllCount();
    }

    public int getVisibleCount() {
        return mapper.getVisibleCount();
    }

    public TRmsg findOne(Integer id) {
        return mapper.findOne(id);
    }

    public TRmsg findOneByName(String label) {
        return mapper.findOneByName(label);
    }

    public int insert(TRmsg one) {
        return mapper.insert(one);
    }

    public int updateByPrimaryKeySelective(TRmsg one) {
        return mapper.updateByPrimaryKeySelective(one);
    }

    public int deleteById(Integer id) {
        return mapper.deleteById(id);
    }
}
