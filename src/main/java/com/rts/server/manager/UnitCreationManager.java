package com.rts.server.manager;

import java.util.Arrays;

import com.rts.server.attribute.AttributeType;
import com.rts.server.attribute.BuildEffort;
import com.rts.server.unit.Unit;
import com.rts.server.unit.UnitCreator;

public class UnitCreationManager extends Managaer {

	public UnitCreationManager() {
		requiredTypes = Arrays.asList(AttributeType.BuildEffort);
	}

	public long getCreateTime(Unit pUnit, UnitCreator pUnitCreator) {
		long buildEffort = ((BuildEffort) pUnit
				.getAttribute(AttributeType.BuildEffort)).getBuildEffort();

		return (long) Math.ceil(buildEffort / pUnitCreator.getEffortPerTick());
	}

}
