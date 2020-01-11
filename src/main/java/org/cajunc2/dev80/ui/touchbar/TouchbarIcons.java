package org.cajunc2.dev80.ui.touchbar;

import java.io.InputStream;

import com.thizzer.jtouchbar.common.Image;

public class TouchbarIcons {

	/**
	 * Creates an Image with the specified image file, located in /resources/images/icons
	 *
	 * @param iconFileName
	 *            - The filename for the icon image file
	 * @throws Exception
	 */
	public static Image iconFile(String iconFileName) {
		try {
			String resourceRef = "org/cajunc2/system/ui/resources/icons/" + iconFileName;
			InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceRef);
			try {
				if (stream == null) {
					throw new RuntimeException("Unable to load icon: " + resourceRef);
				}
				return new Image(stream);
			} finally {
				if (stream != null) {
					stream.close();
				}
			}
		} catch (Exception e) {
			System.err.println("Unable to load Touchbar Icon: " + iconFileName);
			return null;
		}
	}

}
