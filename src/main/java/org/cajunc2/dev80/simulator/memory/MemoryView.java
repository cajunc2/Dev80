package org.cajunc2.dev80.simulator.memory;

import java.awt.Component;

import com.codingrodent.microprocessor.IMemory;


public interface MemoryView extends IMemory {
	void writeROM(byte[] bytes);

	byte[] readROM();

	void resetRAM();

	Component getUIComponent();
}
