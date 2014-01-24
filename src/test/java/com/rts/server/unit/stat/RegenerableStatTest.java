package com.rts.server.unit.stat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class RegenerableStatTest {

	private static final int MAX_HEALTH = 200;
	private static final int REGEN_VALUE = 10;
	private RegenerableStat stat;

	@Before
	public void before() {
		stat = new RegenerableStat(MAX_HEALTH, REGEN_VALUE);
	}

	@Test
	public void testInitialize() {
		assertEquals(MAX_HEALTH, stat.getCurrent());
	}

	@Test
	public void testDeplete() throws Exception {
		stat.deplete(40);
		assertEquals(160, stat.getCurrent());
	}

	@Test
	public void testReplenish() throws Exception {
		stat.deplete(MAX_HEALTH);
		stat.replenish(140);
		assertEquals(140, stat.getCurrent());
	}

	@Test
	public void testRegenerate() throws Exception {
		stat.deplete(MAX_HEALTH);
		stat.regenerate();
		assertEquals(REGEN_VALUE, stat.getCurrent());
	}

	@Test
	public void testBelowMax() throws Exception {
		assertFalse(stat.isBelowMax());

		stat.deplete(10);
		assertTrue(stat.isBelowMax());
	}

	@Test
	public void testDepleteBelowZero() throws Exception {
		stat.deplete(MAX_HEALTH + 1);
		assertEquals(0, stat.getCurrent());
	}

	@Test
	public void testReplenishBeyondMax() throws Exception {
		stat.replenish(MAX_HEALTH + 1);
		assertEquals(MAX_HEALTH, stat.getCurrent());
	}

}
