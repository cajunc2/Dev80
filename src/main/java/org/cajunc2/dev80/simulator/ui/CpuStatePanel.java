package org.cajunc2.dev80.simulator.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.cajunc2.dev80.ui.Icons;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.util.topic.TopicHandler;

import net.sleepymouse.microprocessor.Z80.Z80Core;
import net.sleepymouse.microprocessor.Z80.Z80Core.RegisterNames;

public class CpuStatePanel extends JPanel {

	private static final long serialVersionUID = -4598870991951000778L;

	private final RegisterTextField registerA = new RegisterTextField();
	private final RegisterTextField registerB = new RegisterTextField();
	private final RegisterTextField registerC = new RegisterTextField();
	private final RegisterTextField registerD = new RegisterTextField();
	private final RegisterTextField registerE = new RegisterTextField();
	private final RegisterTextField registerH = new RegisterTextField();
	private final RegisterTextField registerL = new RegisterTextField();
	private final RegisterTextField registerIX = new Register16TextField();
	private final RegisterTextField registerIY = new Register16TextField();
	private final RegisterTextField registerI = new RegisterTextField();
	private final RegisterTextField registerR = new RegisterTextField();
	private final RegisterTextField registerSP = new Register16TextField();
	private final RegisterTextField registerPC = new Register16TextField();
	private final FlagsTextField registerF = new FlagsTextField();
	private final Z80Core cpu;

	CpuStatePanel(final Z80Core cpu) {
		this.cpu = cpu;
		setPreferredSize(new Dimension(192, 256));
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1;
		constraints.weighty = 0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(2, 2, 2, 2);

		constraints.gridy = 0;
		constraints.gridx = 0;
		add(new RLabel("A"), constraints);

		constraints.gridx = 1;
		add(registerA, constraints);

		constraints.gridx = 2;
		final JLabel statusLabel = new JLabel();
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statusLabel.setIcon(Icons.STATUS_GRAY);
		add(statusLabel, constraints);

		constraints.gridy = 1;
		constraints.gridx = 0;
		add(new RLabel("B"), constraints);

		constraints.gridx = 1;
		add(registerB, constraints);

		constraints.gridx = 2;
		add(registerC, constraints);

		constraints.gridx = 3;
		add(new JLabel("C"), constraints);

		constraints.gridy = 2;
		constraints.gridx = 0;
		add(new RLabel("D"), constraints);

		constraints.gridx = 1;
		add(registerD, constraints);

		constraints.gridx = 2;
		add(registerE, constraints);

		constraints.gridx = 3;
		add(new JLabel("E"), constraints);

		constraints.gridy = 3;
		constraints.gridx = 0;
		add(new RLabel("H"), constraints);

		constraints.gridx = 1;
		add(registerH, constraints);

		constraints.gridx = 2;
		add(registerL, constraints);

		constraints.gridx = 3;
		add(new JLabel("L"), constraints);

		constraints.gridy = 4;
		constraints.gridx = 0;
		add(new RLabel("IX"), constraints);

		constraints.gridx = 1;
		add(registerIX, constraints);

		constraints.gridx = 2;
		add(registerIY, constraints);

		constraints.gridx = 3;
		add(new JLabel("IY"), constraints);

		constraints.gridy = 5;
		constraints.gridx = 0;
		add(new RLabel("I"), constraints);

		constraints.gridx = 1;
		add(registerI, constraints);

		constraints.gridx = 2;
		add(registerR, constraints);

		constraints.gridx = 3;
		add(new JLabel("R"), constraints);

		constraints.gridy = 6;
		constraints.gridx = 0;
		add(new RLabel("SP"), constraints);

		constraints.gridx = 1;
		add(registerSP, constraints);

		constraints.gridx = 2;
		add(registerPC, constraints);

		constraints.gridx = 3;
		add(new JLabel("PC"), constraints);

		constraints.gridy = 7;
		constraints.gridx = 0;
		add(new RLabel("F"), constraints);

		constraints.gridx = 1;
		constraints.gridwidth = 2;
		add(registerF, constraints);

		constraints.gridy = 8;
		constraints.gridx = 1;
		constraints.gridwidth = 1;
		JButton nmiButton = new JButton("NMI");
		nmiButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				cpu.setNMI();
			}
		});
		add(nmiButton, constraints);

		constraints.gridy = 8;
		constraints.gridx = 2;
		constraints.gridwidth = 1;
		JButton intButton = new JButton("INT");
		intButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				cpu.setINT();
			}
		});
		add(intButton, constraints);

		Events.SYSTEM_PAUSED.subscribe(new TopicHandler<Void>() {

			@Override
			public void topicReceived(Void payload) {
				statusLabel.setIcon(Icons.STATUS_YELLOW);
				statusLabel.setToolTipText("CPU Execution Paused");
			}
		});

		Events.SYSTEM_HALTED.subscribe(new TopicHandler<Void>() {

			@Override
			public void topicReceived(Void payload) {
				statusLabel.setIcon(Icons.STATUS_RED);
				statusLabel.setToolTipText("CPU Halted");
			}
		});

		Events.SYSTEM_STARTED.subscribe(new TopicHandler<Void>() {

			@Override
			public void topicReceived(Void payload) {
				statusLabel.setIcon(Icons.STATUS_GREEN);
				statusLabel.setToolTipText("CPU Running");
			}
		});
		Events.SYSTEM_RESET.subscribe(new TopicHandler<Void>() {

			@Override
			public void topicReceived(Void payload) {
				statusLabel.setIcon(Icons.STATUS_GRAY);
				statusLabel.setToolTipText("CPU has been reset");
			}
		});
	}

	public void updateDisplay() {
		registerA.setData(cpu.getRegisterValue(RegisterNames.A));
		registerB.setData((cpu.getRegisterValue(RegisterNames.BC) >> 8) & 0xFF);
		registerC.setData(cpu.getRegisterValue(RegisterNames.BC) & 0xFF);
		registerD.setData((cpu.getRegisterValue(RegisterNames.DE) >> 8) & 0xFF);
		registerE.setData(cpu.getRegisterValue(RegisterNames.DE) & 0xFF);
		registerH.setData((cpu.getRegisterValue(RegisterNames.HL) >> 8) & 0xFF);
		registerL.setData(cpu.getRegisterValue(RegisterNames.HL) & 0xFF);
		registerIX.setData(cpu.getRegisterValue(RegisterNames.IX));
		registerIY.setData(cpu.getRegisterValue(RegisterNames.IY));
		registerI.setData(cpu.getRegisterValue(RegisterNames.I));
		registerR.setData(cpu.getRegisterValue(RegisterNames.R) & 0b01111111);
		registerSP.setData(cpu.getRegisterValue(RegisterNames.SP));
		registerPC.setData(cpu.getRegisterValue(RegisterNames.PC));
		registerF.setData(cpu.getRegisterValue(RegisterNames.F));
	}

	private static class RegisterTextField extends JLabel {

		private static final long serialVersionUID = 5647005872469460368L;

		RegisterTextField() {
			setFont(new Font("Consolas", Font.PLAIN, 14));
			setHorizontalAlignment(SwingConstants.CENTER);
//			setPreferredSize(new Dimension(48, 21));
			setText("00");
		}

		public void setData(int data) {
			if (data < 16) {
				setText("0" + Integer.toHexString(data));
				return;
			}
			setText(Integer.toHexString(data));
		}
	}

	private static class Register16TextField extends RegisterTextField {

		private static final long serialVersionUID = -4201692094742487296L;

		Register16TextField() {
			super();
			setText("0000");
		}

		@Override
		public void setData(int data) {
			if (data < 16) {
				setText("000" + Integer.toHexString(data));
				return;
			}
			if (data < 256) {
				setText("00" + Integer.toHexString(data));
				return;
			}
			if (data < 4096) {
				setText('0' + Integer.toHexString(data));
				return;
			}
			setText(Integer.toHexString(data));
		}
	}

	private static class FlagsTextField extends JLabel {

		private static final long serialVersionUID = 6659316602611603911L;

		FlagsTextField() {
			setFont(new Font("Consolas", Font.PLAIN, 12));
			setHorizontalAlignment(SwingConstants.CENTER);
			setData(0);
		}

		public void setData(int data) {
			StringBuilder sb = new StringBuilder(8);
			sb.append(((0b10000000 & data) == 0b10000000) ? 'S' : 's');
			sb.append(((0b01000000 & data) == 0b01000000) ? 'Z' : 'z');
			sb.append('_');
			sb.append(((0b00010000 & data) == 0b00010000) ? 'H' : 'h');
			sb.append('_');
			sb.append(((0b00000100 & data) == 0b00000100) ? 'P' : 'p');
			sb.append(((0b00000010 & data) == 0b00000010) ? 'N' : 'n');
			sb.append(((0b00000001 & data) == 0b00000001) ? 'C' : 'c');
			setText(sb.toString());
		}
	}

	private static class RLabel extends JLabel {

		private static final long serialVersionUID = -3426766360741285961L;

		RLabel(String label) {
			super(label);
			setHorizontalAlignment(SwingConstants.TRAILING);
		}
	}
}
