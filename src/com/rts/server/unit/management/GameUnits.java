package com.rts.server.unit.management;

import java.awt.Point;
import java.util.*;

import com.rts.server.server.battle.Battle;
import com.rts.server.units.Unit;


public class GameUnits {
	LinkedHashMap<Integer, Unit> unitList = new LinkedHashMap<Integer, Unit>();
	UnitMap unitLocation = new UnitMap();
	
	public void createUnit(Unit unit, Point position) {
		Battle.Log.logln("UnitStatus","Creating unit: " + unit.getName());
		unitLocation.addUnit(unit.uniqueId, position);
		unitList.put(unit.uniqueId, unit);
		Battle.Log.logln("UnitStatus", "Check: " + unitList.containsKey(unit.uniqueId) + ", stuff: " + unitList.get(unit.uniqueId).getName());
	}
	
	public void removeUnit (int uniqueId) {
		Battle.Log.logln("UnitStatus","Removing unit: " + Integer.toString(uniqueId));
		unitLocation.deleteUnit(uniqueId);
		unitList.remove(uniqueId);
	}
	
	public void moveUnit (int uniqueId, Point newPosition) {
		Battle.Log.logln("UnitStatus","Moving unit: " + Integer.toString(uniqueId));
		
		unitLocation.moveUnit(uniqueId, unitList.get(uniqueId).getPosition(), newPosition);
		unitList.get(uniqueId).setPosition(newPosition);
		
		//set unit location in unit class 
		//unitList.get(unit.uniqueId).set(newPosition);
	}
	
	public ArrayList<Unit> getUnits (Point position) { // gets units at position
		Battle.Log.logln("UnitStatus","Looking up units at position: " + position.toString());
		Set<Integer> uniqueIds = unitLocation.getUnits(position);
		ArrayList<Unit> units = new ArrayList<Unit>(uniqueIds.size());
		for (Integer uniqueId : uniqueIds) {
			units.add(unitList.get(uniqueId));
		}
		return units;
	}
	
	public Unit getUnit (int uniqueId) {
		Battle.Log.logln("UnitStatus","Moving unit: " + Integer.toString(uniqueId));
		return unitList.get(uniqueId);	
	}
}
