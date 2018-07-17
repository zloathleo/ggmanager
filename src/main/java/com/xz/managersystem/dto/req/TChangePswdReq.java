package com.xz.managersystem.dto.req;

import com.xz.managersystem.entity.BasicEntity;

import javax.validation.constraints.NotNull;

public class TChangePswdReq extends BasicEntity {

    @NotNull(message = "原密码不能为空")
    private String oldpassword;

    @NotNull(message = "新密码不能为空")
    private String newpassword;

    public TChangePswdReq() {
    }

    public TChangePswdReq(@NotNull(message = "原密码不能为空") String oldpassword,
                          @NotNull(message = "新密码不能为空") String newpassword) {
        this.oldpassword = oldpassword;
        this.newpassword = newpassword;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
