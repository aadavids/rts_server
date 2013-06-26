package server_battle;

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

	public void run() {
		poll.scheduleAtFixedRate(
			new Runnable() {
				public void run() {
					if(queue.ready()) {
						pool.execute(queue.poll());
					}
				}
			},
			0,
			POLL_PERIOD,
			POLL_PERIOD_UNIT
			);
	}
}

