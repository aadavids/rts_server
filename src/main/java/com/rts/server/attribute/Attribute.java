package com.rts.server.attribute;

import java.util.Observable;

public abstract class Attribute extends Observable {

	protected final AttributeType type;
	private Object value;

	protected Attribute(AttributeType type, Object value) {
		this.type = type;
		this.value = value;
	}

	public AttributeType getType() {
		return type;
	}

	protected void setValue(Object value) {
		this.value = value;
		setChanged();
		notifyObservers();
	}

	protected Object getValue() {
		return this.value;
	}
}
