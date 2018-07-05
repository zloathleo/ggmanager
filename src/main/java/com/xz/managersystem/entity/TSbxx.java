package com.xz.managersystem.entity;

import java.util.Date;

public class TSbxx extends BasicEntity {

    private Integer id;

    private String label;

    private String des;

    private String location;

    private String fzLabel;

    private String ymLabel;

    private String ymContent;

    private Date ymUpdateTime;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFzLabel() {
        return fzLabel;
    }

    public void setFzLabel(String fzLabel) {
        this.fzLabel = fzLabel;
    }

    public String getYmLabel() {
        return ymLabel;
    }

    public void setYmLabel(String ymLabel) {
        this.ymLabel = ymLabel;
    }

    public String getYmContent() {
        return ymContent;
    }

    public void setYmContent(String ymContent) {
        this.ymContent = ymContent;
    }

    public Date getYmUpdateTime() {
        return ymUpdateTime;
    }

    public void setYmUpdateTime(Date ymUpdateTime) {
        this.ymUpdateTime = ymUpdateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
