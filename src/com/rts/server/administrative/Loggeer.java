package com.rts.server.administrative;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

public class Loggeer {
	private List<outputChannel> channelList;
	private SimpleDateFormat dateformat = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss ");
	private static final Logger log = Logger.getLogger(Loggeer.class);

	// constructor
	public Loggeer() {
		channelList = new ArrayList<outputChannel>();
	}

	public boolean initialize(List<String> fileNames, List<String> comPorts) {
		PrintWriter outputFile = null;
		OutputStream serialchannel = null;

		if (fileNames.size() == comPorts.size()) {
			for (int i = 0; i < fileNames.size(); i++) {
				String filename = fileNames.get(i) + ".txt";
				try {
					outputFile = new PrintWriter(filename, "UTF-8");
				} catch (Exception e) {
					log.error(e.getMessage());
					this.close();
					return false;
				}

				try {
					CommPortIdentifier portIdentifier = CommPortIdentifier
							.getPortIdentifier(comPorts.get(i));
					if (portIdentifier.isCurrentlyOwned()) {
						outputFile.close();
						this.close();
						throw new Exception("Error: Port is currently in use");
					}

					CommPort commPort = portIdentifier.open(this.getClass()
							.getName(), 2000);
					SerialPort serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(57600,
							SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
					serialchannel = serialPort.getOutputStream();

				} catch (Exception e) {
					log.error(e.getMessage());
					outputFile.close();
					return false;
				}
				channelList.add(new outputChannel(fileNames.get(i), outputFile,
						serialchannel));
			}
			return true;
		} else
			return false;
	}

	public void logln(String type, String Message) {
		String date = dateformat.format(Calendar.getInstance().getTime());
		/*
		 * for(int x = 0; x < channelList.size(); x++)
		 * if(channelList.get(x).name == type){ try{
		 * channelList.get(x).filechannel.println(date + "> " + Message);
		 * channelList.get(x).serialchannel.write((date + "> " + Message +
		 * "\r\n").getBytes("UTF-8")); }catch(Exception e){
		 * log.error("Error at " + e.getStackTrace().toString() +
		 * " With Message: " + e.getMessage()); } break; }
		 */
		log.info(date + ">" + type + " :" + Message);
	}

	public void close() {

		for (int x = 0; x < channelList.size(); x++) {
			try {
				channelList.get(x).filechannel.close();
				channelList.get(x).serialchannel.close();
			} catch (Exception e) {
				log.error("Error at " + e.getStackTrace().toString()
						+ " With Message: " + e.getMessage());
			}
		}
	}

}
