package com.xz.managersystem.service;

import com.xz.managersystem.dao.RmsgMapper;
import com.xz.managersystem.entity.TRmsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RmsgService {

    @Autowired
    RmsgMapper mapper;

    public int insert(TRmsg one) {
        return mapper.insert(one);
    }

}
