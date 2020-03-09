package com.sushi.enums;

public enum OrderStatus {
	// order status list
	CREATED("created"), 
	IN_PROGRESS("in-progress"), 
	PAUSED("paused"), 
	RESUMED("in-progress"), 
	FINISHED("finished"), 
	CANCELLED("cancelled"); 

	private final String value; // Private variable

	OrderStatus(String value) {     // Constructor
	      this.value = value;
	}

	public String getValue() { // Getter
		return value;
	}
}
