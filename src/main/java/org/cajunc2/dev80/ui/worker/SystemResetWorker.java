package org.cajunc2.dev80.ui.worker;

import javax.swing.SwingWorker;

import com.codingrodent.microprocessor.Z80.Z80Core;

import org.cajunc2.dev80.simulator.ui.CpuStatePanel;
import org.cajunc2.dev80.ui.topic.Events;

public class SystemResetWorker extends SwingWorker<Void, Void> {

	private final Z80Core cpu;
	private final CpuStatePanel statePanel;

	public SystemResetWorker(Z80Core cpu, CpuStatePanel statePanel) {
		this.cpu = cpu;
		this.statePanel = statePanel;
	}

	@Override
	protected Void doInBackground() throws Exception {
		cpu.reset();
		Events.SYSTEM_RESET.publish();
		statePanel.updateDisplay();
		return null;
	}

}
