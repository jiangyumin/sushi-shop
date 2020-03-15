package com.sushi.controller;

import java.util.Map;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sushi.SushiShopApplication;
import com.sushi.entity.Order;
import com.sushi.entity.OrderTask;
import com.sushi.entity.Sushi;
import com.sushi.enums.OrderStatus;
import com.sushi.service.OrderService;
import com.sushi.service.StatusService;
import com.sushi.service.SushiService;
import com.sushi.task.TaskProcessor;
import com.sushi.utils.ResponseUtils;

@RestController
@RequestMapping(path="/api")
public class OrderController {
	
	private static final Logger log = LoggerFactory.getLogger(SushiShopApplication.class);
	
	@Autowired
	OrderService orderService;
	@Autowired
	SushiService sushiService;
	@Autowired
	StatusService statusService;
	@Autowired
	TaskProcessor taskProcessor;

	@PostMapping(path="/orders")
	public Map<String, Object> createOrder(@RequestParam(value = "sushi_name") String sushiName) {
		log.info("-- Start to create order for sushi name {} --", sushiName);
		Order order = orderService.createOrder(sushiName);
		log.info("-- Created order: " + order);
		Sushi sushi = sushiService.getSushiByName(sushiName);
		
		//start the thread to process the application
		taskProcessor.submit(new OrderTask(order, sushi.getTimeToMake(),
				orderService, statusService));
		
		return ResponseUtils.getMessage(order, OrderStatus.CREATED.name());
	}
	
	@PutMapping(path="/orders/cancel/{order_id}")
	public Map<String, Object> cancelOrder(@PathVariable("order_id") Integer orderId) {
		log.info("-- Start to cancel order for id {} --", orderId);
		Order order = orderService.getOrderById(orderId);
		
		if (order != null) {
			taskProcessor.cancelOrder(orderId);
			
			order.setStatusId(statusService.getOrderStatusByName(
					OrderStatus.CANCELLED.getValue()).getId());
			orderService.updateOrder(order);
			log.info("-- Cancelled order: " + order);
		}
		return ResponseUtils.getMessage(
				order != null ? order.getCode() : 1, OrderStatus.CANCELLED.name());
	}
	
	@PutMapping(path="/orders/pause/{order_id}")
	public Map<String, Object> pauseOrder(@PathVariable("order_id") Integer orderId) {
		log.info("-- Start to pause order for id {} --", orderId);
		Order order = orderService.getOrderById(orderId);
		
		if (order != null) {
			taskProcessor.pauseOrder(orderId);
			
			order.setStatusId(statusService.getOrderStatusByName(
					OrderStatus.PAUSED.getValue()).getId());
			orderService.updateOrder(order);
			log.info("-- Paused order: " + order);
		}
		
		return ResponseUtils.getMessage(
				order != null ? order.getCode() : 1, OrderStatus.PAUSED.name());
	}
	
	@PutMapping(path="/orders/resume/{order_id}")
	public Map<String, Object> resumeOrder(@PathVariable("order_id") Integer orderId) {
		log.info("-- Start to resume order for id {} --", orderId);
		Order order = orderService.getOrderById(orderId);
		
		if (order != null) {
			taskProcessor.resumeOrder(orderId);
			
			order.setStatusId(statusService.getOrderStatusByName(
					OrderStatus.RESUMED.getValue()).getId());
			orderService.updateOrder(order);
			log.info("-- Resumed order: " + order);
		}
		return ResponseUtils.getMessage(
				order != null ? order.getCode() : 1, OrderStatus.RESUMED.name());
	}
	
	@GetMapping(path="/orders/status")
	public Map<String, Object> getOrders() {
		log.info("-- Start to get all orders");
		return null;
	}
}
