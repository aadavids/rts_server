package com.rts.server.interactions;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rts.server.orders.Order;
import com.rts.server.orders.Order.OrderType;
import com.rts.server.unit.management.GameUnits;

public class OrderTimeMatrix {

	private static final Logger log = Logger.getLogger(OrderTimeMatrix.class);
	private final OrderQueue orderQueue;
	private final GameMap gameMap;
	private final com.rts.server.unit.management.GameUnits gameDatabase;
	private final OrderService orderService;
	protected Map<OrderType, OrderList> orderLists;

	public OrderTimeMatrix(int pPoolsize, GameMap pGameMap,
			GameUnits pGameDatabase) {
		orderQueue = new OrderQueue();
		orderService = new OrderService(orderQueue, pPoolsize);
		orderLists = new HashMap<OrderType, OrderList>();
		gameMap = pGameMap;
		gameDatabase = pGameDatabase;
		orderService.run();
	}

	private OrderList findByType(OrderType pOrderType) {
		log.info("finding list: " + pOrderType.toString());
		if (orderLists.containsKey(pOrderType)) {
			return orderLists.get(pOrderType);
		}
		return orderLists.get(0);
	}

	public Long addOrder(Order pNewOrder, OrderType pOrderType) {
		log.info("adding order to list: " + pOrderType.toString());
		findByType(pOrderType).add(pNewOrder);
		pNewOrder.setSubscritables(orderQueue, gameMap, gameDatabase);
		return pNewOrder.activate();
	}

	/**
	 * remove all geo/general subscriptions
	 * 
	 * @param pOrder
	 * @param pOrderType
	 */
	public void removeOrder(Order pOrder, OrderType pOrderType) {
		log.info("removing order from list: " + pOrderType.toString());
		findByType(pOrderType).remove(pOrder);
		orderQueue.remove(pOrder);
	}

	public void registerOrderType(OrderType pOrderType) {
		log.info("registering order list: " + pOrderType.toString());
		orderLists.put(pOrderType, new OrderList(pOrderType));
	}

	public GameUnits getGameDatabase() {
		return gameDatabase;
	}

	public Integer queuePeek() {
		return orderQueue.numEnqueued();
	}
}
