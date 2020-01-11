package org.cajunc2.dev80.ui.worker;

import java.io.File;

import nl.grauw.glass.AssemblyException;

public class CompileError {

	private final File sourceFile;
	private final int lineNumber;
	private final String errorDescription;

	public CompileError(String errorDescription, AssemblyException e) {
		this.errorDescription = errorDescription;
		this.sourceFile = e.getSourceFile();
		this.lineNumber = e.getLineNumber();
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public File getSourceFile() {
		return sourceFile;
	}
}
