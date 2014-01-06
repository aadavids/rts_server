package units;

import java.util.LinkedHashMap;
import server_battle.Battle;
import server_battle.Position;

public class GameUnits {
	LinkedHashMap<Integer, Unit> unitList = new LinkedHashMap<Integer, Unit>();
	UnitLocation unitLocation = new UnitLocation();
	
	public void createUnit(Unit unit, Position position) {
		// should probably have mutex but #yolo
		Battle.Log.logln("UnitStatus","Creating unit: " + unit.getName() + " " +  Integer.toString(unit.uniqueId));
		unitList.put(unit.uniqueId, unit);
		unitLocation.addUnit(unit.uniqueId, position);
	}
	
	public void removeUnit (int uniqueId) {
		// should probably have mutex but #yolo
		Battle.Log.logln("UnitStatus","Removing unit: " + Integer.toString(uniqueId));
		unitList.remove(uniqueId);
		unitLocation.DeleteUnit(uniqueId);
	}
	
	public void moveUnit (int uniqueId, Position newPosition) {
		Battle.Log.logln("UnitStatus","Moving unit: " + Integer.toString(uniqueId));
		unitLocation.moveUnit(uniqueId, newPosition);
		//set unit location in unit class 
		//unitList.get(unit.uniqueId).set(newPosition);
	}
	
	public Unit getUnit (Position newPosition) { // gets unit closest to position
		Battle.Log.logln("UnitStatus","Looking up unit at position: " + newPosition.toString());
		return null;
	}
	
	public Unit getUnit (int uniqueId) {
		Battle.Log.logln("UnitStatus","Unit Lookup: " + Integer.toString(uniqueId) + " at Position: " + unitList.get(uniqueId).getPosition().toString());
		return unitList.get(uniqueId);
	}
}
