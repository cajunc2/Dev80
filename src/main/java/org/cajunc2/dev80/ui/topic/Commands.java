package org.cajunc2.dev80.ui.topic;

import java.io.File;

import org.cajunc2.dev80.project.LabelLocation;
import org.cajunc2.dev80.ui.SourceLine;
import org.cajunc2.util.topic.Topic;

public class Commands {

	public static final Topic<Void> NEW_PROJECT = new Topic<Void>();
	public static final Topic<Void> OPEN_PROJECT = new Topic<Void>();
	public static final Topic<File> OPEN_SPECIFIC_PROJECT = new Topic<File>();
	public static final Topic<Void> REFRESH_PROJECT = new Topic<Void>();

	public static final Topic<Void> NEW_FILE = new Topic<Void>();
	public static final Topic<Void> OPEN_FILE = new Topic<Void>();
	public static final Topic<Void> CLOSE_FILE = new Topic<Void>();
	public static final Topic<File> OPEN_SPECIFIC_FILE = new Topic<File>();
	public static final Topic<Void> SAVE_FILE = new Topic<Void>();
	public static final Topic<Void> SAVE_AS_FILE = new Topic<Void>();
	public static final Topic<Void> SAVE_ALL_FILES = new Topic<Void>();

	public static final Topic<File> SET_BUILD_TARGET = new Topic<File>();
	public static final Topic<Void> BUILD_PROJECT = new Topic<Void>();
	public static final Topic<byte[]> EXPORT_ROM = new Topic<byte[]>();
	public static final Topic<Void> BURN_ROM = new Topic<Void>();

	public static final Topic<Void> RUN_SYSTEM = new Topic<Void>();
	public static final Topic<Void> RUN_SYSTEM_SLOW = new Topic<Void>();
	public static final Topic<Void> RUN_SYSTEM_STEP = new Topic<Void>();
	public static final Topic<Void> RESET_SYSTEM = new Topic<Void>();
	public static final Topic<Void> SYSTEM_STOP = new Topic<Void>();
	public static final Topic<Void> INTERRUPT = new Topic<Void>();

	public static final Topic<Void> SHOW_DISPLAY = new Topic<Void>();
	public static final Topic<Void> SHOW_KEYPAD = new Topic<Void>();
	public static final Topic<Void> SHOW_CPUMON = new Topic<Void>();
	public static final Topic<Void> TOGGLE_HELP_PANEL = new Topic<Void>();

	public static final Topic<SourceLine> SHOW_FILE_LOCATION = new Topic<SourceLine>();
	public static final Topic<LabelLocation> GO_TO_LABEL = new Topic<LabelLocation>();

}
