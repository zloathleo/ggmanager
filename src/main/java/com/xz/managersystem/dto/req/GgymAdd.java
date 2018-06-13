package com.xz.managersystem.dto.req;

public class GgymAdd extends BasicReq {

    private Integer id;

    private String name;

    private String desc;

    private Integer stype;

    private String videoUrls;

    private String imgUrls;

    private String textMsg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStype() {
        return stype;
    }

    public void setStype(Integer stype) {
        this.stype = stype;
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
}
