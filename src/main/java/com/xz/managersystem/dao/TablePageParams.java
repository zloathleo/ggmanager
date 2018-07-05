package com.xz.managersystem.dao;

public class TablePageParams {

    private Integer start;
    private Integer rows;
    private String type;

    public TablePageParams() {
    }

    public TablePageParams(Integer start, Integer rows) {
        this.start = start;
        this.rows = rows;
        this.type = "";
    }

    public TablePageParams(Integer start, Integer rows, String type) {
        this.start = start;
        this.rows = rows;
        this.type = type;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
