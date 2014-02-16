package com.rts.server.interactions;

import java.util.concurrent.PriorityBlockingQueue;

import org.apache.log4j.Logger;

import com.rts.server.orders.Order;

public class OrderQueue {
	private static final Logger log = Logger.getLogger(OrderQueue.class);
	private final PriorityBlockingQueue<OEWrapper> inner;

	private class OEWrapper implements Comparable<OEWrapper> {

		public final Order order;
		public final long executionTime;

		public OEWrapper(Order pOrder, long pExecutionTime) {
			order = pOrder;
			executionTime = pExecutionTime;
		}

		public int compareTo(OEWrapper pOther) {
			return Long.compare(executionTime, pOther.executionTime);
		}

		@Override
		public boolean equals(Object pOther) {
			if (pOther instanceof OEWrapper) {
				return order.equals(((OEWrapper) pOther).order);
			} else if (pOther instanceof Order) {
				return order.equals(pOther);
			}
			return false;
		}
	}

	public OrderQueue() {
		inner = new PriorityBlockingQueue<OEWrapper>();
	}

	public void add(Order pOrder, long pExecutionWait) {
		log.info("Putting order on queue");
		inner.put(new OEWrapper(pOrder, System.currentTimeMillis()
				+ pExecutionWait));
	}

	public void remove(Order pOrder) {
		log.info("Removing order from queue");
		inner.remove(pOrder);
	}

	public boolean ready() {
		OEWrapper order = inner.peek();
		return order != null
				&& order.executionTime <= System.currentTimeMillis();
	}

	public int numEnqueued() {
		return inner.size();
	}

	public Order poll() {
		log.info("Polling the queue");
		OEWrapper orderWrapper = inner.poll();
		if (orderWrapper == null) {
			return null;
		}
		return orderWrapper.order;
	}

}
