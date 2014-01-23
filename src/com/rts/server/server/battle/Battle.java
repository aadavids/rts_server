package com.rts.server.server.battle;

import java.awt.Point;
import java.io.*;

import com.rts.server.administrative.Loggeer;
import com.rts.server.units.*;


public class Battle {

	private static final int EXECUTION_POOL_SIZE = 4;
	public static Loggeer Log = new Loggeer();
	
	public static void main(String args[]) throws Exception {
		//List<String> fileNames = Arrays.asList("MainOut","UnitStatus","OrderStatus","Engine");
		//List<String> comPorts = Arrays.asList("COM1","COM2","COM3","COM4");
		//Log.initialize(fileNames, comPorts);
		
		Map gameMap = new Map();
		com.rts.server.unit.management.GameUnits gameDatabase = new com.rts.server.unit.management.GameUnits();
		OrderTimeMatrix Orders = new OrderTimeMatrix(EXECUTION_POOL_SIZE, gameMap, gameDatabase);
		Orders.RegisterOrder_Type(new UnitCreationOrderList());
		Orders.RegisterOrder_Type(new MovementOrderList());
		Orders.RegisterOrder_Type(new UpdateOrderList());

		init();

		
		Log.logln("MainOut", "Let the Game Begin");
		String input;
		do{
			input = "";
			try{
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				input = bufferRead.readLine();
				Log.logln("MainOut",input);
				if(!parseStringtoOrder(Orders, input)){
					Log.logln("MainOut", input + " DID NOT COMPILE");
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			outputCurrentState(Orders, gameDatabase, gameMap);
		}while(input != "exit");
		
		outputCurrentState(Orders, gameDatabase, gameMap);
		//Log.close();
    }
	
	private static void init(){
	}
	

	private static boolean parseStringtoOrder(OrderTimeMatrix Orders, String input){
		String[] inParse = input.trim().split(" ");
		if(inParse[0].equals("createUnit")){
			Log.logln("MainOut","HOLYSHITIMABAT");
			Unit createdUnit;
			
			if(inParse[1].equals("marine") || inParse[1].equals("Marine") || inParse[1].equals("m"))
				createdUnit = new Marine();
			else
				createdUnit = null;
			
			Point createPosition = new Point(Integer.parseInt(inParse[2]),Integer.parseInt(inParse[3]));
			
			if(createdUnit != null && createPosition != null)
				Orders.AddOrder(new UnitCreationOrder(createdUnit, createPosition), "UnitCreation");
			
			Log.logln("MainOut",createdUnit.getName());
		}else if(inParse[0].equals("moveUnit")){
			//Orders.AddOrder(newOrder, "Movement");
		}else if(inParse[0].equals("checkUnit")){
			Orders.getGameDatabase().getUnit(Integer.parseInt(inParse[1]));
		}else{
			return false;
		}
		return true;
		//Orders.AddOrder(newOrder, order_Listname);
	}
	
	private static void outputCurrentState(OrderTimeMatrix Orders, com.rts.server.unit.management.GameUnits gameDatabase, Map gameMap){
		Log.logln("MainOut", "Number of Orders on the Time Queue: " + Orders.queuePeek());
	}
}
