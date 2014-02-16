package com.rts.server.interactions;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.rts.server.orders.Order.OrderType;
import com.rts.server.orders.UnitCreationOrder;
import com.rts.server.unit.Marine;
import com.rts.server.unit.Unit;
import com.rts.server.unit.management.GameUnits;

/**
 * 
 * Used for command line level command entry
 * 
 */
public class Battle {

	private static final int EXECUTION_POOL_SIZE = 4;
	private static final Logger log = Logger.getLogger(Battle.class);

	private static enum Command {
		create, move, check
	}

	public static void main(String args[]) throws Exception {
		// List<String> fileNames =
		// Arrays.asList("MainOut","UnitStatus","OrderStatus","Engine");
		// List<String> comPorts = Arrays.asList("COM1","COM2","COM3","COM4");
		// Log.initialize(fileNames, comPorts);

		GameMap gameMap = new GameMap();
		GameUnits gameDatabase = new GameUnits();
		OrderTimeMatrix orders = new OrderTimeMatrix(EXECUTION_POOL_SIZE,
				gameMap, gameDatabase);
		orders.registerOrderType(OrderType.UnitCreation);
		orders.registerOrderType(OrderType.Movement);
		orders.registerOrderType(OrderType.Update);

		init();

		log.info("Let the game begin!");
		BufferedReader buffReader = null;
		String input;
		do {
			input = "";
			try {
				if (buffReader == null) {
					buffReader = new BufferedReader(new InputStreamReader(
							System.in));
				}
				input = buffReader.readLine();
				log.info("Input captured: " + input);
				parseOrder(orders, input);
			} catch (IOException e) {
				e.printStackTrace();
			}
			outputCurrentState(orders, gameDatabase, gameMap);
		} while (input != "exit");

		outputCurrentState(orders, gameDatabase, gameMap);
		// Log.close();
	}

	private static void init() {
	}

	static boolean parseOrder(OrderTimeMatrix pOrders, String pInput) {
		// TODO replace with StringUtils
		if (pInput == null || pInput.trim().equals("")) {
			return false;
		}
		String[] input = asCleanCommands(pInput);

		Command command;
		try {
			command = Command.valueOf(input[0]);
		} catch (IllegalArgumentException e) {
			log.warn("Unrecognized command ignored: " + input[0]);
			return false;
		}

		switch (command) {
		case create:
			if (input.length != 4) {
				paramError(Command.create);
			} else {
				createUnit(pOrders, input[1], input[2], input[3]);
			}
			break;
		case move:
			// Orders.AddOrder(newOrder, "Movement");
			break;
		case check:
			if (input.length != 2) {
				paramError(Command.check);
			} else {
				return pOrders.getGameDatabase().getUnit(
						Integer.parseInt(input[1])) != null;
			}
			break;
		}
		return true;
		// Orders.AddOrder(newOrder, order_Listname);
	}

	private static void createUnit(OrderTimeMatrix pOrders, String pUnit,
			String pX, String pY) {
		log.info(String.format("creating %s at position (%s, %s)", pUnit, pX,
				pY));
		Unit createdUnit;

		if (pUnit.equals("marine") || pUnit.equals("m")) {
			createdUnit = new Marine();
		} else {
			log.warn("did not recognize unit name: " + pUnit);
			createdUnit = null;
		}

		Point createPosition = null;
		try {
			createPosition = new Point(Integer.parseInt(pX),
					Integer.parseInt(pY));
		} catch (NumberFormatException e) {
			log.error(String.format("could not parse to position: (%s, %s)",
					pX, pY));
		}

		if (createdUnit != null && createPosition != null) {
			pOrders.addOrder(
					new UnitCreationOrder(createdUnit, createPosition),
					OrderType.UnitCreation);
		}

		log.info("unit created: " + createdUnit);
	}

	private static void paramError(Command pCommand) {
		log.error(pCommand.toString()
				+ " called with invalid number of parameters");
	}

	private static void outputCurrentState(OrderTimeMatrix Orders,
			GameUnits gameDatabase, GameMap gameMap) {
		log.info("number of orders on the time queue: " + Orders.queuePeek());
	}

	private static String[] asCleanCommands(String pRawInput) {
		String[] rawCommands = pRawInput.split(" ");
		for (int i = 0; i < rawCommands.length; i++) {
			rawCommands[i] = rawCommands[i].trim().toLowerCase();
		}
		return rawCommands;
	}
}
