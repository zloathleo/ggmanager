package com.xz.managersystem.dto.req;

public class TDeviceAdd extends BasicReq {

    private Integer id;

    private String label;

    private String des;

    private String location;

    private Integer ggymId;

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
}
