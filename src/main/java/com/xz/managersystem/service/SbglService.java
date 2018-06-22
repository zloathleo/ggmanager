package com.xz.managersystem.service;

import com.xz.managersystem.dao.SbglMapper;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.entity.TSbxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SbglService {

    @Autowired
    SbglMapper mapper;

    public List<TSbxx> selectVisibleAll() {
        return mapper.selectVisibleAll();
    }

    public List<TSbxx> selectPage(TablePageParams params) {
        return mapper.selectPage(params);
    }

    public int getAllCount() {
        return mapper.getAllCount();
    }

    public int getVisibleCount() {
        return mapper.getVisibleCount();
    }

    public TSbxx findOne(Integer id) {
        return mapper.findOne(id);
    }

    public TSbxx findOneByName(String label) {
        return mapper.findOneByName(label);
    }

    public int insert(TSbxx one) {
        return mapper.insert(one);
    }

    public int updateByPrimaryKeySelective(TSbxx one) {
        return mapper.updateByPrimaryKeySelective(one);
    }

    public int deleteById(Integer id) {
        return mapper.deleteById(id);
    }

}
