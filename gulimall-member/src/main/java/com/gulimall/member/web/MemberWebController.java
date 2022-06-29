package com.gulimall.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.gulimall.member.feign.OrderFeignService;

@Controller
public class MemberWebController {

	@Autowired
	OrderFeignService orderFeignService;
}
