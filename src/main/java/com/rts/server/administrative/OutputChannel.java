package com.rts.server.administrative;

import java.io.OutputStream;
import java.io.PrintWriter;

public class OutputChannel {
	String name;
	PrintWriter filechannel;
	OutputStream serialchannel;

	OutputChannel(String pName, PrintWriter pFilechannel,
			OutputStream pSerialchannel) {
		name = pName;
		filechannel = pFilechannel;
		serialchannel = pSerialchannel;
	}
}
