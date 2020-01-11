package org.cajunc2.dev80.simulator.io.busdevice;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.util.BorderUtil;
import org.cajunc2.util.topic.TopicHandler;

public class CharLCDWindow extends JDialog implements TopicHandler<Void> {

	private static final long serialVersionUID = 2968229186867936031L;
	final int cols;
	private final int lines;
	private final DisplayChar[] displayChars;

	public CharLCDWindow(JFrame parent, int cols, int lines) {
		super(parent);
		this.cols = cols;
		this.lines = lines;
		displayChars = new DisplayChar[cols * lines];
		setTitle("Character LCD");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.setLayout(new GridBagLayout());
		JPanel lcdPanel = new JPanel();
		Border blackCasing = BorderFactory.createLineBorder(new Color(32, 32, 32), 12);
		Border lightGlow = BorderFactory.createLineBorder(new Color(46, 94, 222).brighter(), 1);
		Border blueBackground = BorderFactory.createLineBorder(new Color(46, 94, 222), 6);
		lcdPanel.setBorder(BorderUtil.createCompoundBorder(blackCasing, lightGlow, blueBackground));
		lcdPanel.setBackground(new Color(46, 94, 222));
		lcdPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(1, 1, 1, 1);
		for (int i = 0; i < displayChars.length; i++) {
			constraints.gridx = i % cols;
			constraints.gridy = i / cols;
			DisplayChar dc = new DisplayChar();
			displayChars[i] = dc;
			lcdPanel.add(dc, constraints);
		}
		this.add(lcdPanel, constraints);
		Commands.SHOW_DISPLAY.subscribe(this);
	}

	public void displayData(int[] data) {
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < lines; j++) {
				this.displayChars[(cols * j) + i].setByte(data[((80 / lines) * j) + i]);
			}
		}
		repaint();
	}

	private static class DisplayChar extends JComponent {

		private static final long serialVersionUID = -5532580990935015731L;
		private static final Image FONT_IMAGE = imageFromResource("charmap-blue.png");
		private int sx;
		private int sy;

		public DisplayChar() {
			super();
			setPreferredSize(new Dimension(10, 20));
			this.setByte(0x20);
		}

		public void setByte(int data) {
			int charTableCharacter = (data & 0xff) - 0x10;
			sx = (charTableCharacter / 16) * 15 + 2;
			sy = (charTableCharacter % 16) * 22 + 1;
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			g.drawImage(FONT_IMAGE, 0, 0, 10, 20, sx, sy, sx + 10, sy + 20, null);
		}

		private static Image imageFromResource(String string) {
			String resourceRef = "org/cajunc2/system/simulator/io/graphics/" + string;
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

	@Override
	public void topicReceived(Void payload) {
		if (this.isVisible()) {
			this.dispose();
		} else {
			this.setVisible(true);
		}
		// this.setVisible(!this.isVisible());
	}
}
