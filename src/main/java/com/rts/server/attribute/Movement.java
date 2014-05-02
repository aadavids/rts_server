package com.rts.server.attribute;

public class Movement extends Attribute {

	// Also include a list of acceptable modifications from buffs/debuffs

	public Movement(int pSpeed) {
		super(AttributeType.Movement, pSpeed);
	}

	public void setSpeed(int pSpeed) {
		setValue(pSpeed);
	}

	public int getSpeed() {
		return (int) getValue();
	}
}
