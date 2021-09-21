package com.gulimall.ware.vo;

import com.gulimall.common.vo.PageVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author aqiang9  2020-08-08
 */
@Getter
@Setter
@ToString
public class PurchaseDetailPageVo extends PageVo {
    private Long wareId;//仓库id
    private Integer status ; // 状态

    public Long getWareId() {
        return wareId;
    }

    public void setWareId(Long wareId) {
        this.wareId = wareId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
