package com.rts.server.server.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public abstract class OrderLists {
protected static AtomicLong Unique_Order_ID = new AtomicLong();
protected List<Order> Outstanding_Orders = new ArrayList<Order>();
protected final String name;


public OrderLists(String name){
	this.name = name;
}

public String getName(){
	return name;
}

public long add(Order order){
	order.setOrder_ID(GetandIncrement());
	Outstanding_Orders.add(order);
	return order.getOrder_ID();
}

public void remove(Order order){
	Outstanding_Orders.remove(order);
}

protected synchronized long GetandIncrement(){
	return Unique_Order_ID.getAndIncrement();
}
}
