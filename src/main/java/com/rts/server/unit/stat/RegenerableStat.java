package com.rts.server.unit.stat;

public class RegenerableStat {
	private final int maxValue;
	private final int regenValue;
	private int current;
	private int regenMod;
	private int maxMod;
//	private int maxRegen;

	public RegenerableStat(int pMaxValue, int pRegenValue) {
		maxValue = pMaxValue;
		regenValue = pRegenValue;
	}

	private int getRegen() {
		return Math.max(regenValue + regenMod, 0);
	}

	public void regenerate() {
		replenish(getRegen());
	}

	public void replenish(int pValue) {
		current = Math.min(current + pValue, maxValue + maxMod);
	}

	public int deplete(int pValue) {
		current = Math.max(current - pValue, 0);
		return current;
	}

	public int getCurrent() {
		return current;
	}

	public void maxOut() {
		current = maxValue;
	}

	public boolean isBelowMax() {
		return current < (maxValue + maxMod);
	}
}
