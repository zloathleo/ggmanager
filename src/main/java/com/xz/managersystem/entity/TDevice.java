package com.xz.managersystem.entity;

import java.util.Date;

public class TDevice  extends BasicEntity {

    private Integer id;

    private String label;

    private String des;

    private String location;

    private Integer ggymId;

    private Date createTime;

    private Date updateTime;

    private String ymLabel;

    private Integer mbId;

    private String mbLabel;

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

    public Integer getGgymId() {
        return ggymId;
    }

    public void setGgymId(Integer ggymId) {
        this.ggymId = ggymId;
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

    public String getYmLabel() {
        return ymLabel;
    }

    public void setYmLabel(String ymLabel) {
        this.ymLabel = ymLabel;
    }

    public Integer getMbId() {
        return mbId;
    }

    public void setMbId(Integer mbId) {
        this.mbId = mbId;
    }

    public String getMbLabel() {
        return mbLabel;
    }

    public void setMbLabel(String mbLabel) {
        this.mbLabel = mbLabel;
    }
}
