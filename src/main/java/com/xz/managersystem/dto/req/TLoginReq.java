package com.xz.managersystem.dto.req;

import com.xz.managersystem.entity.BasicEntity;

public class TLoginReq extends BasicEntity {

    private String user;

    private String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
