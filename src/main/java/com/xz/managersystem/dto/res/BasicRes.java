package com.xz.managersystem.dto.res;

import com.xz.managersystem.entity.BasicEntity;

public class BasicRes extends BasicEntity {

    private String msg;

    public BasicRes() {
    }

    public BasicRes(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BasicRes{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
