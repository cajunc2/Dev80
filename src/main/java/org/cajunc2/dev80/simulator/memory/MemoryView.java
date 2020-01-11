package org.cajunc2.dev80.simulator.memory;

import java.awt.Component;

import net.sleepymouse.microprocessor.Memory;


public interface MemoryView extends Memory {
	void writeROM(byte[] bytes);

	byte[] readROM();

	void resetRAM();

	Component getUIComponent();
}
