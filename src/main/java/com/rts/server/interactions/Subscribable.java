package com.rts.server.interactions;

import java.util.Set;

import com.rts.server.attribute.Attribute;
import com.rts.server.attribute.AttributeType;

public interface Subscribable {
	public Attribute getAttribute(AttributeType attributeType);

	public Set<AttributeType> listAttributes();
}
