package com.rts.server.unit.management;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.rts.server.unit.Unit;

public class GameUnits {
	private static final Logger log = Logger.getLogger(GameUnits.class);
	LinkedHashMap<Integer, Unit> unitList = new LinkedHashMap<Integer, Unit>();
	UnitMap unitLocation = new UnitMap();

	public void createUnit(Unit unit, Point position) {
		log.info("creating unit: " + unit.uid);
		unitLocation.addUnit(unit.uid, position);
		unitList.put(unit.uid, unit);
		log.info("check: " + unitList.containsKey(unit.uid) + ", position: ("
				+ position.toString() + ", " + position.y + ")");
	}

	public void removeUnit(int uniqueId) {
		log.info("removing unit: " + Integer.toString(uniqueId));
		unitLocation.deleteUnit(uniqueId);
		unitList.remove(uniqueId);
	}

	public boolean moveUnit(int uniqueId, Point newPosition) {
		log.info("moving unit: " + Integer.toString(uniqueId));

		boolean moved = unitLocation.moveUnit(uniqueId, unitList.get(uniqueId)
				.getPosition(), newPosition);

		if (moved) {
			unitList.get(uniqueId).setPosition(newPosition);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param position
	 * @return list of units at the specified position
	 */
	public List<Unit> getUnits(Point position) {
		log.info("looking for units at position: " + position.toString());
		Set<Integer> uniqueIds = unitLocation.getUnits(position);
		ArrayList<Unit> units = new ArrayList<Unit>(uniqueIds.size());
		for (Integer uniqueId : uniqueIds) {
			units.add(unitList.get(uniqueId));
		}
		return units;
	}

	public Unit getUnit(int uniqueId) {
		log.info("checking unit: " + uniqueId);
		return unitList.get(uniqueId);
	}

	public void checkUnit(int uniqueId) {
		log.info("checking unit: " + uniqueId);
		log.info(Integer.toString(uniqueId) + " is at postion "
				+ unitList.get(uniqueId).getPosition().toString());
	}
}
