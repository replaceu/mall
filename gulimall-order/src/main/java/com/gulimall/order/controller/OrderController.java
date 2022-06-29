package com.gulimall.order.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.order.entity.OrderEntity;
import com.gulimall.order.service.OrderService;
import com.gulimall.service.utils.PageUtils;

/**
 * 订单
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:01:26
 */
@RestController
@RequestMapping("order/order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@GetMapping("/test/createOrder")
	public String createOrderTest() {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderSn(UUID.randomUUID().toString());
		orderEntity.setModifyTime(new Date());
		//给MQ发送消息
		rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", orderEntity);
		return "ok";
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = orderService.queryPage(params);

		return R.ok().put("page", page);
	}

	@PostMapping("/listWithItems")
	public R listWithItems(@RequestBody Map<String, Object> params) {
		PageUtils page = orderService.queryPageWithItems(params);
		return R.ok().setData(page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id) {
		OrderEntity order = orderService.getById(id);

		return R.ok().put("order", order);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")

	public R save(@RequestBody OrderEntity order) {
		orderService.save(order);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")

	public R update(@RequestBody OrderEntity order) {
		orderService.updateById(order);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		orderService.removeByIds(Arrays.asList(ids));
		return R.ok();
	}

	@GetMapping("/status/{orderSn}")
	public CommonResult getOrderStatus(@PathVariable("orderSn") String orderSn) {
		OrderEntity orderEntity = orderService.getOrderByOrderSn(orderSn);
		return CommonResult.ok(orderEntity);
	}

}
