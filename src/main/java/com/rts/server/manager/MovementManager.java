package com.rts.server.manager;

import java.util.Arrays;

import com.rts.server.attribute.AttributeType;
import com.rts.server.attribute.Movement;
import com.rts.server.unit.Unit;

public class MovementManager extends Managaer {

	public MovementManager() {
		requiredTypes = Arrays.asList(AttributeType.Movement);
	}

	public int getMoveSpeed(Unit pUnit) {
		return ((Movement) pUnit.getAttribute(AttributeType.Movement))
				.getSpeed();
	}
}
