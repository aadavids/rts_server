package com.rts.server.manager;

import java.util.ArrayList;
import java.util.List;

import com.rts.server.attribute.AttributeType;
import com.rts.server.unit.Unit;

public abstract class Managaer {

	protected List<AttributeType> requiredTypes = new ArrayList<>();
	protected List<AttributeType> optionalTypes = new ArrayList<>();

	public boolean canBeApplied(Unit pUnit) {
		return pUnit.hasAttributes(requiredTypes);
	}

}
