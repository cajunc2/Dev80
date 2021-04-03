package org.cajunc2.dev80.newui.menu;

import java.awt.AWTEvent;
import java.awt.event.InputEvent;

import javax.swing.FocusManager;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.cajunc2.dev80.newui.EditorWindow;
import org.cajunc2.dev80.newui.project.ProjectWindow;

class EditMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	public EditMenu(EditorWindow editorWindow, ProjectWindow projectWindow) {
		super("Edit");
		JMenuItem undoItem = new JMenuItem("Undo");
		undoItem.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.META_DOWN_MASK));
		add(undoItem);
		JMenuItem redoItem = new JMenuItem("Redo");
		redoItem.setAccelerator(
				KeyStroke.getKeyStroke('Z', InputEvent.META_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		add(redoItem);

		addSeparator();

		JMenuItem cutItem = new JMenuItem("Cut");
		cutItem.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.META_DOWN_MASK));
		add(cutItem);

		JMenuItem copyItem = new JMenuItem("Copy");
		copyItem.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.META_DOWN_MASK));
		add(copyItem);

		JMenuItem pasteItem = new JMenuItem("Paste");
		pasteItem.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.META_DOWN_MASK));
		add(pasteItem);

		JMenuItem selectAllItem = new JMenuItem("Select All");
		selectAllItem.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.META_DOWN_MASK));
		selectAllItem.addActionListener(evt -> {
			AWTEvent event = new SelectAllEvent();
			FocusManager.getCurrentManager().getActiveWindow().dispatchEvent(event);
		});

		add(selectAllItem);
	}

	private static class SelectAllEvent extends AWTEvent {
		private static final long serialVersionUID = 1L;

		public SelectAllEvent() {
			super(null, 0);
		}

	}
}