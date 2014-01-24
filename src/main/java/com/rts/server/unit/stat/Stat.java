package com.rts.server.unit.stat;

public class Stat {
	final int value;
	int modifier;

	public Stat(int pValue) {
		value = pValue;
	}

	public void addModifier(int pModifier) {
		modifier += pModifier;
	}

	public void resetModifier() {
		modifier = 0;
	}

	public int getModified() {
		return value + modifier;
	}

	public int getBase() {
		return value;
	}
}
