package com.rts.server.units;

import java.awt.Point;
import java.util.concurrent.atomic.AtomicInteger;

import com.rts.server.units.Unit;

public abstract class Unit {
	private static final AtomicInteger uniqueIdCounter = new AtomicInteger();
	
	public final int uniqueId, buildTime;
	protected long updateOrder;
	private final String name;
	
	private Point position;
	
	public Unit (String name, int buildTime)	{
		uniqueId = uniqueIdCounter.getAndIncrement();
		this.buildTime = buildTime;
		this.name = name;
	}
	
	public Point getPosition () {
		return position;
	}

	public void setPosition (Point newPosition) {
		position.setLocation(newPosition);
	}
	
	public void update(){isupdaterequired();}
	public boolean isupdaterequired(){return false;}
	
	public String getName() {
		return name;
	}
	public int getbuildTime() {
		return buildTime;
	}
	public String toString() {
		return this.getName();
		
	}
}