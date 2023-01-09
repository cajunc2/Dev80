package org.cajunc2.dev80.newui.project;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import org.cajunc2.dev80.ui.Icons;

class ProjectToolbar extends JToolBar {
      private static final long serialVersionUID = 1L;

      public ProjectToolbar(ProjectWindow projectWindow) {
            this.setFloatable(false);
            this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("Separator.foreground")));

            // JButton projectPropertiesButton = new JButton(Icons.PROJECT_PROPERTIES);
            // projectPropertiesButton.setToolTipText("Project properties");
            // this.add(projectPropertiesButton);

            // this.addSeparator();

            JButton addFileButton = new JButton(Icons.ADD_FILE);
            addFileButton.setToolTipText("Add files");
            addFileButton.addActionListener((evt) -> {
                  projectWindow.addFiles();
            });
            this.add(addFileButton);

            JButton removeFileButton = new JButton(Icons.REMOVE_FILE);
            removeFileButton.setToolTipText("Remove file");
            removeFileButton.addActionListener((evt) -> {
                  projectWindow.removeFile();
            });
            this.add(removeFileButton);

            this.addSeparator();

            JButton buildProjectButton = new JButton(Icons.BUILD_PROJECT);
            buildProjectButton.setToolTipText("Build project");
            buildProjectButton.addActionListener((evt) -> {
                  projectWindow.buildProject();
            });
            this.add(buildProjectButton);

            JButton burnRomButton = new JButton(Icons.BURN_ROM);
            burnRomButton.setToolTipText("Write to ROM");
            this.add(burnRomButton);
      }
}