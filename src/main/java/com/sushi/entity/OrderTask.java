package com.sushi.entity;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sushi.enums.OrderStatus;
import com.sushi.service.OrderService;
import com.sushi.service.StatusService;

public class OrderTask implements Callable<Integer> {
	
	private static final Logger log = LoggerFactory.getLogger(OrderTask.class);
	
	private int timeSpent = 0;
	
	private int orderId = 0;
	
	@JsonIgnore
	private int taskStatus = 1;
	
	@JsonIgnore
	private int timeToMake = 0;
	
	@JsonIgnore
	private String orderStatus = null;
	
	@JsonIgnore
	private OrderService orderService;
	
	@JsonIgnore
	private StatusService statusService;
	
	@JsonIgnore
	private Order order = null;

	public OrderTask(Order order, int timeToMake, 
			OrderService orderService, StatusService statusService) {
		this.timeToMake = timeToMake;
		this.orderId = order.getId();
		this.orderService = orderService;
		this.statusService = statusService;
		this.order = order;
	}

	@Override
	public Integer call() throws Exception {
		log.info("-- Start to prepare the food for order " + order.getId());
		log.info("-- Time To Make is " + timeToMake);
		order.setStatusId(statusService.getOrderStatusByName(
				OrderStatus.IN_PROGRESS.getValue()).getId());
		orderService.updateOrder(order);
		
		while (timeSpent < timeToMake) {
			Thread.sleep(1000);
			++timeSpent;
			// taskStatus == 0 : Pause the process
			if (taskStatus == 0) {
				order.setStatusId(statusService.getOrderStatusByName(
				OrderStatus.PAUSED.getValue()).getId());
				orderService.updateOrder(order);
				this.wait();
			}
			// taskStatus == 0 : Cancel the process
			if (taskStatus == -1) {
				order.setStatusId(statusService.getOrderStatusByName(
						OrderStatus.CANCELLED.getValue()).getId());
						orderService.updateOrder(order);
				Thread.interrupted();
			}
		}
		order.setStatusId(statusService.getOrderStatusByName(
						OrderStatus.FINISHED.getValue()).getId());
		orderService.updateOrder(order);
		log.info("-- Finish To Make the Food, Time spent is " + timeSpent);
		
		return timeSpent;
	}

	public int getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}

	public int getTimeToMake() {
		return timeToMake;
	}

	public void setTimeToMake(int timeToMake) {
		this.timeToMake = timeToMake;
	}

	public int getTimeSpent() {
		return timeSpent;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public StatusService getStatusService() {
		return statusService;
	}

	public void setStatusService(StatusService statusService) {
		this.statusService = statusService;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
