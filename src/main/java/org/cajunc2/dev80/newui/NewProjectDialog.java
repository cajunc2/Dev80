package org.cajunc2.dev80.newui;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import org.cajunc2.dev80.newui.project.ProjectWindow;
import org.cajunc2.dev80.project.Project;

public class NewProjectDialog extends JDialog {
      private static final long serialVersionUID = 1L;
      private static final int PADDING = 24;
      private static final int SPACING = 6;

      public NewProjectDialog() {
            this.setTitle("New Project");
            JPanel contentPane = new JPanel();
            contentPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24),
                        BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground"))));
            SpringLayout layout = new SpringLayout();
            contentPane.setLayout(layout);

            JLabel projectNameLabel = new JLabel("Project Name:");
            layout.putConstraint(SpringLayout.WEST, projectNameLabel, PADDING, SpringLayout.WEST, contentPane);
            layout.putConstraint(SpringLayout.EAST, projectNameLabel, -PADDING, SpringLayout.EAST, contentPane);
            layout.putConstraint(SpringLayout.NORTH, projectNameLabel, PADDING, SpringLayout.NORTH, contentPane);
            contentPane.add(projectNameLabel);

            JTextField projectNameField = new JTextField();
            layout.putConstraint(SpringLayout.EAST, projectNameField, -PADDING, SpringLayout.EAST, contentPane);
            layout.putConstraint(SpringLayout.WEST, projectNameField, PADDING, SpringLayout.WEST, contentPane);
            layout.putConstraint(SpringLayout.NORTH, projectNameField, SPACING, SpringLayout.SOUTH, projectNameLabel);
            contentPane.add(projectNameField);

            JLabel projectLocationLabel = new JLabel("Project Location:");
            layout.putConstraint(SpringLayout.WEST, projectLocationLabel, PADDING, SpringLayout.WEST, contentPane);
            layout.putConstraint(SpringLayout.EAST, projectLocationLabel, -PADDING, SpringLayout.EAST, contentPane);
            layout.putConstraint(SpringLayout.NORTH, projectLocationLabel, PADDING, SpringLayout.SOUTH,
                        projectNameField);
            contentPane.add(projectLocationLabel);

            JTextField projectLocationField = new JTextField();
            JButton projectLocationBrowseButton = new JButton("Browse...");
            layout.putConstraint(SpringLayout.EAST, projectLocationField, -SPACING, SpringLayout.WEST,
                        projectLocationBrowseButton);
            layout.putConstraint(SpringLayout.WEST, projectLocationField, PADDING, SpringLayout.WEST, contentPane);
            layout.putConstraint(SpringLayout.NORTH, projectLocationField, SPACING, SpringLayout.SOUTH,
                        projectLocationLabel);
            layout.putConstraint(SpringLayout.EAST, projectLocationBrowseButton, -PADDING, SpringLayout.EAST,
                        contentPane);
            layout.putConstraint(SpringLayout.NORTH, projectLocationBrowseButton, 0, SpringLayout.NORTH,
                        projectLocationField);
            layout.putConstraint(SpringLayout.SOUTH, projectLocationBrowseButton, 0, SpringLayout.SOUTH,
                        projectLocationField);
            contentPane.add(projectLocationField);
            contentPane.add(projectLocationBrowseButton);

            JCheckBox createSubdirectoryCheckbox = new JCheckBox("Create project subdirectory");
            createSubdirectoryCheckbox
                        .setToolTipText("Create a subdirectory with the project's name and put the project in there");
            createSubdirectoryCheckbox.setSelected(true);
            layout.putConstraint(SpringLayout.WEST, createSubdirectoryCheckbox, PADDING, SpringLayout.WEST,
                        contentPane);
            layout.putConstraint(SpringLayout.NORTH, createSubdirectoryCheckbox, SPACING, SpringLayout.SOUTH,
                        projectLocationField);
            contentPane.add(createSubdirectoryCheckbox);

            JButton okButton = new JButton("Create");
            JButton cancelButton = new JButton("Cancel");
            layout.putConstraint(SpringLayout.EAST, okButton, -PADDING, SpringLayout.EAST, contentPane);
            layout.putConstraint(SpringLayout.SOUTH, okButton, -PADDING, SpringLayout.SOUTH, contentPane);
            layout.putConstraint(SpringLayout.EAST, cancelButton, -SPACING, SpringLayout.WEST, okButton);
            layout.putConstraint(SpringLayout.SOUTH, cancelButton, -PADDING, SpringLayout.SOUTH, contentPane);
            contentPane.add(okButton);
            contentPane.add(cancelButton);

            projectLocationBrowseButton.addActionListener((evt) -> {
                  JFileChooser directoryChooser = new JFileChooser();
                  directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                  if (directoryChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                        projectLocationField.setText(directoryChooser.getSelectedFile().getAbsolutePath());
                  }
            });

            okButton.addActionListener((evt) -> {
                  try {
                        String projectLocation = projectLocationField.getText();
                        if (createSubdirectoryCheckbox.isSelected()) {
                              projectLocation = projectLocation + File.separator + projectNameField.getText();
                        }
                        File projectLocationFile = new File(projectLocation);
                        projectLocationFile.mkdir();
                        Project newProject = new Project(projectLocationFile, projectNameField.getText());
                        File projectMainFile = new File(projectLocation + File.separator + "main.asm");
                        projectMainFile.createNewFile();
                        newProject.addFiles(List.of(projectMainFile));
                        newProject.setCompileFile(projectMainFile);
                        ProjectWindow pw = new ProjectWindow(newProject);
                        pw.setVisible(true);
                        newProject.save();
                        this.dispose();
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
            });

            cancelButton.addActionListener((evt) -> {
                  this.dispose();
            });

            this.getRootPane().setDefaultButton(okButton);
            this.getRootPane().registerKeyboardAction((evt) -> {
                  this.dispose();
            }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

            this.add(contentPane);
            this.setSize(480, 320);
            this.setLocationRelativeTo(null);
      }
}