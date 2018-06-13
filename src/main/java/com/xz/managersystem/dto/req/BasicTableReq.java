package com.xz.managersystem.dto.req;

/**
 * 请求表格的默认参数
 */
public class BasicTableReq extends BasicReq {
    private Integer page;
    private Integer rows;

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

    @Override
    public String toString() {
        return "BasicTableReq{" +
                "page=" + page +
                ", rows=" + rows +
                '}';
    }
}
