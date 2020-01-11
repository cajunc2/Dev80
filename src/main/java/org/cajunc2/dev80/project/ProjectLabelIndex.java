package org.cajunc2.dev80.project;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectLabelIndex {
	private static final Logger logger = Logger.getLogger(ProjectLabelIndex.class.getName());
	private final Map<String, LabelLocation> locations = new HashMap<>();

	public boolean hasLabel(String test) {
		return test != null && locations.containsKey(test);
	}

	public void add(String label, File file, int lineNumber) {
		if (logger.isLoggable(Level.FINEST)) {
			logger.finest("Adding label: " + label);
		}
		this.locations.put(label, new LabelLocation(file, lineNumber));
	}

	public LabelLocation find(String label) {
		return locations.get(label);
	}

	public void clear() {
		locations.clear();
	}
}
