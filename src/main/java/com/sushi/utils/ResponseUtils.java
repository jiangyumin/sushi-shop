package com.sushi.utils;

import java.util.Map;
import java.util.TreeMap;

import com.sushi.constants.SushiConstants;
import com.sushi.entity.Order;
import com.sushi.enums.OrderStatus;

public class ResponseUtils {
	
	public static Map<String, Object> getMessage(Order order, String orderStatus) {
		Map<String, Object> msg = new TreeMap<>();
		
		msg.put(SushiConstants.MSG, geMessageByCodeAndStatus(order.getCode(), orderStatus));
		msg.put(SushiConstants.CODE, order.getCode());
		msg.put(SushiConstants.ORDER, order);
		
		return msg;
	}
	
	public static Map<String, Object> getMessage(Integer code, String orderStatus) {
		Map<String, Object> msg = new TreeMap<>();
		
		msg.put(SushiConstants.MSG, geMessageByCodeAndStatus(code, orderStatus));
		msg.put(SushiConstants.CODE, code);
		
		return msg;
	}
	
	// get the return msg by the code and order status
	private static String geMessageByCodeAndStatus(int code, String orderStatus) {
		String msg = null;
		OrderStatus status = Enum.valueOf(OrderStatus.class, orderStatus);
		
		switch (status) {
	        case CREATED:
	        	msg = code == 0 ? "Order submitted" : "";
	            break;
	        case CANCELLED:
	            msg = code == 0 ? "Order cancelled" : "";
	            break;
	        case PAUSED:
	            msg = code == 0 ? "Order paused" : "";
	            break;
	        case RESUMED:
	            msg = code == 0 ? "Order resumed" : "";
	            break;
			default:
				break;
		}
		
		return msg;
	}

}
