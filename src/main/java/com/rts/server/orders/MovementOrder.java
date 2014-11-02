package com.rts.server.orders;

import java.awt.Point;
import java.util.ArrayList;

import com.rts.server.attribute.AttributeType;
import com.rts.server.unit.Unit;

/**
 * 
 * Order that relates to unit movement
 * 
 */
public class MovementOrder extends Order {
	private int Uid;
	private Unit unit;
	private ArrayList<Point> remainingMovement;
	private Point moveGoal;
	private Point currentMovement;
	private int moveSpeed;
	private double currentTickTime;

	public MovementOrder(int pUid, Point pMoveGoal) {
		remainingMovement = new ArrayList<Point>();
		Uid = pUid;
		moveGoal = pMoveGoal;
	}

	@Override
	public void activation() {
		unit = gameDatabase.getUnit(Uid);

		if (unit.hasAttribute(AttributeType.Movement)) {
			moveSpeed = ((com.rts.server.attribute.Movement) unit
					.getAttribute(AttributeType.Movement)).getSpeed();
		} else {
			moveSpeed = 0;
		}
		remainingMovement.add(moveGoal); // replace this when pathme is written
		// pathme
		// remainingMovement = pathme(moveGoal);
		currentMovement = remainingMovement.get(0);
		remainingMovement.remove(0);

		currentTickTime = (currentMovement.distance(unit.getPosition()))
				/ moveSpeed;

		currentTickTime *= 1000;
		orderQueue.add(this, (int) currentTickTime);
	}

	@Override
	public void run() {
		boolean lastMove = gameDatabase.moveUnit(unit.uid, currentMovement);
		if (lastMove && !remainingMovement.isEmpty()) {
			currentMovement = remainingMovement.get(0);
			remainingMovement.remove(0);
			if (unit.getPosition().x != currentMovement.x
					&& unit.getPosition().y != currentMovement.y) {
				currentTickTime = Math.sqrt(2) * moveSpeed;
			} else {
				currentTickTime = moveSpeed;
			}

			currentTickTime *= 1000;
			orderQueue.add(this, (int) currentTickTime);
		} else if (!lastMove) {
			// check if old path is now valid again, otherwise
			// repathme
			// remainingMovement = pathme(moveGoal);
			// run();
		} else {
			// Movement succesful stuff

		}
	}
}
