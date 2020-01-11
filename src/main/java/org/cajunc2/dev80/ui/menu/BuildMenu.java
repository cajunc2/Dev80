package org.cajunc2.dev80.ui.menu;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.KeyEvent;

import org.cajunc2.dev80.ui.listener.EventActionListener;
import org.cajunc2.dev80.ui.topic.Commands;

public class BuildMenu extends Menu {
	private static final long serialVersionUID = 1L;

	public BuildMenu() {
		super("Build");

		MenuItem buildProject = new MenuItem("Build Project");
		buildProject.setShortcut(new MenuShortcut(KeyEvent.VK_B, false));
		buildProject.addActionListener(new EventActionListener<Void>(Commands.BUILD_PROJECT));
		add(buildProject);

		MenuItem writeROM = new MenuItem("Write to EEPROM");
		writeROM.setShortcut(new MenuShortcut(KeyEvent.VK_B, true));
		writeROM.addActionListener(new EventActionListener<Void>(Commands.BURN_ROM));
		add(writeROM);

		addSeparator();
	}
}
