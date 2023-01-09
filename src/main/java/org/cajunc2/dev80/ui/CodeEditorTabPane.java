package org.cajunc2.dev80.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import org.cajunc2.dev80.project.LabelLocation;
import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.simulator.memory.MemoryView;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.dev80.ui.worker.AssemblerWorker;
import org.cajunc2.dev80.ui.worker.CompileError;
import org.cajunc2.util.topic.TopicHandler;
import org.fife.ui.rtextarea.RTextScrollPane;

public class CodeEditorTabPane extends DnDTabbedPane {

	private static final long serialVersionUID = 6226941278713085568L;
	private static final Logger logger = Logger.getLogger(CodeEditorTabPane.class.getName());
	private final FileDialog fd = new FileDialog((Dialog) null);

	File buildTargetFile;

	private Project project;

	public CodeEditorTabPane(Project project) {
		super();
		this.project = project;
		setOpaque(false);
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		Commands.NEW_FILE.subscribe(this::newFile);
		Commands.OPEN_FILE.subscribe(this::openFile);
		Commands.CLOSE_FILE.subscribe(new CloseFileHandler());
		Commands.OPEN_SPECIFIC_FILE.subscribe(new OpenSpecificFileHandler());
		Commands.SAVE_FILE.subscribe(new SaveFileHandler());
		Commands.SAVE_AS_FILE.subscribe(new SaveAsFileHandler());
		Commands.SAVE_ALL_FILES.subscribe(new SaveAllFilesHandler());
		Commands.BUILD_PROJECT.subscribe(this::clearErrors);
		Events.COMPILE_ERROR.subscribe(this::displayError);
		Commands.GO_TO_LABEL.subscribe(this::goToLabel);
		setComponentPopupMenu(new TabContextMenu());
	}

	public void newFile(Void v) {
		try {
		AssemblyEditor editor = new AssemblyEditor(project, null);
		RTextScrollPane scrollPane = new RTextScrollPane(editor);
		// scrollPane.setBackground(Color.WHITE);
		scrollPane.getGutter().setIconRowHeaderInheritsGutterBackground(true);
		// scrollPane.getGutter().setBackground(Color.WHITE);
		scrollPane.getGutter().setLineNumberFont(new Font("Consolas", Font.PLAIN, 12));
		scrollPane.setIconRowHeaderEnabled(true);
		scrollPane.getGutter().setBookmarkingEnabled(true);
		// scrollPane.setBorder(BorderFactory.createEmptyBorder());
		Icon i = getTabCount() == 0 ? Icons.DOCUMENT : Icons.DOCUMENT;
		addTab("untitled", i, scrollPane);
		setTabComponentAt(getTabCount() - 1, new TabComponent(null));
		setSelectedIndex(getTabCount() - 1);
		editor.grabFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayError(CompileError payload) {
		int tabIndex = -1;
		for (int i = 0; i < getTabCount(); i++) {
			TabComponent tc = (TabComponent) getTabComponentAt(i);
			if (tc.tabFile != null && tc.tabFile.equals(payload.getSourceFile())) {
				tabIndex = i;
				break;
			}
		}
		if (tabIndex == -1) {
			openFile(payload.getSourceFile());
			tabIndex = getTabCount() - 1;
		}
		setSelectedIndex(tabIndex);

		RTextScrollPane scrollPane = ((RTextScrollPane) getComponentAt(tabIndex));
		AssemblyEditor editor = ((AssemblyEditor) scrollPane.getTextArea());
		SwingUtilities.invokeLater(() -> {
			try {
				scrollPane.getGutter().addLineTrackingIcon(payload.getLineNumber(), Icons.LINE_ERROR, payload.getErrorDescription());
				editor.addLineHighlight(payload.getLineNumber(), new Color(255, 192, 192));
				editor.scrollToLineNumber(payload.getLineNumber());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		});
	}

	public void goToLabel(LabelLocation payload) {
		int tabIndex = -1;
		for (int i = 0; i < getTabCount(); i++) {
			TabComponent tc = (TabComponent) getTabComponentAt(i);
			if (tc.tabFile != null && tc.tabFile.equals(payload.getFile())) {
				tabIndex = i;
				break;
			}
		}
		if (tabIndex == -1) {
			openFile(payload.getFile());
			tabIndex = getTabCount() - 1;
		}
		setSelectedIndex(tabIndex);

		RTextScrollPane scrollPane = ((RTextScrollPane) getComponentAt(tabIndex));
		AssemblyEditor editor = ((AssemblyEditor) scrollPane.getTextArea());
		SwingUtilities.invokeLater(() -> {
			editor.scrollToLineNumber(payload.getLineNumber());
			try {
				editor.setCaretPosition(editor.getLineStartOffset(payload.getLineNumber()));
			} catch (BadLocationException e) {
				logger.log(Level.WARNING, "", e);
			}
		});
	}

	public void showSourceLine(SourceLine sourceLine) {
		int tabIndex = -1;
		for (int i = 0; i < getTabCount(); i++) {
			TabComponent tc = (TabComponent) getTabComponentAt(i);
			if (tc.tabFile != null && tc.tabFile.equals(sourceLine.getSourceFile())) {
				tabIndex = i;
				break;
			}
		}
		if (tabIndex == -1) {
			openFile(sourceLine.getSourceFile());
			tabIndex = getTabCount() - 1;
		}
		setSelectedIndex(tabIndex);

		RTextScrollPane scrollPane = ((RTextScrollPane) getComponentAt(tabIndex));
		AssemblyEditor editor = ((AssemblyEditor) scrollPane.getTextArea());
		editor.scrollToLineNumber(sourceLine.getLineNumber());
	}

	public void clearErrors(@SuppressWarnings("unused") Void v) {
		for (int i = 0; i < getTabCount(); i++) {
			RTextScrollPane scrollPane = ((RTextScrollPane) getComponentAt(i));
			scrollPane.getGutter().removeAllTrackingIcons();
			AssemblyEditor editor = ((AssemblyEditor) scrollPane.getTextArea());
			editor.removeAllLineHighlights();
		}
	}

	public void openFile(Void v) {
		fd.setMode(FileDialog.LOAD);
		fd.setMultipleMode(true);
		fd.setVisible(true);
		File[] files = fd.getFiles();
		if (files == null || files.length == 0) {
			return;
		}
		fileIteration:
		for (File selectedFile : files) {
			for (int i = 0; i < getTabCount(); i++) {
				TabComponent tc = (TabComponent) getTabComponentAt(i);
				if (tc.tabFile != null && tc.tabFile.equals(selectedFile)) {
					setSelectedIndex(i);
					continue fileIteration;
				}
			}
			openFile(selectedFile);
		}
	}

	public void openSpecificFile(File selectedFile) {
		for (int i = 0; i < getTabCount(); i++) {
			TabComponent tc = (TabComponent) getTabComponentAt(i);
			if (tc.tabFile != null && tc.tabFile.equals(selectedFile)) {
				setSelectedIndex(i);
				return;
			}
		}
		openFile(selectedFile);
	}

	public void buildFile(MemoryView memoryView) {
		TabComponent buildTargetComponent = null;
		for (int i = 0; i < getTabCount(); i++) {
			TabComponent tc = (TabComponent) getTabComponentAt(i);
			if (tc.getFile() != null && tc.getFile().equals(this.buildTargetFile)) {
				buildTargetComponent = tc;
				break;
			}
		}
		if (buildTargetComponent == null) {
			JOptionPane.showMessageDialog(this, "No build target set");
			return;
		}
		AssemblerWorker worker = new AssemblerWorker(memoryView, buildTargetComponent.getFile());
		try {
			worker.execute();
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void buildFile(File mainFile, MemoryView memoryView) {
		for (int i = 0; i < getTabCount(); i++) {
			TabComponent c = (TabComponent) getTabComponentAt(i);
			if (c.getFile().equals(mainFile)) {
				AssemblerWorker worker = new AssemblerWorker(memoryView, mainFile);
				try {
					worker.execute();
				} catch (RuntimeException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
		}
	}

	public void openFile(File file) {
		try {

			if (this.getTabCount() == 1) {
				TabComponent tc = (TabComponent) getTabComponentAt(0);
				if (tc.getTitle().equals("untitled") && !tc.isDirty()) {
					this.removeTabAt(0);
				}
			}
			AssemblyEditor editor = new AssemblyEditor(project, file);
			RTextScrollPane scrollPane = new RTextScrollPane(editor);
			scrollPane.setBackground(Color.WHITE);
			scrollPane.getGutter().setIconRowHeaderInheritsGutterBackground(true);
			scrollPane.getGutter().setBackground(Color.WHITE);
			scrollPane.getGutter().setLineNumberFont(new Font("Consolas", Font.PLAIN, 12));
			scrollPane.setIconRowHeaderEnabled(true);
			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			addTab(file.getName(), Icons.DOCUMENT, scrollPane, file.getAbsolutePath());
			TabComponent tc = new TabComponent(file);
			setTabComponentAt(getTabCount() - 1, tc);
			setSelectedIndex(getTabCount() - 1);
			Events.FILE_OPENED.publish(editor);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

	void saveCurrentTabAs() {
		int currentTab = this.getSelectedIndex();
		if (currentTab == -1) {
			return;
		}
		RTextScrollPane editorScrollPane = (RTextScrollPane) CodeEditorTabPane.this.getComponentAt(currentTab);
		AssemblyEditor editor = (AssemblyEditor) editorScrollPane.getTextArea();
		chooseFileForEditor(editor);
		try {
			editor.saveFile(editor.getCurrentFile());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

	void saveCurrentTab() {
		int currentTab = this.getSelectedIndex();
		if (currentTab == -1) {
			return;
		}
		RTextScrollPane editorScrollPane = (RTextScrollPane) CodeEditorTabPane.this.getComponentAt(currentTab);
		AssemblyEditor editor = (AssemblyEditor) editorScrollPane.getTextArea();
		File editorFile = editor.getCurrentFile();
		if (editorFile == null) {
			chooseFileForEditor(editor);
		}
		try {
			editor.saveFile(editor.getCurrentFile());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

	void saveTab(int tabIndex) {
		RTextScrollPane editorScrollPane = (RTextScrollPane) CodeEditorTabPane.this.getComponentAt(tabIndex);
		AssemblyEditor editor = (AssemblyEditor) editorScrollPane.getTextArea();
		File editorFile = editor.getCurrentFile();
		if (editorFile == null) {
			chooseFileForEditor(editor);
		}
		try {
			editor.saveFile(editor.getCurrentFile());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

	private void chooseFileForEditor(AssemblyEditor editor) {
		fd.setMode(FileDialog.SAVE);
		if (project != null) {
			fd.setDirectory(project.getProjectDir().getAbsolutePath());
		}
		fd.setVisible(true);
		File[] files = fd.getFiles();
		if (files == null || files.length == 0) {
			return;
		}
		File selectedFile = files[0];
		editor.setCurrentFile(selectedFile);
	}

	private class CloseFileHandler implements TopicHandler<Void> {

		CloseFileHandler() {
		}

		@Override
		public void topicReceived(Void payload) {
			int i = CodeEditorTabPane.this.getSelectedIndex();
			if (i < 0 || i >= CodeEditorTabPane.this.getTabCount()) {
				return;
			}
			TabComponent tc = (TabComponent) CodeEditorTabPane.this.getTabComponentAt(i);
			if (tc.isDirty()) {
				String msg = "File " + tc.label.getText() + " has unsaved changes. What would you like to do?";
				int response = JOptionPane.showOptionDialog(CodeEditorTabPane.this, msg, "Confirm", 0, JOptionPane.QUESTION_MESSAGE, null,
				        new String[] { "Leave Open", "Close Editor" }, "Leave Open");
				if (response != 1) {
					return;
				}
			}
			CodeEditorTabPane.this.remove(i);
		}

	}

	private class OpenSpecificFileHandler implements TopicHandler<File> {

		OpenSpecificFileHandler() {
		}

		@Override
		public void topicReceived(File payload) {
			CodeEditorTabPane.this.openSpecificFile(payload);
		}

	}

	private class SaveFileHandler implements TopicHandler<Void> {

		SaveFileHandler() {
		}

		@Override
		public void topicReceived(Void payload) {
			CodeEditorTabPane.this.saveCurrentTab();
		}

	}

	private class SaveAsFileHandler implements TopicHandler<Void> {

		SaveAsFileHandler() {
		}

		@Override
		public void topicReceived(Void payload) {
			CodeEditorTabPane.this.saveCurrentTabAs();
		}

	}

	private class SaveAllFilesHandler implements TopicHandler<Void> {

		SaveAllFilesHandler() {
		}

		@Override
		public void topicReceived(Void payload) {
			int tabCount = CodeEditorTabPane.this.getTabCount();
			for (int i = 0; i < tabCount; i++) {
				CodeEditorTabPane.this.saveTab(i);
			}
		}

	}

	private class TabComponent extends JPanel {

		private static final long serialVersionUID = -7216117325855466973L;
		File tabFile;
		final JLabel label;
		boolean dirty = false;
		boolean flagged = false;

		public TabComponent(File file) {
			super(new BorderLayout());
			setPreferredSize(new Dimension(128, 20));
			setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
			setOpaque(false);

			this.tabFile = file;
			Icon icn = getTabCount() == 1 ? Icons.DOCUMENT : Icons.DOCUMENT;
			this.flagged = getTabCount() == 1;
			this.label = new JLabel(file == null ? "untitled" : file.getName(), icn, SwingConstants.LEADING);
			add(this.label, BorderLayout.CENTER);
			JButton closeButton = new JButton(Icons.TAB_CLOSE);
			closeButton.setFocusable(false);
			closeButton.setPreferredSize(new Dimension(15, 15));
			closeButton.setBorderPainted(false);
			closeButton.setFocusPainted(false);
			closeButton.setContentAreaFilled(false);
			closeButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (TabComponent.this.isDirty()) {
						String msg = "File " + label.getText() + " has unsaved changes. What would you like to do?";
						int response = JOptionPane.showOptionDialog(CodeEditorTabPane.this, msg, "Confirm", 0, JOptionPane.QUESTION_MESSAGE, null,
						        new String[] { "Leave Open", "Close Editor" }, "Leave Open");
						if (response != 1) {
							return;
						}
					}
					int i = CodeEditorTabPane.this.indexOfTabComponent(TabComponent.this);
					if (i != -1) {
						CodeEditorTabPane.this.remove(i);
					}
					// if (CodeEditorTabPane.this.getTabCount() == 0) {
					// CodeEditorTabPane.this.newFile();
					// }
				}
			});
			add(closeButton, BorderLayout.EAST);
			Events.SOURCE_CHANGED.subscribe(new TopicHandler<AssemblyEditor>() {

				@Override
				public void topicReceived(AssemblyEditor payload) {
					int tabIndex = indexOfTabComponent(TabComponent.this);
					if (tabIndex == -1) {
						return;
					}
					RTextScrollPane scrollPane = (RTextScrollPane) CodeEditorTabPane.this.getComponentAt(indexOfTabComponent(TabComponent.this));
					if (payload == scrollPane.getTextArea()) {
						Icon newIcon = TabComponent.this.flagged ? Icons.DOCUMENT_EDITED : Icons.DOCUMENT_EDITED;
						TabComponent.this.label.setIcon(newIcon);
						TabComponent.this.dirty = true;
					}
				}

			});
			Events.FILE_SAVED.subscribe(new TopicHandler<AssemblyEditor>() {

				@Override
				public void topicReceived(AssemblyEditor payload) {
					int tabIndex = indexOfTabComponent(TabComponent.this);
					if (tabIndex == -1) {
						return;
					}
					RTextScrollPane scrollPane = (RTextScrollPane) CodeEditorTabPane.this.getComponentAt(indexOfTabComponent(TabComponent.this));
					if (payload == scrollPane.getTextArea()) {
						TabComponent.this.label.setText(payload.getCurrentFile().getName());
						Icon newIcon = TabComponent.this.flagged ? Icons.DOCUMENT : Icons.DOCUMENT;
						TabComponent.this.label.setIcon(newIcon);
						TabComponent.this.dirty = false;
					}
				}

			});

			// Commands.SET_BUILD_TARGET.subscribe(this);
		}

		public String getTitle() {
			return label.getText();
		}

		public File getFile() {
			return this.tabFile;
		}

		public boolean isDirty() {
			return this.dirty;
		}
	}

	private class TabContextMenu extends JPopupMenu {

		private static final long serialVersionUID = 2284114101221595577L;
		TabComponent tabComponent;

		public TabContextMenu() {
			this.tabComponent = null;
			JMenuItem closeMenuItem = new JMenuItem("Close");
			add(closeMenuItem);
			JMenuItem closeOthersMenuItem = new JMenuItem("Close Others");
			add(closeOthersMenuItem);
			JMenuItem closeAllMenuItem = new JMenuItem("Close All");
			add(closeAllMenuItem);

			closeMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (tabComponent.isDirty()) {
						String msg = "Close " + tabComponent.label.getText() + " without saving?";
						int response = JOptionPane.showConfirmDialog(null, msg, "Confirm", JOptionPane.YES_NO_OPTION);
						if (response != JOptionPane.YES_OPTION) {
							return;
						}
					}
					int i = CodeEditorTabPane.this.indexOfTabComponent(tabComponent);
					if (i != -1) {
						CodeEditorTabPane.this.remove(i);
					}
				}
			});

			closeOthersMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					int tabCount = CodeEditorTabPane.this.getTabCount();
					for (int i = tabCount - 1; i >= 0; i--) {
						TabComponent tc = (TabComponent) CodeEditorTabPane.this.getTabComponentAt(i);
						if (tc != tabComponent) {
							if (tc.isDirty()) {
								String msg = "Close " + tabComponent.label.getText() + " without saving?";
								int response = JOptionPane.showConfirmDialog(null, msg, "Confirm", JOptionPane.YES_NO_OPTION);
								if (response != JOptionPane.YES_OPTION) {
									return;
								}
							}
							CodeEditorTabPane.this.remove(i);
						}
					}
				}
			});

			closeAllMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					int tabCount = CodeEditorTabPane.this.getTabCount();
					for (int i = tabCount - 1; i >= 0; i--) {
						TabComponent tc = (TabComponent) CodeEditorTabPane.this.getTabComponentAt(i);
						if (tc.isDirty()) {
							String msg = "Close " + tabComponent.label.getText() + " without saving?";
							int response = JOptionPane.showConfirmDialog(null, msg, "Confirm", JOptionPane.YES_NO_OPTION);
							if (response != JOptionPane.YES_OPTION) {
								return;
							}
						}
						CodeEditorTabPane.this.remove(i);
					}
				}
			});
		}

		@Override
		public void setVisible(boolean b) {
			tabComponent = (TabComponent) CodeEditorTabPane.this.getTabComponentAt(CodeEditorTabPane.this.getSelectedIndex());
			super.setVisible(b);
		}

	}

	public void changeProject(Project project) {
		this.project = project;

	}

}
