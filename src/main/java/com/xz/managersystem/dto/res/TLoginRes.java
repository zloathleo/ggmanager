package com.xz.managersystem.dto.res;

import com.xz.managersystem.entity.BasicEntity;

public class TLoginRes extends BasicEntity {

    private String name;

    private String token;

    private String type;

    private String group;

    public TLoginRes(String name, String token, String type, String group) {
        this.name = name;
        this.token = token;
        this.type = type;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
