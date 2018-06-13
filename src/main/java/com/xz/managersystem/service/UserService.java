package com.xz.managersystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    public Integer getCount(){
        return 99;
    }


}
