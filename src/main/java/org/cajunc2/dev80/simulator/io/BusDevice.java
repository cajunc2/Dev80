package org.cajunc2.dev80.simulator.io;


public interface BusDevice {
	int read();
	void write(int data);
}
