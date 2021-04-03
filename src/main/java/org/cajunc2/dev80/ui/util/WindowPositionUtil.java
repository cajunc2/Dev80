package org.cajunc2.dev80.ui.util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;

public class WindowPositionUtil {

	/**
	 * Returns an "ideal" starting position for the given {@link Window}:
	 * centered horizontally and somewhat above center vertically on the screen.
	 * 
	 * @return A {@link Point} representing the top-left corner of the window's
	 * "ideal" position
	 */
	public static Point idealPosition(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) * (381.0 / 1000));
		return new Point(x, y);
	}

}
