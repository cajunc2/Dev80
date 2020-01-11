package org.cajunc2.dev80.ui.worker;

import javax.swing.SwingWorker;

import net.sleepymouse.microprocessor.Z80.Z80Core;

import org.cajunc2.dev80.simulator.ui.CpuStatePanel;
import org.cajunc2.dev80.ui.topic.Events;

public class SystemStepWorker extends SwingWorker<Void, Void> {

	private final Z80Core cpu;
	private final CpuStatePanel statePanel;

	public SystemStepWorker(Z80Core cpu, CpuStatePanel statePanel) {
		this.cpu = cpu;
		this.statePanel = statePanel;
	}

	@Override
	protected Void doInBackground() throws Exception {
		try {
			if (!cpu.getHalt()) {
				Events.SYSTEM_STARTED.publish();
				cpu.executeOneInstruction();
				Events.SYSTEM_PAUSED.publish();
				statePanel.updateDisplay();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
