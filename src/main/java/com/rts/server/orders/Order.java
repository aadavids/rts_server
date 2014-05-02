package com.rts.server.orders;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.rts.server.attribute.Attribute;
import com.rts.server.attribute.AttributeType;
import com.rts.server.interactions.GameMap;
import com.rts.server.interactions.OrderQueue;
import com.rts.server.interactions.Subscribable;
import com.rts.server.unit.management.GameUnits;

public abstract class Order implements Runnable, Subscribable, Observer {

	public enum OrderType {
		UnitCreation, Update, Movement
	}

	private long id;
	protected OrderQueue orderQueue;
	protected GameMap map;
	protected GameUnits gameDatabase;
	protected ConcurrentHashMap<AttributeType, Attribute> attributes;

	public Order() {
		attributes = new ConcurrentHashMap<AttributeType, Attribute>();
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

	@Override
	public Attribute getAttribute(AttributeType attributeType) {
		return attributes.get(attributeType);
	}

	@Override
	public Set<AttributeType> listAttributes() {
		return attributes.keySet();
	}

	@Override
	public void run() {
	}

	@Override
	public void update(Observable obj, Object value) {
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