package com.rts.server.world;

public class World {
	private OctNode root;
	private ProcGen mapgen;

	public World() {
		root = new OctNode();
		mapgen = new AndrewGen();

		// Generate World

	}
	/*
	 * public Block get() {
	 * 
	 * }
	 * 
	 * public Block getModify() {
	 * 
	 * }
	 * 
	 * 
	 * 
	 * Get create content specific object return object
	 * 
	 * Get & Modify create content specific object with old data apply
	 * modification return old object
	 */

}
