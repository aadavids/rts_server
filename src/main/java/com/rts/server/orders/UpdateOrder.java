package com.rts.server.orders;

import com.rts.server.unit.Unit;

public class UpdateOrder extends Order {
	private static final long TICK_TIME = 1000;
	private final Unit unit;

	public UpdateOrder(Unit pUnit) {
		unit = pUnit;
	}

	public void run() {
		unit.update();
		if (unit.isUpdateRequired()) {
			orderQueue.add(this, TICK_TIME);
		}
	}
}
