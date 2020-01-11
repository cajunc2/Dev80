package org.cajunc2.dev80.simulator.io.busdevice;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import org.cajunc2.dev80.simulator.io.BusDevice;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.util.topic.TopicHandler;

public class Keypad16 extends JDialog implements BusDevice, TopicHandler<Void> {

	private static final long serialVersionUID = -4264875803788269241L;
	private final JButton keys[] = new JButton[16];

	public Keypad16(JFrame parent) {
		super(parent);
		setTitle("Keypad");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(Color.BLACK);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(6, 6, 6, 6);

		c.gridx = 0;
		c.gridy = 0;
		keys[0] = new KeypadButton("1");
		add(keys[0], c);

		c.gridx = 1;
		c.gridy = 0;
		keys[1] = new KeypadButton("2");
		add(keys[1], c);

		c.gridx = 2;
		c.gridy = 0;
		keys[2] = new KeypadButton("3");
		add(keys[2], c);

		c.gridx = 3;
		c.gridy = 0;
		keys[3] = new KeypadButton("A");
		add(keys[3], c);

		c.gridx = 0;
		c.gridy = 1;
		keys[4] = new KeypadButton("4");
		add(keys[4], c);

		c.gridx = 1;
		c.gridy = 1;
		keys[5] = new KeypadButton("5");
		add(keys[5], c);

		c.gridx = 2;
		c.gridy = 1;
		keys[6] = new KeypadButton("6");
		add(keys[6], c);

		c.gridx = 3;
		c.gridy = 1;
		keys[7] = new KeypadButton("B");
		add(keys[7], c);

		c.gridx = 0;
		c.gridy = 2;
		keys[8] = new KeypadButton("7");
		add(keys[8], c);

		c.gridx = 1;
		c.gridy = 2;
		keys[9] = new KeypadButton("8");
		add(keys[9], c);

		c.gridx = 2;
		c.gridy = 2;
		keys[10] = new KeypadButton("9");
		add(keys[10], c);

		c.gridx = 3;
		c.gridy = 2;
		keys[11] = new KeypadButton("C");
		add(keys[11], c);

		c.gridx = 0;
		c.gridy = 3;
		keys[12] = new KeypadButton("*");
		add(keys[12], c);

		c.gridx = 1;
		c.gridy = 3;
		keys[13] = new KeypadButton("0");
		add(keys[13], c);

		c.gridx = 2;
		c.gridy = 3;
		keys[14] = new KeypadButton("#");
		add(keys[14], c);

		c.gridx = 3;
		c.gridy = 3;
		keys[15] = new KeypadButton("D");
		add(keys[15], c);

		pack();
		setResizable(false);
		//setVisible(true);

		Commands.SHOW_KEYPAD.subscribe(this);
	}

	@Override
	public int read() {
		for (int i = 0; i < keys.length; i++) {
			if (keys[i].getModel().isPressed()) {
				return i;
			}
		}
		return 0;
	}

	@Override
	public void write(int data) {
		// does nothing
	}

	private static class KeypadButton extends JButton {

		private static final long serialVersionUID = -5649980574016653619L;

		public KeypadButton(final String label) {
			super(label);
			setBorderPainted(false);
			setContentAreaFilled(false);
			setOpaque(true);
			setMargin(new Insets(1, 1, 1, 1));
			setFont(new Font("OCR A Extended", Font.PLAIN, 16));
			setPreferredSize(new Dimension(27, 21));
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					super.mousePressed(e);
					Commands.INTERRUPT.publish();
				}
			});
		}

		@Override
		public Color getBackground() {
			return this.getModel().isPressed() ? Color.LIGHT_GRAY : Color.WHITE;
		}

		@Override
		public Border getBorder() {
			return BorderFactory.createEmptyBorder();
		}

	}

	@Override
	public void topicReceived(Void payload) {
		if (this.isVisible()) {
			this.dispose();
		} else {
			this.setVisible(true);
		}
	}
}
