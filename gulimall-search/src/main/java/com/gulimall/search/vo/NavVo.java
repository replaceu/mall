package com.gulimall.search.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 面包屑数据
 * @author aqiang9  2020-08-22
 */
@Getter
@Setter
public class NavVo {
    private String navName;
    private String navValue;
    private String link ;

    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName == null ? null : navName.trim();
    }

    public String getNavValue() {
        return navValue;
    }

    public void setNavValue(String navValue) {
        this.navValue = navValue == null ? null : navValue.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }
}
