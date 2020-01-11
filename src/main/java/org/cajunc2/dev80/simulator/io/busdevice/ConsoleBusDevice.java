package org.cajunc2.dev80.simulator.io.busdevice;

import java.io.IOException;

import org.cajunc2.dev80.simulator.io.BusDevice;


public class ConsoleBusDevice implements BusDevice {

	@Override
    public int read() {
	    try {
	        return System.in.read();
        } catch (IOException e) {
        	e.printStackTrace();
        	return 0;
        }
    }

	@Override
    public void write(int data) {
		System.out.print(data);
	}

}
