package org.cajunc2.dev80.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.dev80.ui.util.RoundedBorder;
import org.cajunc2.dev80.ui.worker.CompileError;

public class CodeEditorPanel extends JPanel {

	private static final long serialVersionUID = -6375679194759996098L;
	private static final Border successOutline = new RoundedBorder(Color.GREEN.darker(), 12);
	private static final Border errorOutline = new RoundedBorder(Color.RED.darker(), 12);
	private static final Border messagePadding = BorderFactory.createEmptyBorder(4, 4, 4, 4);
	static final Border successBorder = BorderFactory.createCompoundBorder(messagePadding, successOutline);
	static final Border errorBorder = BorderFactory.createCompoundBorder(messagePadding, errorOutline);

	MouseListener errorBarMouseListener;
	JLabel compileMessageLabel = new JLabel();
	final CodeEditorTabPane tabPane;

	public CodeEditorPanel(final CodeEditorTabPane tabs) {
		this.tabPane = tabs;
		this.setLayout(new BorderLayout());
		this.add(tabs, BorderLayout.CENTER);
		this.add(compileMessageLabel, BorderLayout.SOUTH);
		this.compileMessageLabel.setForeground(Color.RED.darker());
		this.compileMessageLabel.setFont(this.compileMessageLabel.getFont());
		this.compileMessageLabel.setOpaque(true);
		this.compileMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.compileMessageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		Events.COMPILE_ERROR.subscribe(this::showCompileError);
		Events.COMPILE_SUCCESS.subscribe(this::showCompileSuccess);
		Commands.BUILD_PROJECT.subscribe(this::clearCompilerMessage);
	}

	private void clearCompilerMessage(@SuppressWarnings("unused") Void v) {
		compileMessageLabel.setBorder(BorderFactory.createEmptyBorder());
	}

	private void showCompileError(CompileError payload) {
		StringBuilder errMessage = new StringBuilder(1024);
		errMessage.append("Assembler Error: ");
		errMessage.append(payload.getErrorDescription());
		errMessage.append(" (");
		errMessage.append(payload.getSourceFile().getName());
		errMessage.append(" : ");
		errMessage.append(payload.getLineNumber() + 1);
		errMessage.append(")");
		compileMessageLabel.setForeground(Color.RED.darker());
		compileMessageLabel.setBorder(errorBorder);
		compileMessageLabel.setText(errMessage.toString());
		compileMessageLabel.removeMouseListener(errorBarMouseListener);
		errorBarMouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tabPane.displayError(payload);
			}
		};
		compileMessageLabel.addMouseListener(errorBarMouseListener);
	}

	private void showCompileSuccess(byte[] payload) {
		final String msg = "Build Successful - ROM size %d bytes";
		compileMessageLabel.setForeground(Color.GREEN.darker());
		compileMessageLabel.setBorder(successBorder);
		compileMessageLabel.setText(String.format(msg, Integer.valueOf(payload.length)));
		compileMessageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CodeEditorPanel.this.compileMessageLabel.setBorder(BorderFactory.createEmptyBorder());
				CodeEditorPanel.this.compileMessageLabel.setText(null);
			}
		});
		compileMessageLabel.removeMouseListener(errorBarMouseListener);
	}

}
