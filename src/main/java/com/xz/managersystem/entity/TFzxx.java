package com.xz.managersystem.entity;

import java.util.Date;

public class TFzxx extends BasicEntity {

    private Integer id;

    private String label;

    private String des;

    private Integer ymId;

    private Date createTime;

    private Date updateTime;

    private String ymLabel;

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

    public Integer getYmId() {
        return ymId;
    }

    public void setYmId(Integer ymId) {
        this.ymId = ymId;
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

}
