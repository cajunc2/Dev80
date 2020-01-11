package org.cajunc2.dev80.ui;

import java.io.File;

import org.cajunc2.dev80.ui.topic.Commands;

public class SourceLine {
	private final File sourceFile;
	private final int lineNumber;

	public SourceLine(File sourceFile, int lineNumber) {
		this.sourceFile = sourceFile;
		this.lineNumber = lineNumber;
	}

	public void show() {
		Commands.SHOW_FILE_LOCATION.publish(this);
	}

	public File getSourceFile() {
		return sourceFile;
	}

	public int getLineNumber() {
		return lineNumber;
	}
}
