package com.gulimall.member.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author aqiang9  2020-09-02
 */
@Getter
@Setter
public class MemberLoginVo {
    private String loginAccount ;
    private String password ;

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
}
