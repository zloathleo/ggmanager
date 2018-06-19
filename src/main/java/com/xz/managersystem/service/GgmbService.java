package com.xz.managersystem.service;

import com.xz.managersystem.dao.GgmbMapper;
import com.xz.managersystem.dao.GgymMapper;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.entity.TGgmb;
import com.xz.managersystem.entity.TGgym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GgmbService {

    @Autowired
    GgmbMapper mapper;


    public List<TGgmb> selectPage(TablePageParams params) {
        return mapper.selectPage(params);
    }

    public int getVisibleCount() {
        return mapper.getVisibleCount();
    }

    public TGgmb findOne(Integer id) {
        return mapper.findOne(id);
    }

    public List<TGgmb> selectVisibleAll(){
        return mapper.selectVisibleAll();
    }

//    public TGgym findOneByName(String label) {
//        return mapper.findOneByName(label);
//    }

//    @CacheEvict(value = "CacheTGgym", key = "#one.getId()")
//    public int updateByPrimaryKeySelective(TGgym one) {
//        return mapper.updateByPrimaryKeySelective(one);
//    }

}
