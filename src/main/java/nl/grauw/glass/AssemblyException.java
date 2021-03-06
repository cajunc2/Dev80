package nl.grauw.glass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AssemblyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final List<Context> contexts = new ArrayList<>();

	public AssemblyException() {
		this((Throwable) null);
	}

	public AssemblyException(String message) {
		this(message, null);
	}

	public AssemblyException(Throwable cause) {
		this("Error during assembly.", null);
	}

	public AssemblyException(String message, Throwable cause) {
		super(message, cause);
	}

	public void addContext(Line line) {
		addContext(line.getSourceFile(), line.getLineNumber(), -1, line.getSourceText());
	}

	public void addContext(File file, int line, int column, String text) {
		contexts.add(new Context(file, line, column, text));
	}

	public int getLineNumber() {
		return contexts.get(0).line;
	}

	public File getSourceFile() {
		return contexts.get(0).file;
	}

	@Override
	public String getMessage() {
		String message = super.getMessage();

		for (Context context : contexts)
			message += "\n" + context;

		return message;
	}

	public String getPlainMessage() {
		return super.getMessage();
	}

	private static class Context {

		private final File file;
		private final int line;
		private final int column;
		private final String text;

		public Context(File file, int line, int column, String text) {
			this.file = file;
			this.line = line;
			this.column = column;
			this.text = text;
		}

		@Override
		public String toString() {
			String prefix = "[at " + file + ":" + line + (column != -1 ? "," + column : "") + "]\n";
			String context = prefix + text;

			if (column >= 0) {
				int start = Math.min(context.lastIndexOf('\n') + 1, context.length());
				int end = Math.min(start + column, context.length());
				context += "\n" + context.substring(start, end).replaceAll("[^\t]", " ") + "^";
			}

			return context;
		}

	}

}
