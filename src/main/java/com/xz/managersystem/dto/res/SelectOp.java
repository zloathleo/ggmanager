package com.xz.managersystem.dto.res;

import com.xz.managersystem.entity.BasicEntity;

public class SelectOp<V> extends BasicEntity {

    private V value;
    private String display;

    public SelectOp(V value, String d) {
        this.value = value;
        this.display = d;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
