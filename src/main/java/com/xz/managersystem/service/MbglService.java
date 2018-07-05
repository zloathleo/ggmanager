package com.xz.managersystem.service;

import com.xz.managersystem.dao.MbMapper;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.entity.TMbxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MbglService {

    @Autowired
    MbMapper mapper;

    public int getVisibleCount() {
        return mapper.getVisibleCount();
    }

    public List<TMbxx> selectVisibleAll(){
        return mapper.selectVisibleAll();
    }

    public List<TMbxx> selectPage(TablePageParams params) {
        return mapper.selectPage(params);
    }

    public TMbxx findOne(Integer id) {
        return mapper.findOne(id);
    }

    public TMbxx findOneByName(String label) {
        return mapper.findOneByName(label);
    }

    @CacheEvict(value = "CacheTMbxx", key = "#one.getId()")
    public int updateByPrimaryKeySelective(TMbxx one) {
        return mapper.updateByPrimaryKeySelective(one);
    }

}
