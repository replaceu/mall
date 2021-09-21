package com.gulimall.ware.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.ware.entity.WareOrderTaskEntity;

/**
 * 库存工作单
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:07:54
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

	PageUtils queryPage(Map<String, Object> params);

	WareOrderTaskEntity getOrderTaskByOrderSn(String orderSn);
}
