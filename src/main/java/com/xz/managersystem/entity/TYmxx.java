package com.xz.managersystem.entity;

import java.util.Date;

public class TYmxx extends BasicEntity {

    private Integer id;

    private String label;

    private String des;

    private String videoUrls;

    private String imgUrls;

    private String textUrls;

    private Integer mbId;

    private Date createTime;

    private Date updateTime;

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

    public String getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(String videoUrls) {
        this.videoUrls = videoUrls;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getTextUrls() {
        return textUrls;
    }

    public void setTextUrls(String textUrls) {
        this.textUrls = textUrls;
    }

    public Integer getMbId() {
        return mbId;
    }

    public void setMbId(Integer mbId) {
        this.mbId = mbId;
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

    public String getMbLabel() {
        return mbLabel;
    }

    public void setMbLabel(String mbLabel) {
        this.mbLabel = mbLabel;
    }

}
