package org.cajunc2.dev80;

import java.awt.Desktop;
import java.awt.Taskbar;
import java.awt.Window;
import java.awt.desktop.QuitEvent;
import java.awt.desktop.QuitResponse;
import java.awt.event.WindowEvent;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import org.cajunc2.dev80.newui.WelcomeWindow;
import org.cajunc2.dev80.newui.menu.MainMenuBar;
import org.cajunc2.dev80.ui.Icons;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class.getName());
	public static final boolean DARK_MODE;

	static {
		System.setProperty("sun.java2d.opengl", "True");
		System.setProperty("apple.awt.application.name", "Dev80");
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		JFrame.setDefaultLookAndFeelDecorated(true);
		DARK_MODE = isMacDarkMode();
	}

	public static void main(String... args) {
		try {
			configureUI();
			Desktop.getDesktop().setAboutHandler((evt) -> {
				String versionNumber = "Unknown Version";
				try {
					Properties p = new Properties();
					p.load(Main.class.getResourceAsStream("/version-info.properties"));
					versionNumber = p.getProperty("system.version");
				} catch (Exception e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "About Dev80 " + versionNumber);
			});
			Desktop.getDesktop().setQuitHandler((QuitEvent e, QuitResponse response) -> {
				for (Window win : Window.getWindows()) {
					if (win.isVisible()) {
						win.dispatchEvent(new WindowEvent(win, WindowEvent.WINDOW_CLOSING));
						if (win.isVisible()) {
							response.cancelQuit();
							return;
						}
					}
				}
				response.performQuit();
			});
			Taskbar.getTaskbar().setIconImage(Icons.APP_ICON.getImage());
			Desktop.getDesktop().setDefaultMenuBar(new MainMenuBar(null, null));
			new WelcomeWindow().setVisible(true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unexpected exception caught, exiting...", e);
			System.exit(-1);
		}

	}

	private static void configureUI() {
		if (DARK_MODE) {
			FlatDarkLaf.install();
		} else {
			FlatLightLaf.install();
		}
		UIManager.put("ScrollBar.showButtons", true);
		UIManager.put("ScrollBar.width", 14);
		UIManager.put("Component.arrowType", "triangle");
	}

	/**
	 * @return true if <code>defaults read -g AppleInterfaceStyle</code> has an exit
	 *         status of <code>0</code> (i.e. _not_ returning "key not found").
	 */
	private static boolean isMacDarkMode() {
		try {
			// check for exit status only. Once there are more modes than "dark" and
			// "default", we might need to analyze string contents..
			final Process proc = Runtime.getRuntime()
					.exec(new String[] { "defaults", "read", "-g", "AppleInterfaceStyle" });
			proc.waitFor(100, TimeUnit.MILLISECONDS);
			return proc.exitValue() == 0;
		} catch (Exception ex) {
			// IllegalThreadStateException thrown by proc.exitValue(), if process didn't
			// terminate
			System.err.println(
					"Could not determine, whether 'dark mode' is being used. Falling back to default (light) mode.");
			return false;
		}
	}

}
