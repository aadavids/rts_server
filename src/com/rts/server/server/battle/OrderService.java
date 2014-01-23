package com.rts.server.server.battle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderService implements Runnable {
	private static final long POLL_PERIOD = 10;
	private static final TimeUnit POLL_PERIOD_UNIT = TimeUnit.MILLISECONDS;
	
	private final OrderQueue queue;
	private final ScheduledExecutorService poll;
	private final ExecutorService pool;

	public OrderService(OrderQueue queue, int poolSize) {
		this.queue = queue;
		this.poll = Executors.newScheduledThreadPool(1);
		this.pool = Executors.newFixedThreadPool(poolSize);
	}

	// Periodically checks if the next order is ready. If so, run it
	public void run() {	
		poll.scheduleAtFixedRate(
			new Runnable() {
				public void run() {
					if(queue.ready()) { 
						Battle.Log.logln("Engine", "Order ready, sending to pool for execution");
						pool.execute(queue.poll());
						Battle.Log.logln("Engine", "Order added to pool for execution");
					}
				}
			},
			0,
			POLL_PERIOD,
			POLL_PERIOD_UNIT
			);
	}
}

