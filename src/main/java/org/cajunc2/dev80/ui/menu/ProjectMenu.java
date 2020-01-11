package org.cajunc2.dev80.ui.menu;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.KeyEvent;

import org.cajunc2.dev80.ui.listener.EventActionListener;
import org.cajunc2.dev80.ui.topic.Commands;

class ProjectMenu extends Menu {
	private static final long serialVersionUID = 1L;

	public ProjectMenu() {
		super("Project");

		MenuItem newProject = new MenuItem("New Project");
		newProject.setShortcut(new MenuShortcut(KeyEvent.VK_N, true));
		newProject.addActionListener(new EventActionListener<>(Commands.NEW_PROJECT));
		add(newProject);

		MenuItem openProject = new MenuItem("Open Project...");
		openProject.setShortcut(new MenuShortcut(KeyEvent.VK_O, true));
		openProject.addActionListener(new EventActionListener<>(Commands.OPEN_PROJECT));
		add(openProject);

		add(new RecentProjectsMenu());
	}
}