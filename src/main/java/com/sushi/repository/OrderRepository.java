package com.sushi.repository;

import org.springframework.data.repository.CrudRepository;

import com.sushi.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
	
}
