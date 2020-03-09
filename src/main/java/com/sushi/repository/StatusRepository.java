package com.sushi.repository;

import org.springframework.data.repository.CrudRepository;

import com.sushi.entity.Status;

public interface StatusRepository extends CrudRepository<Status, Integer> {
	Status findByName(String name);
}
