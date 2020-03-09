package com.sushi.service;

import com.sushi.entity.Status;

public interface StatusService {
	
	/**
	 * Get the order status by status name. 
	 *
	 * @param name. Status Name.
	 * @return the OrderStatus entity.
	 */
	Status getOrderStatusByName(String name);	
}
