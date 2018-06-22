package com.xz.managersystem.service;

import com.xz.managersystem.dao.YmglMapper;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.entity.TYmxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YmglService {

    @Autowired
    YmglMapper mapper;

    public List<TYmxx> selectVisibleAll() {
        return mapper.selectVisibleAll();
    }

    public List<TYmxx> selectPage(TablePageParams params) {
        return mapper.selectPage(params);
    }

    public int getAllCount() {
        return mapper.getAllCount();
    }

    public int getVisibleCount() {
        return mapper.getVisibleCount();
    }

    @Cacheable(value = "CacheTGgym", key = "#id")
    public TYmxx findOne(Integer id) {
        return mapper.findOne(id);
    }

    public TYmxx findOneByName(String label) {
        return mapper.findOneByName(label);
    }

    public int insert(TYmxx tYmxx) {
        return mapper.insert(tYmxx);
    }

    @CacheEvict(value = "CacheTGgym", key = "#one.getId()")
    public int updateByPrimaryKeySelective(TYmxx one) {
        return mapper.updateByPrimaryKeySelective(one);
    }

    public int deleteById(Integer id) {
        return mapper.deleteById(id);
    }

}
