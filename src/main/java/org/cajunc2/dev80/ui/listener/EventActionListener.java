package org.cajunc2.dev80.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.cajunc2.util.topic.Topic;

public class EventActionListener<T> implements ActionListener {

	private final Topic<T> topic;
	private final T payload;

	public EventActionListener(Topic<T> topic) {
		this.topic = topic;
		this.payload = null;
	}

	public EventActionListener(Topic<T> topic, T payload) {
		this.topic = topic;
		this.payload = payload;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		topic.publish(payload);
	}

}
