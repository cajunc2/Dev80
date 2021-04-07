package org.cajunc2.dev80.simulator.io;

import java.awt.Component;

import com.codingrodent.microprocessor.IBaseDevice;

public interface IOView extends IBaseDevice {
	void clear();
	Component getUIComponent();
}
