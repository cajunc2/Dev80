package org.cajunc2.dev80.newui.menu;

import java.awt.event.InputEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.cajunc2.dev80.newui.EditorWindow;
import org.cajunc2.dev80.newui.project.ProjectWindow;
import org.cajunc2.dev80.ui.listener.EventActionListener;
import org.cajunc2.dev80.ui.topic.Commands;

class ProjectMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	public ProjectMenu(EditorWindow editorWindow, ProjectWindow projectWindow) {
		super("Project");

		JMenuItem addFilesItem = new JMenuItem("Add Files...");
		addFilesItem.setEnabled(projectWindow != null);
		addFilesItem.addActionListener((evt) -> {
			projectWindow.addFiles();
		});
		add(addFilesItem);

		JMenuItem removeFileItem = new JMenuItem("Remove File");
		removeFileItem.setEnabled(projectWindow != null);
		removeFileItem.addActionListener((evt) -> {
			projectWindow.removeFile();
		});
		add(removeFileItem);

		JMenuItem setMainFileItem = new JMenuItem("Set Main Source File");
		setMainFileItem.setEnabled(projectWindow != null);
		setMainFileItem.addActionListener((evt) -> {
			try {
				projectWindow.setMainFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		add(setMainFileItem);

		addSeparator();

		JMenuItem buildProject = new JMenuItem("Build Project");
		buildProject.setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.META_DOWN_MASK));
		buildProject.addActionListener(new EventActionListener<Void>(Commands.BUILD_PROJECT));
		add(buildProject);

		JMenuItem writeROM = new JMenuItem("Write to EEPROM");
		writeROM.setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.META_DOWN_MASK));
		writeROM.addActionListener(new EventActionListener<Void>(Commands.BURN_ROM));
		add(writeROM);
	}
}