package units;

import java.net.UnknownHostException;
import java.util.ArrayList;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import server_battle.Position;

public class UnitLocation {

	public static final String indexName = "geospatialIdx";
	public static final String dbName = "units_geospatial";
	public static final String host = "127.0.0.1";
	public static final int port = 27017;
	public static final String collectionName = "places";
	Mongo mongo;
	DB db;
	DBCollection units;
	
	UnitLocation()	{
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient(host);
			db = mongoClient.getDB( dbName );
			units = db.getCollection("unit_position");
			//this.AddUnit(new AtomicInteger(), new Position(0, 0)); // puts a test unit at the origin
			BasicDBObject index = new BasicDBObject();
			index.put("loc","2d");
			units.ensureIndex(index); // indexes as geospatialIdx
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	// adds unit to map, should be functional
	void AddUnit(final int uniqueID, Position unitPosition) {
		BasicDBObject unit = new BasicDBObject("name", "MongoDB"); 	// create entry for unit
		unit.put("uniqueID", uniqueID);								// add unique id to entry
		BasicDBObject loc = new BasicDBObject();					// create location entry
		loc.put ("x", unitPosition.getx());							// add x coordinate
		loc.put("y", unitPosition.gety());							// add y coordinate
		unit.put("loc", loc);										// add location entry to unit entry
		units.insert(unit);											// add unit to units collection
	}
	
	// update unit location on map, TODO
	void MoveUnit(final int uniqueID, Position newPosition) {
		
		//probably put some check here to see if this is a valid place to move to.
		
		BasicDBObject query = new BasicDBObject("uniqueID", uniqueID);
		BasicDBObject update = (BasicDBObject) units.findOne(query);

		update.removeField("loc");
		BasicDBObject loc = new BasicDBObject();					// create location entry
		loc.put ("x", newPosition.getx());							// add x coordinate
		loc.put ("y", newPosition.gety());							// add y coordinate
		update.put("loc", loc);
		
		units.update(query, update);
	
	}
	
	// get unit by unique id, should be functional
	void GetUnit(final int uniqueID)	{
		BasicDBObject query = new BasicDBObject("uniqueID", uniqueID);
		DBCursor cursor = units.find(query);
		
		try {
			while(cursor.hasNext()) {
				DBObject result = cursor.next();
				System.out.println(result); // for testing
				
			}
		} finally {
			   cursor.close();
		}
	}
	
	
	ArrayList<Integer> FindUnitsNear(Position unitPosition)	{ // should be functional, TODO: by default returns nearest 50 units, will update 
		BasicDBObject query = new BasicDBObject("$near", new double[] {unitPosition.getx(), unitPosition.gety()});
		DBCursor cursor = units.find(query);
		ArrayList<Integer> unitList = new ArrayList<Integer>();
		try {
		while(cursor.hasNext()) {
			DBObject result = cursor.next();
			System.out.println(result);
			unitList.add((Integer) result.get("uniqueId"));
	   }
		} finally {
		   cursor.close();
		}
		return unitList;
	}
	
	// removes unit from map
	void Destroy() {
		// TODO
	}
	
}
