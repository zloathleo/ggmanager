package com.xz.managersystem.dao;

public class TablePageParams {

    private Integer start;
    private Integer rows;

    public TablePageParams() {
    }

    public TablePageParams(Integer start, Integer rows) {
        this.start = start;
        this.rows = rows;
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
}
