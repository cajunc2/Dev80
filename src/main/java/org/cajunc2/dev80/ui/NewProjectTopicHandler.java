package org.cajunc2.dev80.ui;

import org.cajunc2.util.topic.TopicHandler;

public class NewProjectTopicHandler implements TopicHandler<Void> {
	private final MainWindow window;

	public NewProjectTopicHandler(MainWindow window) {
		this.window = window;
	}

	@Override
	public void topicReceived(Void payload) {
		NewProjectWindow npw = new NewProjectWindow(window);
		npw.setVisible(true);
	}

}
