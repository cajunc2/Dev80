package org.cajunc2.dev80.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import org.cajunc2.dev80.simulator.ui.SimulatorWindow;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.util.topic.Topic;
import org.cajunc2.util.topic.TopicHandler;

public class MainToolbar extends JToolBar {

	private static final long serialVersionUID = -1996324640852968655L;

	public MainToolbar() {
		this.setFloatable(false);
		this.setRollover(true);

		this.add(new ToolbarButton(Icons.NEW_PROJECT, "New Project", Commands.NEW_PROJECT));
		this.add(new ToolbarButton(Icons.OPEN_PROJECT, "Open Project", Commands.OPEN_PROJECT));
		this.add(new ToolbarButton(Icons.EDIT_PROJECT, "Project Settings", Commands.OPEN_PROJECT));
		this.add(new ToolbarButton(Icons.REFRESH, "Refresh View", Commands.REFRESH_PROJECT));

		this.addSeparator();

		this.add(new ToolbarButton(Icons.NEW_FILE, "New File", Commands.NEW_FILE));
		this.add(new ToolbarButton(Icons.OPEN_FILE, "Open File", Commands.OPEN_FILE));
		this.add(new ToolbarButton(Icons.SAVE_FILE, "Save File", Commands.SAVE_FILE));
		this.add(new ToolbarButton(Icons.SAVE_AS_FILE, "Save File As...", Commands.SAVE_AS_FILE));
		this.add(new ToolbarButton(Icons.SAVE_ALL_FILES, "Save All Files", Commands.SAVE_ALL_FILES));

		this.addSeparator();

		final ToolbarButton buildButton = new ToolbarButton(Icons.BUILD_PROJECT, "Build Project",
		        Commands.BUILD_PROJECT);
		this.add(buildButton);
		this.add(new ToolbarButton(Icons.BURN_ROM, "Burn EEPROM chip", Commands.BURN_ROM));
		this.add(new ROMBurnProgressBar());

		final ToolbarButton runFastButton = new ToolbarButton(Icons.RUN_FAST, "Run", Commands.RUN_SYSTEM);
		final ToolbarButton runSlowButton = new ToolbarButton(Icons.RUN_SLOW, "Run (Slow)", Commands.RUN_SYSTEM_SLOW);
		final ToolbarButton pauseButton = new ToolbarButton(Icons.RUN_PAUSE, "Pause", Commands.SYSTEM_STOP);
		final ToolbarButton runStepButton = new ToolbarButton(Icons.RUN_STEP, "Execute One Instruction",
		        Commands.RUN_SYSTEM_STEP);
		final ToolbarButton resetButton = new ToolbarButton(Icons.RESET, "Reset CPU", Commands.RESET_SYSTEM);

		runFastButton.setEnabled(true);
		runSlowButton.setEnabled(true);
		runStepButton.setEnabled(true);
		resetButton.setEnabled(true);
		pauseButton.setEnabled(false);

		this.addSeparator();
		JButton simButton = new JButton(Icons.CPUMON);
		simButton.setToolTipText("Show Simulator");
		simButton.setFocusable(false);
		simButton.setBorderPainted(false);
		simButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SimulatorWindow(null).setVisible(true);
			}
		});
		this.add(simButton);

		Events.SYSTEM_STARTED.subscribe(new TopicHandler<Void>() {
			@Override
			public void topicReceived(Void payload) {
				runFastButton.setEnabled(false);
				runSlowButton.setEnabled(false);
				runStepButton.setEnabled(false);
				resetButton.setEnabled(false);
				pauseButton.setEnabled(true);
				buildButton.setEnabled(false);
			}
		});

		Events.SYSTEM_HALTED.subscribe(new TopicHandler<Void>() {
			@Override
			public void topicReceived(Void payload) {
				runFastButton.setEnabled(false);
				runSlowButton.setEnabled(false);
				runStepButton.setEnabled(false);
				resetButton.setEnabled(true);
				pauseButton.setEnabled(false);
				buildButton.setEnabled(true);
			}
		});

		Events.SYSTEM_RESET.subscribe(new TopicHandler<Void>() {
			@Override
			public void topicReceived(Void payload) {
				runFastButton.setEnabled(true);
				runSlowButton.setEnabled(true);
				runStepButton.setEnabled(true);
				resetButton.setEnabled(true);
				pauseButton.setEnabled(false);
				buildButton.setEnabled(true);
			}
		});

		Events.SYSTEM_PAUSED.subscribe(new TopicHandler<Void>() {
			@Override
			public void topicReceived(Void payload) {
				runFastButton.setEnabled(true);
				runSlowButton.setEnabled(true);
				runStepButton.setEnabled(true);
				resetButton.setEnabled(true);
				pauseButton.setEnabled(false);
				buildButton.setEnabled(false);
			}
		});
	}

	private static class ToolbarButton extends JButton {

		private static final long serialVersionUID = -4583480627801511192L;

		public ToolbarButton(Icon icon, String tooltip, Topic<Void> topic) {
			super(icon);
			setPreferredSize(new Dimension(28, 28));
			setToolTipText(tooltip);
			setFocusable(false);
			addActionListener(new ButtonActionListener(topic));
			setBorderPainted(false);
		}
	}

	private static class ButtonActionListener implements ActionListener {

		private final Topic<Void> command;

		public ButtonActionListener(Topic<Void> command) {
			this.command = command;
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			this.command.publish();
		}
	}

}
