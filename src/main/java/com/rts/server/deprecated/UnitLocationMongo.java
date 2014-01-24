package com.rts.server.deprecated;

import java.awt.Point;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

public class UnitLocationMongo {

	public static final String indexName = "geospatialIdx";
	public static final String dbName = "units_geospatial";
	public static final String host = "127.0.0.1";
	public static final int port = 27017;
	public static final String collectionName = "places";
	private static final Logger log = Logger.getLogger(UnitLocationMongo.class);

	Mongo mongo;
	DB db;
	DBCollection units;

	UnitLocationMongo() {
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient(host);
			db = mongoClient.getDB(dbName);
			units = db.getCollection("unit_position");
			// this.AddUnit(new AtomicInteger(), new Position(0, 0)); // puts a
			// test unit at the origin
			BasicDBObject index = new BasicDBObject();
			index.put("loc", "2d");
			units.ensureIndex(index); // indexes as geospatialIdx

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// adds unit to map, should be functional

	void addUnit(final int uniqueID, Point unitPosition) {
		BasicDBObject unit = new BasicDBObject("name", "MongoDB"); // create
																	// entry for
																	// unit
		unit.put("uniqueID", uniqueID); // add unique id to entry
		BasicDBObject loc = new BasicDBObject(); // create location entry
		loc.put("x", unitPosition.getX()); // add x coordinate
		loc.put("y", unitPosition.getY()); // add y coordinate
		unit.put("loc", loc); // add location entry to unit entry
		units.insert(unit); // add unit to units collection
	}

	// update unit location on map, TODO

	void moveUnit(final int uniqueID, Point newPosition) {

		// TODO check if valid place to move

		BasicDBObject query = new BasicDBObject("uniqueID", uniqueID);
		BasicDBObject update = (BasicDBObject) units.findOne(query);

		update.removeField("loc");
		BasicDBObject loc = new BasicDBObject(); // create location entry
		loc.put("x", newPosition.getX()); // add x coordinate
		loc.put("y", newPosition.getY()); // add y coordinate
		update.put("loc", loc);

		units.update(query, update);

	}

	// get unit by unique id, should be functional
	void getUnit(final int uniqueID) {
		BasicDBObject query = new BasicDBObject("uniqueID", uniqueID);
		DBCursor cursor = units.find(query);

		try {
			while (cursor.hasNext()) {
				DBObject result = cursor.next();
				log.debug(result); // for testing

			}
		} finally {
			cursor.close();
		}
	}

	ArrayList<Integer> FindUnitsNear(Point unitPosition) { // should be
															// functional, TODO:
															// by default
															// returns nearest
															// 50 units, will
															// update
		BasicDBObject query = new BasicDBObject("$near", new double[] {
				unitPosition.getX(), unitPosition.getY() });
		DBCursor cursor = units.find(query);
		ArrayList<Integer> unitList = new ArrayList<Integer>();
		try {
			while (cursor.hasNext()) {
				DBObject result = cursor.next();
				log.debug(result);
				unitList.add((Integer) result.get("uniqueId"));
			}
		} finally {
			cursor.close();
		}
		return unitList;
	}

	// removes unit from map
	void DeleteUnit(int uniqueId) {
		// TODO
	}

}
