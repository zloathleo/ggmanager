package com.xz.managersystem.service;

import com.xz.managersystem.dao.FzMapper;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.entity.TFzxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FzglService {

    @Autowired
    FzMapper mapper;

    public List<TFzxx> selectVisibleAll() {
        return mapper.selectVisibleAll();
    }

    public List<TFzxx> selectPage(TablePageParams params) {
        return mapper.selectPage(params);
    }

    public int getAllCount() {
        return mapper.getAllCount();
    }

    public int getVisibleCount() {
        return mapper.getVisibleCount();
    }

    public TFzxx findOne(Integer id) {
        return mapper.findOne(id);
    }

    public TFzxx findOneByName(String label) {
        return mapper.findOneByName(label);
    }

    public int insert(TFzxx one) {
        return mapper.insert(one);
    }

    public int updateByPrimaryKeySelective(TFzxx one) {
        return mapper.updateByPrimaryKeySelective(one);
    }

    public int deleteById(Integer id) {
        return mapper.deleteById(id);
    }

}
