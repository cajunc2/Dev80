package org.cajunc2.dev80.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;

import org.cajunc2.dev80.Main;
import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.linklabels.LabelLinkGenerator;
import org.cajunc2.dev80.ui.syntax.SyntaxSchemeFactory;
import org.cajunc2.dev80.ui.syntax.Z80AutoCompletion;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.util.io.BufferedCopy;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.FileLocation;
import org.fife.ui.rsyntaxtextarea.TextEditorPane;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;

public class AssemblyEditor extends TextEditorPane {
	private static final long serialVersionUID = -8328148647506863227L;

	static final Logger logger = Logger.getLogger(AssemblyEditor.class.getName());

	private File currentFile = null;
	static {
		AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
		atmf.putMapping("text/asmz80", "org.cajunc2.dev80.ui.syntax.Z80TokenMaker");
	}

	public AssemblyEditor(Project project, File file) throws IOException {
		super(INSERT_MODE, false, new FileFileLocation(file));
		// FileLocation fl = FileLocation.create(file.getAbsolutePath());
		this.setLinkScanningMask(InputEvent.META_DOWN_MASK);
		this.setSyntaxEditingStyle("text/asmz80");
		this.setSyntaxScheme(new SyntaxSchemeFactory().getDefault());

		Color backgroundColor = UIManager.getColor("EditorPane.background");
		if(Main.DARK_MODE) {
			backgroundColor = backgroundColor.darker().darker();
		}
            this.setBackground(backgroundColor);
		this.setCaretColor(Color.BLACK);
		this.setTabSize(4);
		this.setHighlightCurrentLine(true);
		this.setSelectionColor(UIManager.getColor("EditorPane.selectionBackground"));
		this.setSelectedTextColor(UIManager.getColor("EditorPane.selectionForeground"));
		this.setCaretColor(Main.DARK_MODE ? Color.WHITE : Color.BLACK);

		Color focusColor = UIManager.getColor("List.selectionBackground");
		Color lineHighlightColor = new Color(focusColor.getRGB() & 0x11ffffff, true);
		this.setCurrentLineHighlightColor(lineHighlightColor);

		this.setMarginLineColor(new Color(focusColor.getRGB() & 0x33ffffff, true));
		this.setForeground(UIManager.getColor("EditorPane.foreground"));
		
		this.setMarginLineEnabled(true);
		this.setMarginLinePosition(36);
		this.setHyperlinksEnabled(true);
		if (project != null) {
			this.setLinkGenerator(new LabelLinkGenerator(project.getLabelIndex()));
		}

		this.addCaretListener((evt) -> {
			// This drives the Quick Help panel
			Events.CARET_MOVED.publish(this);
		});

		new Z80AutoCompletion().install(this);
	}

	public void saveFile(File file) throws IOException {
		InputStream is = new ByteArrayInputStream(getText().getBytes());
		try {
			OutputStream os = new FileOutputStream(file);
			try {
				new BufferedCopy().copy(is, os);
				Events.FILE_SAVED.publish(this);
			} finally {
				os.close();
			}
		} finally {
			is.close();
		}
	}

	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public void scrollToLineNumber(int lineNumber) {
		Container container = SwingUtilities.getAncestorOfClass(JViewport.class, this);

		if (container == null) {
			return;
		}

		try {
			Rectangle2D r = this.modelToView2D(this.getLineStartOffset(lineNumber));
			JViewport viewport = (JViewport) container;
			int extentHeight = viewport.getExtentSize().height;
			int viewHeight = viewport.getViewSize().height;

			int y = (int) Math.max(0, r.getY() - ((extentHeight - r.getHeight()) / 2));
			y = Math.min(y, viewHeight - extentHeight);

			viewport.setViewPosition(new Point(0, y));
		} catch (BadLocationException ble) {
			logger.log(Level.SEVERE, "", ble);
		}
	}

	public String getCurrentLineCode() {
		try {
			int start = AssemblyEditor.this.getLineStartOffsetOfCurrentLine();
			int end = AssemblyEditor.this.getLineEndOffsetOfCurrentLine();
			String line = AssemblyEditor.this.getText(start, end - start).trim();
			String[] parts = line.split(";");
			if (parts.length == 0) {
				return "";
			}
			return parts[0].trim();
		} catch (BadLocationException e) {
			e.printStackTrace();
			return "";
		}

	}

	private static class FileFileLocation extends FileLocation {

		/**
		 * The file. This may or may not actually exist.
		 */
		private File file;

		/**
		 * Constructor.
		 *
		 * @param file The local file.
		 */
		FileFileLocation(File file) {
			this.file = file;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected long getActualLastModified() {
			return file.lastModified();
		}

		/**
		 * Returns the full path to the file.
		 *
		 * @return The full path to the file.
		 * @see #getFileName()
		 */
		@Override
		public String getFileFullPath() {
			return file.getAbsolutePath();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getFileName() {
			return file.getName();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected InputStream getInputStream() throws IOException {
			return new FileInputStream(file);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected OutputStream getOutputStream() throws IOException {
			return new FileOutputStream(file);
		}

		/**
		 * Always returns <code>true</code>.
		 *
		 * @return <code>true</code> always.
		 * @see #isLocalAndExists()
		 */
		@Override
		public boolean isLocal() {
			return true;
		}

		/**
		 * Since file locations of this type are guaranteed to be local, this method
		 * returns whether the file exists.
		 *
		 * @return Whether this local file actually exists.
		 * @see #isLocal()
		 */
		@Override
		public boolean isLocalAndExists() {
			return file.exists();
		}

	}
	@Override
	public int getLineHeight() {
		return (int) (super.getLineHeight() * 1.1);
	}
}
