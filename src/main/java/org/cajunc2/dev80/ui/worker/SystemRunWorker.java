package org.cajunc2.dev80.ui.worker;

import javax.swing.SwingWorker;

import com.codingrodent.microprocessor.Z80.Z80Core;

import org.cajunc2.dev80.simulator.ui.CpuStatePanel;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.util.topic.TopicHandler;

public class SystemRunWorker extends SwingWorker<Void, Void> implements TopicHandler<Void> {

	private final Z80Core cpu;
	private final CpuStatePanel statePanel;
	private boolean shouldKeepRunning = true;

	public SystemRunWorker(Z80Core cpu, CpuStatePanel statePanel) {
		this.cpu = cpu;
		this.statePanel = statePanel;
	}

	@Override
	protected Void doInBackground() throws Exception {
		if (cpu.getHalt()) {
			return null;
		}
		Commands.SYSTEM_STOP.subscribe(this);
		try {
			statePanel.updateDisplay();
			Events.SYSTEM_STARTED.publish();
			while (!cpu.getHalt()) {
				if (!shouldKeepRunning) {
					Events.SYSTEM_PAUSED.publish();
					statePanel.updateDisplay();
					return null;
				}
				cpu.executeOneInstruction();
			}
			Events.SYSTEM_HALTED.publish();
			statePanel.updateDisplay();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Commands.SYSTEM_STOP.unsubscribe(this);
		}
	}

	@Override
	public void topicReceived(Void payload) {
		this.shouldKeepRunning = false;
	}

}
