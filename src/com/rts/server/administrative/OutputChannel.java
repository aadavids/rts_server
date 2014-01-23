package com.rts.server.administrative;

import java.io.OutputStream;
import java.io.PrintWriter;

public class OutputChannel {
		String name;
		PrintWriter filechannel;
		OutputStream serialchannel;
		
		OutputChannel(String name, PrintWriter filechannel, OutputStream serialchannel){
			this.name = name;
			this.filechannel = filechannel;
			this.serialchannel = serialchannel;
		}
}
