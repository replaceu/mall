package com.gulimall.member.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author aqiang9  2020-09-02
 */
@Getter
@Setter
public class MemberRegisterVo {
    private String username ;
    private String password;
    private String mobile;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }
}
