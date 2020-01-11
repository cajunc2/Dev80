package org.cajunc2.dev80.ui.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {

	private static final long serialVersionUID = -6419651483806072647L;
	private final Color color;
	private final int gap;

	public RoundedBorder(Color c, int g) {
		color = c;
		gap = g;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(color);
		Shape borderShape = new RoundRectangle2D.Double(x, y, width - 1, height - 1, gap, gap);
		g2d.draw(borderShape);
		g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 12));
		g2d.fill(borderShape);
		g2d.dispose();
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return (getBorderInsets(c, new Insets(gap, gap, gap, gap)));
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.left = insets.top = insets.right = insets.bottom = gap / 2;
		return insets;
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}
}