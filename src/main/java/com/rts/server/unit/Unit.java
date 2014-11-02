package com.rts.server.unit;

import java.awt.Point;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.rts.server.attribute.Attribute;
import com.rts.server.attribute.AttributeType;
import com.rts.server.interactions.Subscribable;

public abstract class Unit implements Subscribable {
	private static final AtomicInteger uidCounter = new AtomicInteger();
	protected ConcurrentHashMap<AttributeType, Attribute> attributes;
	// private static final Logger log = Logger.getLogger(Unit.class);

	public final int uid;
	private Point position;

	public Unit() {
		uid = uidCounter.getAndIncrement();
		position = new Point();
		attributes = new ConcurrentHashMap<>();
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point pPosition) {
		position.setLocation(pPosition);
	}

	@Override
	public Attribute getAttribute(AttributeType pType) {
		return attributes.get(pType);
	}

	@Override
	public Set<AttributeType> listAttributes() {
		return attributes.keySet();
	}

	public void addAttribute(Attribute pAttribute) {
		attributes.put(pAttribute.getType(), pAttribute);
	}

	public void addAttributes(List<Attribute> pAttributes) {
		for (Attribute attribute : pAttributes) {
			attributes.put(attribute.getType(), attribute);
		}
	}

	public boolean hasAttribute(AttributeType pType) {
		return attributes.containsKey(pType);
	}

	public boolean hasAttributes(List<AttributeType> pAttributeTypes) {
		return attributes.keySet().containsAll(pAttributeTypes);
	}

	public void removeAttribute(AttributeType pType) {
		attributes.remove(pType);
	}

}