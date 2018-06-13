package com.xz.managersystem.service;

import com.xz.managersystem.dao.GgymMapper;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.entity.TGgym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GgymService {

    @Autowired
    GgymMapper mapper;

    public List<TGgym> selectVisibleAll() {
        return mapper.selectVisibleAll();
    }

    public List<TGgym> selectPage(TablePageParams params) {
        return mapper.selectPage(params);
    }

    public int getAllCount() {
        return mapper.getAllCount();
    }

    public int getVisibleCount() {
        return mapper.getVisibleCount();
    }

    @Cacheable(value = "CacheTGgym", key = "#id")
    public TGgym findOne(Integer id) {
        return mapper.findOne(id);
    }

    public TGgym findOneByName(String label) {
        return mapper.findOneByName(label);
    }

    public int insert(TGgym tGgym) {
        return mapper.insert(tGgym);
    }

    @CacheEvict(value = "CacheTGgym", key = "#one.getId()")
    public int updateByPrimaryKeySelective(TGgym one) {
        return mapper.updateByPrimaryKeySelective(one);
    }

    public int deleteById(Integer id) {
        return mapper.deleteById(id);
    }

}
