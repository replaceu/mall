package com.gulimall.auth.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author aqiang9  2020-09-02
 */
@Getter
@Setter
public class UserLoginVo {
    @NotEmpty(message = "账号不能为空")
    private String loginAccount ;
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 来源地址
     */
    private String originUrl ;

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount == null ? null : loginAccount.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl == null ? null : originUrl.trim();
    }
}

