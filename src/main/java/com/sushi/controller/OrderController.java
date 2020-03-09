package com.sushi.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sushi.SushiShopApplication;
import com.sushi.entity.Order;
import com.sushi.enums.OrderStatus;
import com.sushi.service.OrderService;
import com.sushi.utils.ResponseUtils;

@RestController
@RequestMapping(path="/api")
public class OrderController {
	
	private static final Logger log = LoggerFactory.getLogger(SushiShopApplication.class);
	
	@Autowired
	OrderService orderService;

	@PostMapping(path="/orders")
	public Map<String, Object> createOrder(@RequestParam(value = "sushi_name") String sushiName) {
		log.info("-- Start to create order for sushi name {} --", sushiName);
		Order order = orderService.createOrder(sushiName);
		log.info("-- Created order: " + order);
		
		return ResponseUtils.getMessage(order, OrderStatus.CREATED.name());
	}
}
