package org.cajunc2.dev80.ui.menu;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.cajunc2.dev80.ui.topic.Commands;

class ViewMenu extends Menu {
	private static final long serialVersionUID = 1L;
	final MenuItem instructionHelp;

	public ViewMenu() {
		super("View");

		instructionHelp = new MenuItem("Hide Quick Help");
		instructionHelp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Commands.TOGGLE_HELP_PANEL.publish();
				if ("Show Quick Help".equals(instructionHelp.getLabel())) {
					instructionHelp.setLabel("Hide Quick Help");
				} else {
					instructionHelp.setLabel("Show Quick Help");
				}
			}
		});
		add(instructionHelp);
	}
}