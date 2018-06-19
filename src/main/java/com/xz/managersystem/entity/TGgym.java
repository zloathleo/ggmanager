package com.xz.managersystem.entity;

import java.util.Date;

public class TGgym extends BasicEntity {

    private Integer id;

    private String label;

    private Integer stype;

    private String des;

    private Integer ggmbId;

    private String videoUrls;

    private String imgUrls;

    private String textMsg;

    private Date createTime;

    private Date updateTime;

    private String mbLabel;

    private String mbDes;


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

    public Integer getStype() {
        return stype;
    }

    public void setStype(Integer stype) {
        this.stype = stype;
    }

    public Integer getGgmbId() {
        return ggmbId;
    }

    public void setGgmbId(Integer ggmbId) {
        this.ggmbId = ggmbId;
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

    public String getTextMsg() {
        return textMsg;
    }

    public void setTextMsg(String textMsg) {
        this.textMsg = textMsg;
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

    public String getMbDes() {
        return mbDes;
    }

    public void setMbDes(String mbDes) {
        this.mbDes = mbDes;
    }
}
