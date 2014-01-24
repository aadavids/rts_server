package com.rts.server.unit;

import java.awt.Point;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

public abstract class Unit {
	private static final AtomicInteger uniqueIdCounter = new AtomicInteger();
	private static final Logger log = Logger.getLogger(Unit.class);

	public enum UnitType {
		Marine
	}

	public final int uniqueId;
	public final int buildTime;
	protected long updateOrder;
	private final UnitType unitType;

	private Point position;

	public Unit(UnitType pUnitType, int pBuildTime) {
		uniqueId = uniqueIdCounter.getAndIncrement();
		buildTime = pBuildTime;
		unitType = pUnitType;

		log.debug(String.format("unit initialized: %d %d %s", uniqueId,
				buildTime, unitType.toString()));
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point pPosition) {
		position.setLocation(pPosition);
	}

	public void update() {
		isUpdateRequired();
	}

	public boolean isUpdateRequired() {
		return false;
	}

	public UnitType getUnitType() {
		return unitType;
	}

	public int getBuildTime() {
		return buildTime;
	}

	public String toString() {
		return unitType.toString();

	}
}