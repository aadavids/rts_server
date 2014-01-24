package com.rts.server.unit.stat;

/**
 * handles all actions required by a stat that can be regenerated (health, mana,
 * stamina, etc.)
 * 
 * @author benteichman
 * 
 */
public class RegenerableStat {
	private final int maxValue;
	private final int regenValue;
	private int current;
	private int regenMod;
	private int maxMod;

	// private int maxRegen;

	public RegenerableStat(int pMaxValue, int pRegenValue) {
		maxValue = pMaxValue;
		regenValue = pRegenValue;

		current = pMaxValue;
	}

	private int getRegen() {
		return Math.max(regenValue + regenMod, 0);
	}

	/**
	 * 
	 * @param pValue
	 *            new regenMod
	 */
	public void setRegenMod(int pValue) {
		regenMod = pValue;
	}

	/**
	 * replenish a value equal to the regenValue + regenMod. It will not
	 * increase beyond maxValue + maxMod
	 */
	public void regenerate() {
		replenish(getRegen());
	}

	/**
	 * 
	 * @param pValue
	 *            amount to replenish current value by. It will not increase
	 *            beyond maxValue + maxMod
	 */
	public void replenish(int pValue) {
		current = Math.min(current + pValue, maxValue + maxMod);
	}

	/**
	 * 
	 * @param pValue
	 *            amount to deplete current value by
	 * @return the new current value of this stat
	 */
	public int deplete(int pValue) {
		current = Math.max(current - pValue, 0);
		return current;
	}

	/**
	 * 
	 * @return the current value of this stat
	 */
	public int getCurrent() {
		return current;
	}

	/**
	 * sets the current value to the maxValue + maxMod
	 */
	public void maxOut() {
		current = maxValue + maxMod;
	}

	/**
	 * 
	 * @return true if the current value is below maxValue + maxMod
	 */
	public boolean isBelowMax() {
		return current < (maxValue + maxMod);
	}
}
