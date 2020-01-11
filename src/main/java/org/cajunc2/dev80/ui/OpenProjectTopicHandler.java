package org.cajunc2.dev80.ui;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.util.topic.TopicHandler;

public class OpenProjectTopicHandler implements TopicHandler<Void> {
	private static final Logger logger = Logger.getLogger(OpenProjectTopicHandler.class.getName());
	private final MainWindow window;

	public OpenProjectTopicHandler(MainWindow window) {
		this.window = window;
	}

	@Override
	public void topicReceived(Void payload) {
		JFileChooser chooser = new ProjectFileChooser();
		chooser.showOpenDialog(window);
		File projectFile = chooser.getSelectedFile();
		if (projectFile == null) {
			return;
		}
		try {
			Project project = Project.load(projectFile);
			window.changeProject(project);
			Commands.SET_BUILD_TARGET.publish(project.getCompileFile());
			Events.PROJECT_OPENED.publish(project);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

	private static class ProjectFileChooser extends JFileChooser {
		private static final long serialVersionUID = 1L;

		public ProjectFileChooser() {
			this.setFileHidingEnabled(false);
		}

		@Override
		public boolean accept(File f) {
			return (f.isDirectory() && !f.getName().startsWith(".")) || Project.FILE_NAME.equals(f.getName());
		}

	}
}
