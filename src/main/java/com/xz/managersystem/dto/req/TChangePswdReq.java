package com.xz.managersystem.dto.req;

import com.xz.managersystem.entity.BasicEntity;

import javax.validation.constraints.NotNull;

public class TChangePswdReq extends BasicEntity {

    @NotNull(message = "原密码不能为空")
    private String oldPassword;

    @NotNull(message = "新密码不能为空")
    private String newPassword;

    public TChangePswdReq(@NotNull(message = "原密码不能为空") String oldPassword,
                          @NotNull(message = "新密码不能为空") String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
