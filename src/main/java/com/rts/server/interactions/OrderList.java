package com.rts.server.interactions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.rts.server.orders.Order;
import com.rts.server.orders.Order.OrderType;

public class OrderList {
	private static AtomicLong uniqueOrderId = new AtomicLong();
	private List<Order> orderQueue = new ArrayList<Order>();
	private OrderType orderType;

	OrderList(OrderType pOrderType) {
		orderType = pOrderType;
	}

	public long add(Order pOrder) {
		pOrder.setId(getAndIncrement());
		orderQueue.add(pOrder);
		return pOrder.getId();
	}

	public void remove(Order pOrder) {
		orderQueue.remove(pOrder);
	}

	private synchronized long getAndIncrement() {
		return uniqueOrderId.getAndIncrement();
	}

	public OrderType getOrderType() {
		return orderType;
	}

}
