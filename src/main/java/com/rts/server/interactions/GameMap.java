package com.rts.server.interactions;

public class GameMap {
	// terrain database
	private Terrain terrainDb;

	public GameMap() {
		terrainDb = new Terrain();
	}

	public Terrain getTerrain() {
		return terrainDb;
	}
}
