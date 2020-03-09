package com.sushi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sushi.entity.Sushi;
import com.sushi.repository.SushiRepository;
import com.sushi.service.SushiService;

@Service
public class SushiServiceImpl implements SushiService {
	
	@Autowired SushiRepository sushiRepository;

	@Override
	public Sushi getSushiByName(String sushiName) {
		return sushiRepository.findByName(sushiName);
	}

}
