package com.rts.server.orders;

import java.awt.Point;

import com.rts.server.unit.Unit;

public class UnitCreationOrder extends Order {
	private static final long TICK_TIME = 1000;

	private Unit unit;
	private Point position;

	private long remainingBuildTime;

	public UnitCreationOrder(Unit pUnit, Point pPosition/* ,Player pPlayer */) {
		unit = pUnit;
		position = pPosition;

		remainingBuildTime = pUnit.getBuildTime() * 1000;
	}

	public void activation() {
		orderQueue.add(this, TICK_TIME);
	}

	@Override
	public void run() {
		remainingBuildTime -= TICK_TIME;

		if (remainingBuildTime >= TICK_TIME) {
			orderQueue.add(this, TICK_TIME);
		} else if (remainingBuildTime > 0) {
			orderQueue.add(this, remainingBuildTime);
		} else {
			gameDatabase.createUnit(unit, position);
			// create the unit here,
			// getgameQueue().Database.addUnit(unit,position/*,possibleBuildArea*/);
		}
	}
}
