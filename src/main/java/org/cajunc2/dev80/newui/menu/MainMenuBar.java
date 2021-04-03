package org.cajunc2.dev80.newui.menu;

import javax.swing.JMenuBar;

import org.cajunc2.dev80.newui.EditorWindow;
import org.cajunc2.dev80.newui.project.ProjectWindow;

public class MainMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public enum Type {
		NO_WINDOWS, PROJECT_WINDOW, EDITOR_WINDOW
	}
	
	public MainMenuBar(EditorWindow editorWindow, ProjectWindow projectWindow) {
		add(new FileMenu(editorWindow, projectWindow));
		add(new EditMenu(editorWindow, projectWindow));
		add(new SearchMenu(editorWindow, projectWindow));
		add(new ProjectMenu(editorWindow, projectWindow));
		add(new WindowMenu(editorWindow, projectWindow));
	}

}