package server_battle;

import java.io.*;
import java.util.*;
import com.beust.jcommander.*;

import administrative.Logger;

public class Battle {

	private static final int EXECUTION_POOL_SIZE = 4;
	private static Logger Log = new Logger();
	private static final Logger log = Logger.getLogger(Battle.class);
	private static JCommander jc;
	private static Commands.createUnit blarg;

	public static void main(String args[]) throws Exception {
		Map gameMap = new Map();
		units.GameUnits gameDatabase = new units.GameUnits();
		Order_Time_Matrix Orders = new Order_Time_Matrix(EXECUTION_POOL_SIZE,
				gameMap, gameDatabase);
		Orders.RegisterOrder_Type(new UnitCreationOrder_List());
		Orders.RegisterOrder_Type(new MovementOrder_List());
		Orders.RegisterOrder_Type(new UpdateOrder_List());
		List<String> fileNames = Arrays.asList("MainOut", "UnitStatus",
				"OrderStatus", "Active");
		List<String> comPorts = Arrays.asList("COM1", "COM2", "COM3", "COM4");

		Properties p = new Properties(System.getProperties());
		p.setProperty("jcommander.debug", "true");
		System.setProperties(p);

		init();
		Log.initialize(fileNames, comPorts);

		log.info("Let The Game Begin: ");
		Log.logln("MainOut", "Let the Game Begin");
		String input;
		do {
			input = "";
			try {
				BufferedReader bufferRead = new BufferedReader(
						new InputStreamReader(System.in));
				input = bufferRead.readLine();
				Log.logln("MainOut", input);
				if (!parseStringtoOrder(Orders, input)) {
					Log.logln("MainOut", input + " DID NOT COMPILE");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} while (input != "exit");

		outputCurrentState(Orders, gameDatabase, gameMap);
		Log.close();
	}

	private static void init() {
		blarg = new Commands.createUnit();
		jc = new JCommander();
		jc.setProgramName("rawr");
		jc.addCommand("createUnit", blarg);
		jc.usage();
	}

	private static boolean parseStringtoOrder(Order_Time_Matrix Orders,
			String input) {
		jc.parse(input.split(" "));
		if (jc.getParsedCommand() == "createUnit") {
			Log.logln("MainOut", "HOLYSHITIMABAT");
			// Orders.AddOrder(newOrder, "UnitCreation");
			int health = blarg.health;
			Log.logln("MainOut", Integer.toString(health));
		} else if (jc.getParsedCommand() == "moveUnit") {
			// Orders.AddOrder(newOrder, "Movement");
		} else {
			return false;
		}
		return true;
		// Orders.AddOrder(newOrder, order_Listname)
	}

	private static void outputCurrentState(Order_Time_Matrix Orders,
			units.GameUnits gameDatabase, Map gameMap) {

	}
}
