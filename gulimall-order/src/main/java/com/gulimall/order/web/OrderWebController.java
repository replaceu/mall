package com.gulimall.order.web;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gulimall.common.utils.R;
import com.gulimall.order.service.OrderService;
import com.gulimall.order.vo.OrderConfirmVo;
import com.gulimall.order.vo.OrderSubmitResponseVo;
import com.gulimall.order.vo.OrderSubmitVo;

/**
 * @author aqiang9  2020-09-11
 */
@Controller
public class OrderWebController {
	@Autowired
	OrderService orderService;

	@GetMapping("/list.html")
	public String list() {
		return "list";
	}

	@GetMapping("/detail.html")
	public String detail() {
		return "detail";
	}

	@GetMapping("/confirm.html")
	public String confirm(HttpServletRequest request) throws ExecutionException, InterruptedException {
		//展示订单的数据
		OrderConfirmVo orderConfirm = orderService.confirmOrder();
		return "confirm";
	}

	@GetMapping("/pay.html")
	public String pay() {
		return "pay";
	}

	@PostMapping("/submitOrder")
	public String submitOrder(OrderSubmitVo orderSubmit) {
		//去创建订单，验证令牌，验价，锁库存
		OrderSubmitResponseVo submitResponse = orderService.submitOrder(orderSubmit);

		if (submitResponse.getCode() == 0) {
			return "pay";
		} else {
			return "redirect:http://order.gulimall.com/confirm";
		}

	}

	@GetMapping("/pay.html")
	public R getOrderPay(String orderSn) {
		return orderService.getOrderPayByOrderSn(orderSn);
	}

	@PostMapping("/pay.html")
	public void updateOrderStatus(String outTradeNo, Integer code) {
		orderService.updateOrderStatus(outTradeNo, code);
	}
}
