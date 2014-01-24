package com.rts.server.interactions;

import junit.framework.TestCase;

import org.junit.Test;

import com.rts.server.orders.Order.OrderType;
import com.rts.server.unit.management.GameUnits;

public class BattleTest extends TestCase {

	@Test
	public void testCreateUnit() throws Exception {
		OrderTimeMatrix orders = new OrderTimeMatrix(4, new GameMap(),
				new GameUnits());
		orders.registerOrderType(new OrderList(), OrderType.UnitCreation);
		Battle.parseOrder(orders, "create marine 123 456");

		assertEquals(1, (int) orders.queuePeek());
	}

	@Test
	public void testInvalidCommand() throws Exception {

	}
}
