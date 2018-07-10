package com.xz.managersystem.dto.res;

import com.xz.managersystem.entity.BasicEntity;

public class TLoginRes extends BasicEntity {

    private String token;

    private String type;

    private String group;

    public TLoginRes(String token, String type, String group) {
        this.token = token;
        this.type = type;
        this.group = group;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
