package org.cajunc2.dev80.project;

import java.io.File;

public class LabelLocation {
	private final File file;
	private final int lineNumber;

	public LabelLocation(File file, int lineNumber) {
		super();
		this.file = file;
		this.lineNumber = lineNumber;
	}

	public File getFile() {
		return file;
	}

	public int getLineNumber() {
		return lineNumber;
	}
}
