package com.xz.managersystem.service;

import com.xz.managersystem.dao.SbglMapper;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.entity.TDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SbglService {

    @Autowired
    SbglMapper mapper;

    public List<TDevice> selectPage(TablePageParams params) {
        return mapper.selectPage(params);
    }

    public int getAllCount() {
        return mapper.getAllCount();
    }

    public int getVisibleCount() {
        return mapper.getVisibleCount();
    }

    public TDevice findOne(Integer id) {
        return mapper.findOne(id);
    }

    public TDevice findOneByName(String label) {
        return mapper.findOneByName(label);
    }

    public int insert(TDevice one) {
        return mapper.insert(one);
    }

    public int updateByPrimaryKeySelective(TDevice one) {
        return mapper.updateByPrimaryKeySelective(one);
    }

    public int deleteById(Integer id) {
        return mapper.deleteById(id);
    }

}
