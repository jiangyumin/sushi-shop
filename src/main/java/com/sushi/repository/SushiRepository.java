package com.sushi.repository;

import org.springframework.data.repository.CrudRepository;

import com.sushi.entity.Sushi;

public interface SushiRepository extends CrudRepository<Sushi, Integer> {
	Sushi findByName(String sushiName);
}
