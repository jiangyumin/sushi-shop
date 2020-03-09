package com.sushi.service;

import com.sushi.entity.Sushi;

public interface SushiService {
	
	/**
	 * Get the Sushi by Sushi Name. 
	 *
	 * @param name. Sushi Name.
	 * @return the Sushi entity.
	 */
	Sushi getSushiByName(String sushiName);	
}
