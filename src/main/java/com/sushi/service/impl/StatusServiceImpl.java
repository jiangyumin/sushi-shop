package com.sushi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sushi.entity.Status;
import com.sushi.repository.StatusRepository;
import com.sushi.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {
	
	@Autowired StatusRepository statusRepository;

	@Override
	public Status getOrderStatusByName(String name) {
		return statusRepository.findByName(name);
	}

}
