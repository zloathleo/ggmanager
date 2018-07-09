package com.xz.managersystem.dto.res;

import com.xz.managersystem.entity.BasicEntity;

public class TLoginRes extends BasicEntity {

    private String token;

    private String type;

    public TLoginRes(String token, String type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
