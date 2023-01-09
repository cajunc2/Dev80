package org.cajunc2.dev80.ui.worker;

import java.io.File;

import nl.grauw.glass.AssemblyException;

public class CompileError {

	private final String errorDescription;
	private final AssemblyException assemblyException;

	public CompileError(String errorDescription, AssemblyException e) {
		this.errorDescription = errorDescription;
		this.assemblyException = e;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public int getLineNumber() {
		return assemblyException.getContexts().get(0).getLineStart();
	}

	public File getSourceFile() {
		return assemblyException.getContexts().get(0).getSourceFile().getPath().toFile();
	}
}
