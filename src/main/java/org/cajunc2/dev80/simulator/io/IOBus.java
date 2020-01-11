package org.cajunc2.dev80.simulator.io;

import net.sleepymouse.microprocessor.IODevice;

public class IOBus implements IODevice {

	private final BusDevice[] attachedDevices = new BusDevice[16];

	public void attachDevice(BusDevice device, int slot) {
		attachedDevices[slot] = device;
	}

	public void detachDevice(int slot) {
		attachedDevices[slot] = null;
	}

	@Override
	public int IORead(int address) {
		BusDevice device = attachedDevices[address];
		if (device == null) {
			return 0;
		}
		return device.read();
	}

	@Override
	public void IOWrite(int address, int data) {
		BusDevice device = attachedDevices[address];
		if (device == null) {
			return;
		}
		device.write(data);
	}

}
