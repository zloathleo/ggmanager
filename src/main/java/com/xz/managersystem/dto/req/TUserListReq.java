package com.xz.managersystem.dto.req;

/**
 * 请求表格的默认参数
 */
public class TUserListReq extends BasicReq {
    private Integer page;
    private Integer rows;
    private String type;
    private Boolean filterGroup;

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

    public Boolean getFilterGroup() {
        return filterGroup;
    }

    public void setFilterGroup(Boolean filterGroup) {
        this.filterGroup = filterGroup;
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
