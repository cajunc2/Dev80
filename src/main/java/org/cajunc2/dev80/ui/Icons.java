package org.cajunc2.dev80.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * A utility class containing common icons for various controls throughout the
 * application. Also contains some utility methods for creating icons from
 * images or solid colors.
 */
public final class Icons {

	public static final ImageIcon APP_ICON = iconFile("AppIcon.png");

	public static final ImageIcon NEW_PROJECT = iconFile("application--plus.png");
	public static final ImageIcon OPEN_PROJECT = iconFile("folder-open.png");
	public static final ImageIcon EDIT_PROJECT = iconFile("application--pencil.png");
	public static final ImageIcon PROJECT_PROPERTIES = iconFile("ui-check-boxes-list.png");
	public static final ImageIcon REFRESH = iconFile("arrow-circle-double.png");

	public static final ImageIcon NEW_FILE = iconFile("document--plus.png");
	public static final ImageIcon OPEN_FILE = iconFile("folder-open-document.png");
	public static final ImageIcon SAVE_FILE = iconFile("disk.png");
	public static final ImageIcon SAVE_AS_FILE = iconFile("disk--plus.png");
	public static final ImageIcon SAVE_ALL_FILES = iconFile("disks.png");
	public static final ImageIcon BOOKMARK = iconFile("bookmark.png");

	public static final ImageIcon RUN_PROJECT = iconFile("control.png");
	public static final ImageIcon STOP = iconFile("cross-octagon.png");
	public static final ImageIcon RESET = iconFile("arrow-circle-315-left.png");
	public static final ImageIcon BUILD_FILE = iconFile("document-binary.png");
	public static final ImageIcon BUILD_PROJECT = iconFile("compile.png");
	public static final ImageIcon EXPORT_ROM = iconFile("document-binary.png");
	public static final ImageIcon BURN_ROM = iconFile("burn.png");

	public static final ImageIcon DOCUMENT = iconFile("document-attribute-z.png");
	public static final ImageIcon DOCUMENT_EDITED = iconFile("blue-document-attribute-z.png");
	public static final ImageIcon DOCUMENT_FLAGGED = iconFile("document-bookmark.png");
	public static final ImageIcon DOCUMENT_EDITED_FLAGGED = iconFile("blue-document-bookmark.png");
	public static final ImageIcon DOCUMENT_MAIN = iconFile("document--arrow.png");
	public static final ImageIcon TAB_CLOSE = iconFile("cross-small.png");

	public static final ImageIcon RUN_SLOW = iconFile("control.png");
	public static final ImageIcon RUN_FAST = iconFile("control-double.png");
	public static final ImageIcon RUN_INCR = iconFile("control-stop.png");
	public static final ImageIcon RUN_STEP = iconFile("arrow-step-over.png");
	public static final ImageIcon RUN_CURSOR = iconFile("control-cursor.png");
	public static final ImageIcon RUN_PAUSE = iconFile("control-pause.png");

	public static final ImageIcon STATUS_GREEN = iconFile("status.png");
	public static final ImageIcon STATUS_YELLOW = iconFile("status-away.png");
	public static final ImageIcon STATUS_RED = iconFile("status-busy.png");
	public static final ImageIcon STATUS_GRAY = iconFile("status-offline.png");

	public static final ImageIcon LINE_ERROR = iconFile("status-busy-shadowless.png");

	public static final ImageIcon KEYPAD = iconFile("keyboard.png");
	public static final ImageIcon DISPLAY = iconFile("monitor.png");
	public static final ImageIcon CPUMON = iconFile("processor.png");

	public static final ImageIcon TREE_PROJECT = iconFile("application-list.png");
	public static final ImageIcon TREE_FOLDER = iconFile("blue-folder-horizontal.png");
	public static final ImageIcon TREE_FOLDER_OPEN = iconFile("blue-folder-horizontal-open.png");
	public static final ImageIcon TREE_FILE = DOCUMENT;
	public static final ImageIcon TREE_FILE_MAIN = DOCUMENT_FLAGGED;

	public static final ImageIcon ADD = iconFile("plus.png");
	public static final ImageIcon ADD_LARGE = iconFile("plus-32.png");
	public static final ImageIcon REMOVE = iconFile("minus.png");
	
	public static final ImageIcon ADD_FILE = iconFile("document--plus.png");
	public static final ImageIcon REMOVE_FILE = iconFile("document--minus.png");

	private Icons() {
		throw new RuntimeException("You didn't REALLY just try to instantiate a utility class, did you?");
	}

	/**
	 * Creates an ImageIcon with the specified image file, located in
	 * /resources/images/icons
	 *
	 * @param string
	 *            - The filename for the icon image file
	 */
	public static ImageIcon iconFile(String string) {
		String resourceRef = "org/cajunc2/system/ui/resources/icons/" + string;
		URL iconLocation = ClassLoader.getSystemClassLoader().getResource(resourceRef);
		if (iconLocation == null) {
			throw new RuntimeException("Unable to load icon: " + resourceRef);
		}
		return new ImageIcon(iconLocation);
	}

	/**
	 * Creates solid-color icon with the specified color and size
	 *
	 * @param color
	 *            - The color for the icon
	 * @param size
	 *            - The size of the icon
	 */
	public static Icon solidColor(Color color, Dimension size) {
		return solidColor(color, size.width, size.height);
	}

	/**
	 * Creates solid-color icon with the specified color and size
	 *
	 * @param color
	 *            - The color for the icon
	 * @param width
	 *            - The width of the icon
	 * @param height
	 *            - The height of the icon
	 */
	public static Icon solidColor(Color color, int width, int height) {
		Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, width, height);
		image.flush();
		return new ImageIcon(image);
	}
}
