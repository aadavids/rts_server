package com.rts.server.unit;

import java.util.ArrayList;
import java.util.List;

import com.rts.server.unit.stat.RegenerableStat;
import com.rts.server.unit.stat.Stat;

public class Marine extends Unit {
	private RegenerableStat health;
	private RegenerableStat mana;
	private Stat armour;
	private Stat movementSpeed;
	private List<String> modifiers = new ArrayList<String>();

	// private List<Effect> Afflictions = new ArrayList<Effect>();
	// private List<Ability> Abilities = new ArrayList<Ability>();
	// private List<Subscription> Subscriptions = new ArrayList<Subscription>();

	public Marine() {
		super(UnitType.Marine, 5);
		health = new RegenerableStat(45, 1);
		mana = new RegenerableStat(20, 1);
		armour = new Stat(1);
		movementSpeed = new Stat(2);
		modifiers.add("terran");
		modifiers.add("biological");
		modifiers.add("light");

	}

	public void affectBar(String bar, int incurredchange) {
		switch (bar) {
		case "healthdown":
			if (health.deplete(incurredchange) <= 0) {
				onDeath();
			}
		case "healthup":
			health.replenish(incurredchange);
		case "manadown":
			mana.deplete(incurredchange);
		case "manaup":
			mana.replenish(incurredchange);
		}
	}

	public void update() {
		if (health.isBelowMax()) {
			health.regenerate();
		}

		if (mana.isBelowMax()) {
			mana.regenerate();
		}
	}

	public boolean isUpdateRequired() {
		if (health.isBelowMax() || mana.isBelowMax()) {
			if (updateOrder == 0) {
				// UpdateOrder updateorder = new UpdateOrder(this,1);
				// Queue update updateOrder =
				// toDoQueue.FindOrderList("Update").add(updateorder);
			}
			return true;
		} else if (updateOrder != 0) {
			// Cancel update
			// toDoQueue.FindOrderList("Update").remove(updateOrder);
			updateOrder = 0;
			return false;
		} else
			return false;
	}

	public int getMoveSpeed() {
		return movementSpeed.getModified();
	}

	public int getBaseMoveSpeed() {
		return movementSpeed.getBase();
	}

	public void addMoveSpeed(int pModifier) {
		movementSpeed.addModifier(pModifier);
	}

	public void resetMoveSpeed() {
		movementSpeed.resetModifier();
	}

	public int getArmour() {
		return armour.getModified();
	}

	public int getBaseArmour() {
		return armour.getBase();
	}

	public void addAmour(int pModifier) {
		armour.addModifier(pModifier);
	}

	public void resetArmour() {
		armour.resetModifier();
	}

	public List<String> getModifiers() {
		return modifiers;
	}

	public void addModifiers(String pModifier) {
		modifiers.add(pModifier);
	}

	public void removeModifiers(String pModifier) {
		modifiers.remove(pModifier);
	}

	public void onSpawn() {
		health.maxOut();
		mana.maxOut();
	}

	public void onDeath() {

	}

	public void onLevelup() {

	}
}