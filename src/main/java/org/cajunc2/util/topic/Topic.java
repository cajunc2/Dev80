package org.cajunc2.util.topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class Topic<T> {

	final Set<TopicHandler<T>> handlers = Collections.synchronizedSet(new HashSet<TopicHandler<T>>());
	private final List<Operation> pendingQueue = Collections.synchronizedList(new ArrayList<Operation>());

	private AtomicBoolean isPublishing = new AtomicBoolean(false);

	public void subscribe(TopicHandler<T> handler) {
		if (isPublishing.get()) {
			pendingQueue.add(new AddOperation(handler));
			return;
		}
		handlers.add(handler);
	}

	public void unsubscribe(TopicHandler<T> handler) {
		if (isPublishing.get()) {
			pendingQueue.add(new RemoveOperation(handler));
			return;
		}
		handlers.remove(handler);
	}

	public void publish() {
		publish(null);
	}

	public void publish(T data) {
		if (isPublishing.compareAndSet(false, true)) {
			for (TopicHandler<T> handler : handlers) {
				handler.topicReceived(data);
			}
			processPendingQueue();
			isPublishing.set(false);
		} else {
			pendingQueue.add(new PublishOperation(data));
		}
	}

	private void processPendingQueue() {
		Iterator<Operation> itr = pendingQueue.iterator();
		while (itr.hasNext()) {
			Operation o = itr.next();
			itr.remove();
			o.perform();
		}
	}

	private interface Operation {
		void perform();
	}

	private class AddOperation implements Operation {
		private final TopicHandler<T> handler;

		public AddOperation(TopicHandler<T> handler) {
			this.handler = handler;
		}

		@Override
		public void perform() {
			handlers.add(handler);
		}
	}

	private class RemoveOperation implements Operation {
		private final TopicHandler<T> handler;

		public RemoveOperation(TopicHandler<T> handler) {
			this.handler = handler;
		}

		@Override
		public void perform() {
			handlers.remove(handler);
		}
	}

	private class PublishOperation implements Operation {
		private final T payload;

		public PublishOperation(T payload) {
			this.payload = payload;
		}

		@Override
		public void perform() {
			for (TopicHandler<T> handler : handlers) {
				handler.topicReceived(payload);
			}
		}
	}
}
