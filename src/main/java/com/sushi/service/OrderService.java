package com.sushi.service;

import java.util.List;

import com.sushi.entity.Order;

public interface OrderService {
	
	/**
	 * Get an order by ID. 
	 *
	 * @param id. Order ID.
	 * @return the Order entity.
	 */
	Order getOrderById(Integer id);

	/**
	 * Create an order with Sushi Name. 
	 *
	 * @param Order.
	 * @return the order entity with code status. code = 0 - Success; code = -1 - Failure
	 */
	Order createOrder(String Sushi);
	
	/**
	 * Update the order. 
	 *
	 * @param Order.
	 * @return the code of the order updating status. code = 0 - Success; code = -1 - Failure
	 */
	int updateOrder(Order order);
	
	/**
	 * List the orders. 
	 *
	 * @param Order list.
	 * @return the list of the order entities.
	 */
	List<Order> ListOrders();
	
	
}
