package org.cajunc2.dev80.simulator.io;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.cajunc2.dev80.ui.util.BorderUtil;

public class Lcd1602IOView extends JPanel implements IOView {

	private static final long serialVersionUID = 2968229186867936031L;
	private final DisplayChar[] displayChars = new DisplayChar[32];
	private int addressPointer = 0;
	private JButton[] buttons = new JButton[8];

	public Lcd1602IOView() {
		this.setLayout(new BorderLayout());
		addLcdPanel(BorderLayout.CENTER);
		addInputPanel(BorderLayout.SOUTH);
		clear();
	}

	private void addLcdPanel(Object layoutConstraints) {
		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());
		JPanel lcdPanel = new JPanel();
		Border blackCasing = BorderFactory.createLineBorder(new Color(64, 64, 64), 12);
		Border lightGlow = BorderFactory.createLineBorder(new Color(46, 94, 222).brighter(), 1);
		Border blueBackground = BorderFactory.createLineBorder(new Color(46, 94, 222), 6);
		lcdPanel.setBorder(BorderUtil.createCompoundBorder(blackCasing, lightGlow, blueBackground));
		lcdPanel.setBackground(new Color(46, 94, 222));
		lcdPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(1, 1, 1, 1);
		for (int i = 0; i < displayChars.length; i++) {
			constraints.gridx = i % 16;
			constraints.gridy = i / 16;
			DisplayChar dc = new DisplayChar();
			displayChars[i] = dc;
			lcdPanel.add(dc, constraints);
		}
		setBorder(BorderFactory.createEmptyBorder());
		container.add(lcdPanel);
		this.add(container, layoutConstraints);
	}

	private void addInputPanel(Object layoutConstraints) {
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(1, 8));

		for (int i = 0; i < 8; i++) {
			buttons[i] = new JButton(Integer.toString(i + 1));
			inputPanel.add(buttons[i]);
		}
		this.add(inputPanel, layoutConstraints);
	}

	@Override
	public int IORead(int address) {
		if(address != 0) {
			return 0;
		}
		int result = 0;
		for (int i = 0; i < 8; i++) {
			JButton button = buttons[i];
			if (button.getModel().isPressed()) {
				result |= (1 << i);
			}
		}
		return result;
	}

	@Override
	public void IOWrite(int address, int data) {
		if(address != 0) {
			return;
		}
		int charIndex = addressPointer % 32;
		displayChars[charIndex].setByte(data);
		addressPointer++;
	}

	@Override
	public void clear() {
		for (DisplayChar c : displayChars) {
			c.setByte(0x20);
		}
		this.addressPointer = 0;
	}

	@Override
	public Component getUIComponent() {
		return this;
	}

	private static class DisplayChar extends JComponent {

		private static final long serialVersionUID = -5532580990935015731L;
		private static final Image fontImage = imageFromResource("charmap-blue.png");
		private int currentCharacter;

		public DisplayChar() {
			super();
			setPreferredSize(new Dimension(10, 20));
		}

		public void setByte(int data) {
			this.currentCharacter = data;
			repaint();
		}

		@Override
		protected void paintComponent(Graphics iGraphics) {
			Graphics2D g = (Graphics2D) iGraphics;
			int charTableCharacter = (currentCharacter & 0xff) - 0x10;
			int sx = (charTableCharacter / 16) * 15 + 2;
			int sy = (charTableCharacter % 16) * 22 + 1;
			g.drawImage(fontImage, 0, 0, 10, 20, sx, sy, sx + 10, sy + 20, null);
		}

		private static Image imageFromResource(String string) {
			String resourceRef = "org/cajunc2/system/io/graphics/" + string;
			URL iconLocation = ClassLoader.getSystemClassLoader().getResource(resourceRef);
			if (iconLocation == null) {
				throw new RuntimeException("Unable to load icon: " + resourceRef);
			}
			try {
				return ImageIO.read(iconLocation);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}
}
