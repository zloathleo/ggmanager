package com.xz.managersystem.service;

import com.xz.managersystem.dao.ZyglMapper;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.entity.TZyxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZyglService {

    @Autowired
    ZyglMapper mapper;

    public List<TZyxx> selectVisibleAll() {
        return mapper.selectVisibleAll();
    }

    public List<TZyxx> selectPage(TablePageParams params) {
        return mapper.selectPage(params);
    }

    public int getAllCount() {
        return mapper.getAllCount();
    }

    public int getVisibleCount() {
        return mapper.getVisibleCount();
    }

    @Cacheable(value = "CacheTGgym", key = "#id")
    public TZyxx findOne(Integer id) {
        return mapper.findOne(id);
    }

    public TZyxx findOneByName(String label) {
        return mapper.findOneByName(label);
    }

    public int insert(TZyxx tZyxx) {
        return mapper.insert(tZyxx);
    }

    @CacheEvict(value = "CacheTGgym", key = "#one.getId()")
    public int updateByPrimaryKeySelective(TZyxx one) {
        return mapper.updateByPrimaryKeySelective(one);
    }

    public int deleteById(Integer id) {
        return mapper.deleteById(id);
    }

}
