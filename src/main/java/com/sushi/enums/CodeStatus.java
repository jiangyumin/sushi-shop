package com.sushi.enums;

public enum CodeStatus {
	// code status list
	SUCCESS(0), 
	FAILURE(1); 

	private final int value; // Private variable

	CodeStatus(int value) {     // Constructor
	      this.value = value;
	}

	public int getValue() { // Getter
		return value;
	}
}
