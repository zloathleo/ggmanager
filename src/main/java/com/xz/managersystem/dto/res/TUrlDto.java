package com.xz.managersystem.dto.res;

import com.xz.managersystem.entity.BasicEntity;

import java.util.Date;

public class TUrlDto extends BasicEntity {

    private String name;

    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
