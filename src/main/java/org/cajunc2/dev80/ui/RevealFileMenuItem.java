package org.cajunc2.dev80.ui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

public class RevealFileMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;

	public RevealFileMenuItem(File targetFile) {
		super();
		setAction(getRevealFileAction(targetFile));
	}

	private static Action getRevealFileAction(File targetFile) {
		String osName = System.getProperty("os.name");
		String osNameMatch = osName.toLowerCase();
		if (osNameMatch.startsWith("windows")) {
			return new WindowsRevealFileAction(targetFile);
		}
		if (osNameMatch.startsWith("mac")) {
			return new MacRevealFileAction(targetFile);
		}
		return new GenericRevealFileAction(targetFile);
	}

	private static class MacRevealFileAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final File targetFile;

		MacRevealFileAction(File targetFile) {
			super("Reveal in Finder");
			this.targetFile = targetFile;
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			try {
				Runtime.getRuntime().exec("open -R " + targetFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static class WindowsRevealFileAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final File targetFile;

		WindowsRevealFileAction(File targetFile) {
			super("Show in Explorer");
			this.targetFile = targetFile;
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			try {
				Runtime.getRuntime().exec("Explorer /select,filename" + targetFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static class GenericRevealFileAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private final File targetFile;

		GenericRevealFileAction(File targetFile) {
			super("Show in File Browser");
			this.targetFile = targetFile;
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			try {
				Desktop.getDesktop().browse(targetFile.getParentFile().toURI());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
