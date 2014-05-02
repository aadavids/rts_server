package com.rts.server.interactions;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rts.server.orders.Order;
import com.rts.server.orders.Order.OrderType;
import com.rts.server.unit.management.GameUnits;

/**
 * 
 * Holds references to all objects in the game
 * 
 */
public class OrderTimeMatrix {

	private static final Logger log = Logger.getLogger(OrderTimeMatrix.class);
	private final OrderQueue orderQueue;
	private final OrderService orderService;
	private final GameMap gameMap;
	private final GameUnits gameDatabase;
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
		log.error("could not find the OrderList specified by the type "
				+ pOrderType.toString());
		return null;
	}

	/**
	 * Adds order to the required list
	 * 
	 * @param pOrder
	 * @param pOrderType
	 * @return the unique id of the order
	 */
	public Long addOrder(Order pOrder, OrderType pOrderType) {
		log.info("adding order to list: " + pOrderType.toString());
		findByType(pOrderType).add(pOrder);
		pOrder.setSubscritables(orderQueue, gameMap, gameDatabase);
		return pOrder.activate();
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

	/**
	 * Add a list of the specified type
	 * 
	 * @param pOrderType
	 *            the of list type to be added
	 */
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
