package com.xz.managersystem.dto.req;

import com.xz.managersystem.entity.BasicEntity;

import javax.validation.constraints.NotNull;

public class TLoginReq extends BasicEntity {

    @NotNull(message = "用户名不能为空")
    private String name;

    @NotNull(message = "密码不能为空")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
