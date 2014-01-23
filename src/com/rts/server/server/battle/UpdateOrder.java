package com.rts.server.server.battle;

import com.rts.server.units.Unit;

public class UpdateOrder extends Order{
private static final long TICK_TIME = 1000;
private final Unit unit;

public UpdateOrder(Unit Unit){
	this.unit = Unit;
}

public void run(){
	unit.update();
	if(unit.isupdaterequired()){
		order_Queue.add(this, TICK_TIME);
	}
}
}
