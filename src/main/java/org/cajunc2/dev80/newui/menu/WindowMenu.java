package org.cajunc2.dev80.newui.menu;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.cajunc2.dev80.newui.EditorWindow;
import org.cajunc2.dev80.newui.HelpDialog;
import org.cajunc2.dev80.newui.WelcomeWindow;
import org.cajunc2.dev80.newui.project.ProjectWindow;

class WindowMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private final HelpDialog helpDialog = HelpDialog.getInstance();

	public WindowMenu(EditorWindow editorWindow, ProjectWindow projectWindow) {
		super("Window");

		JMenuItem stackItem = new JMenuItem("Stack");
		add(stackItem);

		JMenuItem tileItem = new JMenuItem("Tile");
		add(tileItem);

		JMenuItem tileVerticalItem = new JMenuItem("Tile Vertical");
		add(tileVerticalItem);

		addSeparator();

		JMenuItem welcomeWindowItem = new JMenuItem("Welcome");
		welcomeWindowItem.addActionListener((evt) -> {
			for (Window win : Window.getWindows()) {
				if (win instanceof WelcomeWindow && win.isVisible()) {
					win.requestFocus();
					return;
				}
			}
			new WelcomeWindow().setVisible(true);
		});
		add(welcomeWindowItem);

		JMenuItem instructionHelp = new JCheckBoxMenuItem("Quick Help");
		instructionHelp.setAccelerator(KeyStroke.getKeyStroke('I', InputEvent.META_DOWN_MASK));
		instructionHelp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getModifiers() == 0) {
					helpDialog.toggle();
				}
			}
		});
		add(instructionHelp);
	}

	void setProjectWindow() {

	}
}