package org.cajunc2.dev80.ui.menu;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.KeyEvent;

import org.cajunc2.dev80.ui.listener.EventActionListener;
import org.cajunc2.dev80.ui.topic.Commands;

class FileMenu extends Menu {
	private static final long serialVersionUID = 1L;

	public FileMenu() {
		super("File");

		MenuItem newFile = new MenuItem("New File");
		newFile.setShortcut(new MenuShortcut(KeyEvent.VK_N, false));
		newFile.addActionListener(new EventActionListener<>(Commands.NEW_FILE));
		add(newFile);

		MenuItem openFile = new MenuItem("Open File...");
		openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
		openFile.addActionListener(new EventActionListener<>(Commands.OPEN_FILE));
		add(openFile);

		add(new RecentFilesMenu());

		addSeparator();

		MenuItem closeFile = new MenuItem("Close File");
		closeFile.setShortcut(new MenuShortcut(KeyEvent.VK_W, false));
		closeFile.addActionListener(new EventActionListener<>(Commands.CLOSE_FILE));
		add(closeFile);

		addSeparator();

		MenuItem saveFile = new MenuItem("Save");
		saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
		saveFile.addActionListener(new EventActionListener<>(Commands.SAVE_FILE));
		add(saveFile);

		MenuItem saveAs = new MenuItem("Save As...");
		saveAs.addActionListener(new EventActionListener<>(Commands.SAVE_AS_FILE));
		add(saveAs);

		MenuItem saveAll = new MenuItem("Save All");
		saveAll.setShortcut(new MenuShortcut(KeyEvent.VK_S, true));
		saveAll.addActionListener(new EventActionListener<>(Commands.SAVE_ALL_FILES));
		add(saveAll);

	}
}