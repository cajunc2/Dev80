package org.cajunc2.dev80.ui;

import javax.swing.JProgressBar;

import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.util.topic.TopicHandler;

public class ROMBurnProgressBar extends JProgressBar {
	private static final long serialVersionUID = 1L;

	public ROMBurnProgressBar() {
		this.setString("Writing EEPROM...");
		this.setStringPainted(true);
		this.setVisible(false);
		this.setIndeterminate(true);

		Commands.BURN_ROM.subscribe(new BurnStartedTopicHandler(this));
		Events.ROM_BURN_FINISHED.subscribe(new BurnFinishedTopicHandler(this));
	}

	private class BurnStartedTopicHandler implements TopicHandler<Void> {
		private final JProgressBar bar;

		BurnStartedTopicHandler(JProgressBar bar) {
			this.bar = bar;
		}

		@Override
		public void topicReceived(Void payload) {
			bar.setVisible(true);
		}
	}

	private class BurnFinishedTopicHandler implements TopicHandler<Boolean> {
		private final JProgressBar bar;

		BurnFinishedTopicHandler(JProgressBar bar) {
			this.bar = bar;
		}

		@Override
		public void topicReceived(Boolean payload) {
			bar.setVisible(false);
		}
	}

}
