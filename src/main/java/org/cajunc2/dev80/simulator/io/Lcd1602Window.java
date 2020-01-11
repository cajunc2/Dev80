package org.cajunc2.dev80.simulator.io;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.cajunc2.dev80.ui.util.BorderUtil;

public class Lcd1602Window extends JDialog {

	private static final long serialVersionUID = 2968229186867936031L;
	private final DisplayChar[] displayChars = new DisplayChar[32];

	public Lcd1602Window() {
		setTitle("LCD 1602");
		setResizable(false);
		this.setLayout(new GridBagLayout());
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
		this.add(lcdPanel, constraints);
	}

	public void displayData(int[] data) {
		for (int i = 0; i < 16; i++) {
			this.displayChars[i].setByte(data[i]);
			this.displayChars[16 + i].setByte(data[40 + i]);
		}
		repaint();
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
