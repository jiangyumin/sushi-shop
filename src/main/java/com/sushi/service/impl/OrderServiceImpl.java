package com.sushi.service.impl;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.sushi.entity.Order;
import com.sushi.entity.Status;
import com.sushi.entity.Sushi;
import com.sushi.enums.CodeStatus;
import com.sushi.enums.OrderStatus;
import com.sushi.repository.OrderRepository;
import com.sushi.service.OrderService;
import com.sushi.service.StatusService;
import com.sushi.service.SushiService;

@Service
public class OrderServiceImpl implements OrderService {
	
	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired OrderRepository orderRepository;
	@Autowired SushiService sushiService;
	@Autowired StatusService statusService;
	
	@Override
	public Order getOrderById(Integer id) {
		return orderRepository.findById(id).orElse(null); 
	}

	@Override
	public Order createOrder(String sushiName) {
		Order order = new Order();
		
		Sushi sushi = sushiService.getSushiByName(sushiName);
		log.info("-- Get Sushi by Name: " + sushi);
		Status status = statusService.getOrderStatusByName(OrderStatus.CREATED.getValue());
		log.info("-- Get Status by Name: " + status);
		
		if (sushi == null) {
			order.setCode(CodeStatus.FAILURE.getValue());
			return order;
		} 
		
		order.setSushiId(sushi.getId());
		order.setStatusId(status.getId());
		order.setCreatedAt(new Timestamp(new Date().getTime()));

		if (saveOrder(order) != 0) {
			order.setCode(CodeStatus.FAILURE.getValue());
		}
		
		return order;
	}

	@Override
	public int updateOrder(Order order) {
		return saveOrder(order);
	}

	@Override
	public List<Order> ListOrders() {
		List<Order> orderList = null;
		Iterable<Order> orders = orderRepository.findAll();		
		
		if (orders != null) {
			// convert the Iterable to ArrayList
			orderList = Lists.newArrayList(orderRepository.findAll());
		} else {
			orderList = Collections.emptyList();
		}
				
		return orderList;
	}
	
	private int saveOrder(Order order) {
		int code = 0;
		try {
			orderRepository.save(order);
		} catch (Exception e) {
			e.printStackTrace();
			code = 1;
		}
		return code;
	}

}
