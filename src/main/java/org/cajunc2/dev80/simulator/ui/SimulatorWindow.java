package org.cajunc2.dev80.simulator.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import org.cajunc2.dev80.simulator.io.IOBus;
import org.cajunc2.dev80.simulator.io.busdevice.CharLCD;
import org.cajunc2.dev80.simulator.io.busdevice.Keypad16;
import org.cajunc2.dev80.simulator.memory.MemoryView;
import org.cajunc2.dev80.simulator.memory.ROMRAMView;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.worker.SystemResetWorker;
import org.cajunc2.dev80.ui.worker.SystemRunSlowWorker;
import org.cajunc2.dev80.ui.worker.SystemRunWorker;
import org.cajunc2.dev80.ui.worker.SystemStepWorker;
import org.cajunc2.util.topic.TopicHandler;

import net.sleepymouse.microprocessor.Z80.Z80Core;

public class SimulatorWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	final MemoryView memoryView = new ROMRAMView();
	private final IOBus ioBus = new IOBus();
	final Z80Core cpu = new Z80Core(memoryView, ioBus);
	CpuStatePanel cpuPanel = new CpuStatePanel(cpu);

	public SimulatorWindow(JFrame parent) {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		add(new CpuStatePanel(cpu), c);

		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		c.weightx = 1;
		c.gridy = 1;
		add(memoryView.getUIComponent(), c);

		pack();
		setLocationRelativeTo(parent);
		Commands.INTERRUPT.subscribe(new TopicHandler<Void>() {

			@Override
			public void topicReceived(Void payload) {
				cpu.setINT();
			}
		});

		CharLCD lcd = new CharLCD(this);
		ioBus.attachDevice(lcd.getControlDevice(), 0);
		ioBus.attachDevice(lcd.getDataDevice(), 1);
		ioBus.attachDevice(new Keypad16(this), 2);

		Commands.RUN_SYSTEM.subscribe(new CpuRunTopicHandler());
		Commands.RUN_SYSTEM_SLOW.subscribe(new CpuRunSlowTopicHandler());
		Commands.RUN_SYSTEM_STEP.subscribe(new CpuStepTopicHandler());
		Commands.RESET_SYSTEM.subscribe(new CpuResetTopicHandler());
	}

	private class CpuRunTopicHandler implements TopicHandler<Void> {

		CpuRunTopicHandler() {
		}

		@Override
		public void topicReceived(Void v) {
			SystemRunWorker worker = new SystemRunWorker(cpu, cpuPanel);
			try {
				worker.execute();
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private class CpuRunSlowTopicHandler implements TopicHandler<Void> {

		CpuRunSlowTopicHandler() {
		}

		@Override
		public void topicReceived(Void v) {
			SystemRunSlowWorker worker = new SystemRunSlowWorker(cpu, cpuPanel);
			try {
				worker.execute();
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private class CpuStepTopicHandler implements TopicHandler<Void> {

		CpuStepTopicHandler() {
		}

		@Override
		public void topicReceived(Void v) {
			SystemStepWorker worker = new SystemStepWorker(cpu, cpuPanel);
			try {
				worker.execute();
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private class CpuResetTopicHandler implements TopicHandler<Void> {

		CpuResetTopicHandler() {
		}

		@Override
		public void topicReceived(Void v) {
			memoryView.resetRAM();
			SystemResetWorker worker = new SystemResetWorker(cpu, cpuPanel);
			try {
				worker.execute();
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
