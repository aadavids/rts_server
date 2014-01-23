package com.rts.server.server.battle;


import java.awt.Point;

import com.rts.server.units.Unit;


public class UnitCreationOrder extends Order {
	private static final long TICK_TIME = 1000;
	
	private Unit Unit;
	private Point Position;
	
	private long remainingBuildTime;
	
	public UnitCreationOrder(Unit unit, Point position/*,Player player*/){
		this.Unit = unit;
		this.Position = position;
		
		this.remainingBuildTime = unit.getbuildTime()*1000;
	}
	public void activation(){
		order_Queue.add(this, TICK_TIME);
	}
	
	@Override
	public void run() {
		remainingBuildTime -= TICK_TIME;
		
		if(remainingBuildTime >= TICK_TIME) {
			order_Queue.add(this, TICK_TIME);
		} else if (remainingBuildTime > 0) {
			order_Queue.add(this, remainingBuildTime);
		} else {
			gameDatabase.createUnit(Unit, Position);
			//create the unit here, getgameQueue().Database.addUnit(Unit,Position/*,possibleBuildArea*/);
		}
	}	
}
