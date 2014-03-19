package server_battle;

import java.awt.Point;
import java.util.ArrayList;

import units.Unit;

public class MovementOrder extends Order {	
	private Unit unit;
	private ArrayList<Point> remainingMovement;
	private Point moveGoal;
	private Point currentMovement;
	private double currentTickTime;

	public MovementOrder(int Unit, Point moveGoal/*,Player player*/){
		this.unit = gameDatabase.getUnit(Unit);
		this.moveGoal = moveGoal;
	}
	
	public void activation(){
		//pathme
		//remainingMovement = pathme(moveGoal);
		currentMovement = remainingMovement.get(0);
		remainingMovement.remove(0);
		if(unit.getPosition().x != currentMovement.x && unit.getPosition().y != currentMovement.y)
			currentTickTime = Math.sqrt(2) * unit.getmovespeed();
		else
			currentTickTime = unit.getmovespeed();
		
		currentTickTime *= 1000;
		order_Queue.add(this, (int)currentTickTime);
	}
	
	@Override
	public void run() {
		boolean lastMove = gameDatabase.moveUnit(unit.uniqueId, currentMovement);
		if(lastMove){
			currentMovement = remainingMovement.get(0);
			remainingMovement.remove(0);
			if(unit.getPosition().x != currentMovement.x && unit.getPosition().y != currentMovement.y)
				currentTickTime = Math.sqrt(2) * unit.getmovespeed();
			else
				currentTickTime = unit.getmovespeed();
			
			currentTickTime *= 1000;
			order_Queue.add(this, (int)currentTickTime);
		}
		else{
			//repathme
			//remainingMovement = pathme(moveGoal);
			//run();
		}
	}
}
