package org.cajunc2.dev80;

import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.apple.eawt.Application;

import org.cajunc2.dev80.ui.Icons;
import org.cajunc2.dev80.ui.MainWindow;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class.getName());
	static {
		System.setProperty("apple.awt.application.name", "Dev80");
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Dev80");
	}

	public static void main(String... args) {
		try {
			setNimbusLookAndFeel();
			Application application = Application.getApplication();
			application.setDockIconImage(Icons.APP_ICON.getImage());
			SwingUtilities.invokeLater(Main::createWindow);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unexpected exception caught, exiting...", e);
			System.exit(-1);
		}

	}

	private static void setNimbusLookAndFeel() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			setSystemLookAndFeel();
		}
	}

	private static void setSystemLookAndFeel() {
		String lafName = UIManager.getSystemLookAndFeelClassName();

		try {
			UIManager.setLookAndFeel(lafName);
			if (lafName.equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")) {
				UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", Boolean.TRUE);
				UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(-1, 1, 2, 3));
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			// Unable to set any particular LAF. Java will go ahead
			// and use whatever it's got, so this isn't fatal, just ugly :)
			logger.info(e.getMessage());
		}
	}

	private static void createWindow() {
		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
	}

}
