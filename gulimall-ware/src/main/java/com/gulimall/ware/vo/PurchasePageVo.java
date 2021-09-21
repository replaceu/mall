package com.gulimall.ware.vo;

import com.gulimall.common.vo.PageVo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author aqiang9  2020-08-14
 */
@Getter
@Setter
public class PurchasePageVo extends PageVo {
    private Integer status ; // 状态

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}