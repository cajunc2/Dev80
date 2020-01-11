package org.cajunc2.util.topic;

public interface TopicHandler<T> {
	void topicReceived(T payload);
}