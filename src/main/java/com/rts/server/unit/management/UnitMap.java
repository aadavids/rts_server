package com.rts.server.unit.management;

import java.awt.Point;
import java.util.*;

import com.google.common.collect.HashMultimap;

/**
 * 
 * Grid that maps location to unique id
 * 
 */
public class UnitMap {
	// Map<Point, Integer> unitMap;
	HashMultimap<Point, Integer> unitMap; // TODO make threadsafe

	UnitMap() {
		unitMap = HashMultimap.create();
	}

	UnitMap(int x, int y, int expectedUnitStackSize) { // might want to
														// predefine map size
		unitMap = HashMultimap.create(x * y, expectedUnitStackSize);
	}

	public void addUnit(final int uniqueId, Point position) {
		unitMap.put(position, uniqueId);
	}

	public void moveUnit(final int uniqueId, Point oldPosition,
			Point newPosition) {
		unitMap.remove(oldPosition, uniqueId);
		unitMap.put(newPosition, uniqueId);
	}

	public Set<Integer> getUnits(final Point pPosition) {
		Set<Integer> unitIds = unitMap.get(pPosition);
		return unitIds;
	}

	public void deleteUnit(final int pUniqueId) {
	}
}
