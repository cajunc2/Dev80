package org.cajunc2.dev80.newui.menu;

import java.awt.event.InputEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.cajunc2.dev80.newui.EditorWindow;
import org.cajunc2.dev80.newui.project.ProjectWindow;

class SearchMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	public SearchMenu(EditorWindow editorWindow, ProjectWindow projectWindow) {
		super("Search");

		JMenuItem findItem = new JMenuItem("Find...");
		findItem.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.META_DOWN_MASK));
		add(findItem);

		JMenuItem findReplaceItem = new JMenuItem("Find and Replace...");
		findReplaceItem.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.META_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		add(findReplaceItem);

		JMenuItem findNextItem = new JMenuItem("Find Next");
		findNextItem.setAccelerator(KeyStroke.getKeyStroke('G', InputEvent.META_DOWN_MASK));
		add(findNextItem);

		JMenuItem findPrevItem = new JMenuItem("Find Previous");
		findPrevItem.setAccelerator(KeyStroke.getKeyStroke('G', InputEvent.META_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		add(findPrevItem);

		addSeparator();

		JMenuItem goLineItem = new JMenuItem("Go to Line...");
		goLineItem.setAccelerator(KeyStroke.getKeyStroke('L', InputEvent.META_DOWN_MASK));
		add(goLineItem);
	}

}