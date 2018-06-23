package com.xz.managersystem.entity;

import java.util.Date;

public class TRmsg extends BasicEntity {

    private Integer id;

    private String label;

    private String startTime;

    private String endTime;

    private Integer zyId;

    private Integer fzId;

    private Date createTime;

    private Date updateTime;

    private String zyLabel;

    private String fzLabel;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getFzId() {
        return fzId;
    }

    public void setFzId(Integer fzId) {
        this.fzId = fzId;
    }

    public Integer getZyId() {
        return zyId;
    }

    public void setZyId(Integer zyId) {
        this.zyId = zyId;
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

    public String getZyLabel() {
        return zyLabel;
    }

    public void setZyLabel(String zyLabel) {
        this.zyLabel = zyLabel;
    }

    public String getFzLabel() {
        return fzLabel;
    }

    public void setFzLabel(String fzLabel) {
        this.fzLabel = fzLabel;
    }
}
