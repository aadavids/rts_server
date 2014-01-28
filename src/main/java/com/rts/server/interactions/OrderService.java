package com.rts.server.interactions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * A thread that runs and waits for orders to be popped from the order queue
 * 
 */
public class OrderService implements Runnable {

	private static final Logger log = Logger.getLogger(OrderService.class);
	private static final long POLL_PERIOD = 10;
	private static final TimeUnit POLL_PERIOD_UNIT = TimeUnit.MILLISECONDS;

	private final OrderQueue queue;
	private final ScheduledExecutorService poll;
	private final ExecutorService pool;

	/**
	 * 
	 * @param pQueue
	 *            the queue of orders that this service waits on
	 * @param pPoolSize
	 *            number of threads in the pool
	 */
	public OrderService(OrderQueue pQueue, int pPoolSize) {
		queue = pQueue;
		poll = Executors.newScheduledThreadPool(1);
		pool = Executors.newFixedThreadPool(pPoolSize);
	}

	/**
	 * Periodically checks if the next order is ready. If so, run it
	 */
	public void run() {
		poll.scheduleAtFixedRate(new Runnable() {
			public void run() {
				if (queue.ready()) {
					log.info("Order ready, sending to pool for execution");
					pool.execute(queue.poll());
					log.info("Order added to pool for execution");
				}
			}
		}, 0, POLL_PERIOD, POLL_PERIOD_UNIT);
	}
}
