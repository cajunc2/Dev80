package org.cajunc2.dev80.simulator.io;

import java.awt.Component;

import net.sleepymouse.microprocessor.IODevice;

public interface IOView extends IODevice {
	void clear();
	Component getUIComponent();
}
