package org.cajunc2.dev80.ui;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.linklabels.LabelParseWorker;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.util.topic.TopicHandler;

public class OpenSpecificProjectTopicHandler implements TopicHandler<File> {
	private static final Logger logger = Logger.getLogger(OpenSpecificProjectTopicHandler.class.getName());
	private final MainWindow window;

	public OpenSpecificProjectTopicHandler(MainWindow window) {
		this.window = window;
	}

	@Override
	public void topicReceived(File payload) {
		try {
			Project project = Project.load(payload);
			window.changeProject(project);
			Commands.SET_BUILD_TARGET.publish(project.getCompileFile());

			LabelParseWorker worker = new LabelParseWorker(project);
			SwingUtilities.invokeLater(worker);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
	}
}
