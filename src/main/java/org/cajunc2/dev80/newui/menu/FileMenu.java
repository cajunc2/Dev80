package org.cajunc2.dev80.newui.menu;

import java.awt.Window;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.cajunc2.dev80.newui.EditorWindow;
import org.cajunc2.dev80.newui.NewProjectDialog;
import org.cajunc2.dev80.newui.project.ProjectWindow;
import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.listener.EventActionListener;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.topic.Events;

class FileMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	public FileMenu(EditorWindow editorWindow, ProjectWindow projectWindow) {
		super("File");

		JMenuItem newProjectItem = new JMenuItem("New Project...");
		newProjectItem.setAccelerator(
				KeyStroke.getKeyStroke('N', InputEvent.META_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		newProjectItem.addActionListener((evt) -> {
			new NewProjectDialog().setVisible(true);
		});
		add(newProjectItem);

		JMenuItem openProjectItem = new JMenuItem("Open Folder...");
		openProjectItem.setAccelerator(
				KeyStroke.getKeyStroke('O', InputEvent.META_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		openProjectItem.addActionListener((evt) -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileHidingEnabled(true);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.showOpenDialog(null);
			File selectedFile = chooser.getSelectedFile();
                  Project project = Project.loadFromDirectory(selectedFile);
			new ProjectWindow(project).setVisible(true);
			Events.PROJECT_OPENED.publish(project);
		});
		add(openProjectItem);

		add(new RecentProjectsMenu());

		addSeparator();

		JMenuItem newFile = new JMenuItem("New File");
		newFile.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.META_DOWN_MASK));
		newFile.addActionListener((event) -> {

		});
		add(newFile);

		JMenuItem openFile = new JMenuItem("Open File...");
		openFile.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.META_DOWN_MASK));
		openFile.addActionListener(new EventActionListener<>(Commands.OPEN_FILE));
		add(openFile);

		add(new RecentFilesMenu());

		addSeparator();

		JMenuItem closeFile = new JMenuItem("Close");
		closeFile.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.META_DOWN_MASK));
		if (editorWindow == null && projectWindow == null) {
			closeFile.setEnabled(false);
		}
		closeFile.addActionListener((evt) -> {
			if (editorWindow != null) {
				editorWindow.dispatchEvent(new WindowEvent(editorWindow, WindowEvent.WINDOW_CLOSING));
				return;
			}
			if (projectWindow != null) {
				projectWindow.dispatchEvent(new WindowEvent(projectWindow, WindowEvent.WINDOW_CLOSING));
				return;
			}
		});
		add(closeFile);

		JMenuItem saveFile = new JMenuItem("Save");
		saveFile.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.META_DOWN_MASK));
		saveFile.setEnabled(false);
		if (editorWindow != null) {
			saveFile.setEnabled(true);
			saveFile.addActionListener((evt) -> {
				editorWindow.save();
			});
		}
		add(saveFile);

		JMenuItem saveAs = new JMenuItem("Save As...");
		saveAs.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.META_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		saveAs.setEnabled(false);
		if (editorWindow != null) {
			saveAs.setEnabled(true);
			saveAs.addActionListener((evt) -> {
				editorWindow.saveAs();
			});
		}
		add(saveAs);

		JMenuItem saveAll = new JMenuItem("Save All");
		saveAll.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.META_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		saveAll.addActionListener((evt) -> {
			for (Window win : Window.getWindows()) {
				if (win instanceof EditorWindow) {
					EditorWindow ew = (EditorWindow) win;
					ew.save();
				}
			}
		});
		add(saveAll);
	}

}