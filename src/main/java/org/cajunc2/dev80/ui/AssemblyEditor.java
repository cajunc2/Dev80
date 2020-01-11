package org.cajunc2.dev80.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.event.InputEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.linklabels.LabelLinkGenerator;
import org.cajunc2.dev80.ui.listener.DocumentChangeListener;
import org.cajunc2.dev80.ui.syntax.Z80AutoCompletion;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.util.io.BufferedCopy;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rtextarea.RTextAreaEditorKit;

public class AssemblyEditor extends RSyntaxTextArea {
	private static final long serialVersionUID = -8328148647506863227L;

	static final Logger logger = Logger.getLogger(AssemblyEditor.class.getName());

	private File currentFile = null;
	static {
		AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
		atmf.putMapping("text/asmz80", "org.cajunc2.dev80.ui.syntax.Z80TokenMaker");
	}

	public AssemblyEditor(Project project) {
		this.setLinkScanningMask(InputEvent.META_DOWN_MASK);
		this.setSyntaxEditingStyle("text/asmz80");
		this.setSyntaxScheme(new DefaultSyntaxScheme());
		this.setCaretColor(Color.BLACK);
		this.setTabSize(4);
		this.setHighlightCurrentLine(true);
		this.setCurrentLineHighlightColor(new Color(245, 250, 255));
		this.setMarginLineEnabled(true);
		this.setMarginLinePosition(36);
		this.setHyperlinksEnabled(true);
		this.setLinkGenerator(new LabelLinkGenerator(project.getLabelIndex()));
		this.addCaretListener((evt) -> {
			SwingUtilities.invokeLater(() -> {
				Events.CARET_MOVED.publish(AssemblyEditor.this);
			});
		});

		new Z80AutoCompletion().install(this);

		getDocument().addDocumentListener(new DocumentChangeListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				Events.SOURCE_CHANGED.publish(AssemblyEditor.this);
			}

		});
	}

	public AssemblyEditor(Project project, File file) throws IOException {
		this(project);
		Reader reader = new FileReader(file);
		try {
			read(reader, null);
			this.currentFile = file;
			setCaretPosition(0);
			Events.SOURCE_CHANGED.publish(this);
		} finally {
			reader.close();
		}
	}

	@Override
	public void read(Reader in, Object desc) throws IOException {
		RTextAreaEditorKit kit = (RTextAreaEditorKit) getUI().getEditorKit(this);
		Document doc = new RSyntaxDocument(getSyntaxEditingStyle());
		if (desc != null) {
			doc.putProperty(Document.StreamDescriptionProperty, desc);
		}
		try {
			kit.read(in, doc, 0);
			setDocument(doc);
			this.setTabSize(4);

			doc.addDocumentListener(new DocumentChangeListener() {

				@Override
				public void changedUpdate(DocumentEvent e) {
					Events.SOURCE_CHANGED.publish(AssemblyEditor.this);
				}

			});
		} catch (BadLocationException e) {
			throw new IOException(e.getMessage());
		}
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

}
