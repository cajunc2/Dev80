package org.cajunc2.dev80.ui.topic;

import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.AssemblyEditor;
import org.cajunc2.dev80.ui.worker.CompileError;
import org.cajunc2.util.topic.Topic;

public final class Events {

	public static final Topic<byte[]> COMPILE_SUCCESS = new Topic<byte[]>();
	public static final Topic<CompileError> COMPILE_ERROR = new Topic<CompileError>();
	public static final Topic<AssemblyEditor> SOURCE_CHANGED = new Topic<AssemblyEditor>();
	public static final Topic<Project> PROJECT_OPENED = new Topic<Project>();
	public static final Topic<AssemblyEditor> FILE_OPENED = new Topic<AssemblyEditor>();
	public static final Topic<AssemblyEditor> FILE_SAVED = new Topic<AssemblyEditor>();
	public static final Topic<AssemblyEditor> CARET_MOVED = new Topic<AssemblyEditor>();
	public static final Topic<Void> SYSTEM_STARTED = new Topic<Void>();
	public static final Topic<Void> SYSTEM_PAUSED = new Topic<Void>();
	public static final Topic<Void> SYSTEM_HALTED = new Topic<Void>();
	public static final Topic<Void> SYSTEM_RESET = new Topic<Void>();
	public static final Topic<Boolean> ROM_BURN_FINISHED = new Topic<Boolean>();

}