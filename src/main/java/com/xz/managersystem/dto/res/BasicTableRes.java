package com.xz.managersystem.dto.res;

import com.xz.managersystem.entity.BasicEntity;

import java.util.List;

/**
 * 表格输出
 * @param <V>
 */
public class BasicTableRes<V> extends BasicEntity {

    private int total;

    private List<V> rows;

    public BasicTableRes() {
    }

    public BasicTableRes(int total, List<V> rows) {
        this.total = total;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<V> getRows() {
        return rows;
    }

    public void setRows(List<V> rows) {
        this.rows = rows;
    }
}
