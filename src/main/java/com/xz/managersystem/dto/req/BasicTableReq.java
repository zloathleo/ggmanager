package com.xz.managersystem.dto.req;

/**
 * 请求表格的默认参数
 */
public class BasicTableReq extends BasicReq {
    private Integer page;
    private Integer rows;
    private String type;
    private Boolean filtergroup;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
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

    public Boolean getFiltergroup() {
        return filtergroup;
    }

    public void setFiltergroup(Boolean filtergroup) {
        this.filtergroup = filtergroup;
    }

    @Override
    public String toString() {
        return "BasicTableReq{" +
                "page=" + page +
                ", rows=" + rows +
                ", type=" + type +
                '}';
    }
}
