package org.cajunc2.dev80.ui;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NewProjectWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JTextField directoryField = new JTextField();
	private final JTextField titleField = new JTextField();

	public NewProjectWindow(Frame parentWindow) {
		super(parentWindow);
		final JButton browseButton = new JButton("Browse...");

		browseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(parentWindow) == JFileChooser.APPROVE_OPTION) {
					directoryField.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.gridx = 0;
		add(new JLabel("Project Location:"), c);
		c.gridx = 1;
		add(directoryField, c);
		c.gridx = 2;
		add(browseButton, c);
		c.gridy = 1;
		c.gridx = 0;
		add(new JLabel("Project Name:"), c);
		c.gridx = 1;
		c.gridwidth = 2;
		add(titleField, c);
		pack();
		setLocationRelativeTo(parentWindow);
	}

	public String getProjectDir() {
		return directoryField.getText();
	}

	public String getProjectTitle() {
		return titleField.getText();
	}
}
