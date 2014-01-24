package com.rts.server.orders;

import com.rts.server.interactions.GameMap;
import com.rts.server.interactions.OrderQueue;
import com.rts.server.unit.management.GameUnits;

public abstract class Order implements Runnable {

	public enum OrderType {
		UnitCreation, Update, Movement
	}

	private long id;
	protected OrderQueue orderQueue;
	protected GameMap map;
	protected GameUnits gameDatabase;

	// protected List<Subscription> subscription = new
	// ArrayList<Subscription>();

	public Order() {
		orderQueue = null;
	}

	public long getId() {
		return id;
	}

	public void setId(long pOrderId) {
		id = pOrderId;
	}

	public void setSubscritables(OrderQueue pOrderQueue, GameMap Map,
			GameUnits GameDatabase) {
		map = Map;
		gameDatabase = GameDatabase;
		orderQueue = pOrderQueue;
	}

	public void run() {
	}

	protected void activation() {
	}

	public Long activate() {
		activation();
		return id;
	}

	// public void addSubscription(){}
	// public void removeSubscription(long sub_id){}
}