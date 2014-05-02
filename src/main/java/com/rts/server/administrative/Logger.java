package com.rts.server.administrative;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Logger {
	private List<OutputChannel> channelList;
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(Logger.class);

	// constructor
	public Logger() {
		channelList = new ArrayList<OutputChannel>();
	}

	public boolean initialize(List<String> pFileNames, List<String> pComPorts) {
		PrintWriter outputFile = null;
		OutputStream serialchannel = null;

		if (pFileNames.size() == pComPorts.size()) {
			for (int i = 0; i < pFileNames.size(); i++) {
				String filename = pFileNames.get(i) + ".txt";
				try {
					outputFile = new PrintWriter(filename, "UTF-8");
				} catch (Exception e) {
					log.error(e.getMessage());
					this.close();
					return false;
				}

				try {
					CommPortIdentifier portIdentifier = CommPortIdentifier
							.getPortIdentifier(pComPorts.get(i));
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
				channelList.add(new OutputChannel(pFileNames.get(i), outputFile,
						serialchannel));
			}
			return true;
		} else {
			return false;
		}
	}

	public void close() {

		for (OutputChannel output : channelList) {
			try {
				output.filechannel.close();
				output.serialchannel.close();
			} catch (Exception e) {
				log.error("error at " + e.getStackTrace().toString()
						+ " with message: " + e.getMessage());
			}
		}
	}

}
