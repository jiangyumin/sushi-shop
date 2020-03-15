package com.sushi.task;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sushi.entity.OrderTask;

@Component
public class TaskProcessor {
	
	private static final Logger log = LoggerFactory.getLogger(TaskProcessor.class);
	
	private ThreadPoolExecutor executor = 
			  (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
	
	private ArrayList<OrderTask> orderTasks = new ArrayList<>(100);

	public ArrayList<OrderTask> getOrderTasks() {
		return orderTasks;
	}

	public void cancelOrder(Integer orderId) {
		OrderTask task = getOrderTask(orderId);
			
		if (task != null) {
			synchronized(task) {
				task.setTaskStatus(-1); // cancel the application
				task.notify();
			}
		}
	}

	public void pauseOrder(Integer orderId) {
		OrderTask task = getOrderTask(orderId);
		
		if (task != null) {
			task.setTaskStatus(0); // pause the application
		}
	}
	
	public void resumeOrder(Integer orderId) {
		OrderTask task = getOrderTask(orderId);
		
		if (task != null) {
			synchronized(task) {
				task.setTaskStatus(1); // resume the application
				task.notify();
			}
		}
	}
	
	private OrderTask getOrderTask(Integer orderId) {
		OrderTask task = null;
		if (orderTasks.size() > 0) {
			task = orderTasks.stream().filter(
					t -> t.getOrderId() == orderId).findFirst().orElse(null);
			log.info("-- Get task order from task list " + task.getOrderId());
		}
		return task;
	}
	
	public void submit(OrderTask orderTask) {
		this.executor.submit(orderTask);
		this.orderTasks.add(orderTask);
	}
}
