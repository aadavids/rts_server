package com.rts.server.server.battle;

import java.util.ArrayList;
import java.util.List;

public class OrderTimeMatrix {
	private final OrderQueue orderQueue;
	private final Map map;
	private final com.rts.server.unit.management.GameUnits gameDatabase;
	private final OrderService orderService;
	protected List<OrderLists> order_Lists;
	
	public OrderTimeMatrix(int poolsize, Map Map, com.rts.server.unit.management.GameUnits GameDatabase){
		orderQueue = new OrderQueue();
		orderService = new OrderService(orderQueue,poolsize);
		order_Lists = new ArrayList<OrderLists>();
		map = Map;
		gameDatabase = GameDatabase;
		orderService.run();
	}
	
	private OrderLists FindByName(String order_Listname){
		Battle.Log.logln("OrderStatus","Finding list: " + order_Listname);
		for(OrderLists x: order_Lists){
			if(x.getName() == order_Listname){
				return x;
			}
		}
		return order_Lists.get(0);
	}
	public Long AddOrder(Order newOrder, String order_Listname){
		Battle.Log.logln("OrderStatus","Adding Order to: " + order_Listname);
		FindByName(order_Listname).add(newOrder);
		newOrder.setSubscritables(orderQueue,map,gameDatabase);
		return newOrder.activate();
	}
	public void RemoveOrder(Order order, String order_Listname){
		Battle.Log.logln("OrderStatus","Removing Order from: " + order_Listname);
		FindByName(order_Listname).remove(order);
		orderQueue.remove(order);
		/*remove all its geo/general subscriptions*/
	}
	public void RegisterOrder_Type(OrderLists newOrder_Lists){
		Battle.Log.logln("OrderStatus","Registering Order Type: " + newOrder_Lists.getName());
		order_Lists.add(newOrder_Lists);
	}
	public com.rts.server.unit.management.GameUnits getGameDatabase(){
		return gameDatabase;
	}
	public Integer queuePeek(){
		return orderQueue.numEnqueued();
	}
}
