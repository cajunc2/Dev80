package org.cajunc2.dev80.ui.menu;

import java.awt.MenuBar;

public class MainMenuBar extends MenuBar {

	private static final long serialVersionUID = 1L;

	public MainMenuBar() {
		add(new ProjectMenu());
		add(new FileMenu());
		add(new BuildMenu());
		add(new ViewMenu());
	}
}
