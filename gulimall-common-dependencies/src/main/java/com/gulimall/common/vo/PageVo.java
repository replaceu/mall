package com.gulimall.common.vo;

import lombok.Data;

/**
 * 分页数据 基础 vo 对象
 *
 * @author aqiang9  2020-07-29
 */
@Data
public class PageVo {
    /**
     * 当前页码
     */
    private Long page;
    /**
     * 每页显示记录数
     */
    private Long limit;
    /**
     * 排序字段
     */
    private String sidx;
    /**
     * 排序方式
     */
    private String order;
    /**
     * 升序
     */
    private String asc;

    /**
     * 查询关键字
     */
    private String key;
}
