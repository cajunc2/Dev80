package org.cajunc2.dev80.ui.util;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class BorderUtil {
	private BorderUtil() {
	}

	public static Border createCompoundBorder(Border... borders) {
		if (borders.length == 0) {
			return BorderFactory.createEmptyBorder();
		}
		if (borders.length == 1) {
			return borders[0];
		}
		if (borders.length == 2) {
			return BorderFactory.createCompoundBorder(borders[0], borders[1]);
		}
		Border result = borders[0];
		for (int i = 1; i < borders.length; i++) {
			result = BorderFactory.createCompoundBorder(result, borders[i]);
		}
		return result;
	}
}
