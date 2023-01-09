package org.cajunc2.dev80.newui;

import static javax.swing.SpringLayout.EAST;
import static javax.swing.SpringLayout.HORIZONTAL_CENTER;
import static javax.swing.SpringLayout.NORTH;
import static javax.swing.SpringLayout.SOUTH;
import static javax.swing.SpringLayout.WEST;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import org.cajunc2.dev80.newui.menu.RecentProjectsMenu;
import org.cajunc2.dev80.newui.project.ProjectWindow;
import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.Icons;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.dev80.ui.util.WindowPositionUtil;

public class WelcomeWindow extends JFrame {
      private static final long serialVersionUID = 1L;

      public WelcomeWindow() {
            setTitle("Dev80");
            Container contentPane = this.getContentPane();
            SpringLayout layout = new SpringLayout();
            contentPane.setLayout(layout);

            AppIconImagePanel appIcon = new AppIconImagePanel();
            contentPane.add(appIcon);

            JLabel appTitle = new JLabel("Dev80");
            appTitle.setFont(appTitle.getFont().deriveFont(36f));
            contentPane.add(appTitle);

            JLabel versionLabel = new JLabel();
            try {
                  Properties p = new Properties();
                  p.load(WelcomeWindow.class.getResourceAsStream("/version-info.properties"));
                  versionLabel.setText("Version " + p.getProperty("system.version"));
            } catch (Exception e) {
                  e.printStackTrace();
            }
            versionLabel.setForeground(new Color(versionLabel.getForeground().getRGB() & 0x66ffffff, true));
            versionLabel.setFont(versionLabel.getFont().deriveFont(12f));

            contentPane.add(versionLabel);

            JButton newProjectButton = new JButton("Open Folder", Icons.ADD_LARGE);
            newProjectButton.setIconTextGap(16);
            newProjectButton.addActionListener((evt) -> {
                  JFileChooser chooser = new JFileChooser();
                  chooser.setFileHidingEnabled(true);
                  chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                  chooser.showOpenDialog(null);
                  File selectedFile = chooser.getSelectedFile();
                  Project project = Project.loadFromDirectory(selectedFile);
                  new ProjectWindow(project).setVisible(true);
                  Events.PROJECT_OPENED.publish(project);
                  WelcomeWindow.this.setVisible(false);
            });
            contentPane.add(newProjectButton);

            JList<Project> recentProjectsList = new JList<>();
            // recentProjectsList.setModel(new RecentProjectsListModel());
            Preferences prefs = Preferences.userNodeForPackage(RecentProjectsMenu.class);
            Project[] recentProjects = Arrays.stream(prefs.get("recentProjects", "").split(File.pathSeparator))
                        .map(filename -> new File(filename + "/.d80project"))
                        .filter(file -> file.exists())
                        .map(file -> {
                              try {
                                    return new Project(file);
                              } catch (Exception e) {
                                    e.printStackTrace();
                                    return null;
                              }
                        }).toArray(Project[]::new);
            recentProjectsList.setListData(recentProjects);

            recentProjectsList.setCellRenderer(new DefaultListCellRenderer() {
                  private static final long serialVersionUID = 1L;

                  @Override
                  public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                              boolean isSelected, boolean cellHasFocus) {
                        String projectTitle = ((Project) value).getTitle();
                        JLabel c = (JLabel) super.getListCellRendererComponent(list, projectTitle, index, isSelected,
                                    cellHasFocus);
                        c.setIcon(Icons.TREE_PROJECT);
                        return c;
                  }
            });
            recentProjectsList.addMouseListener(new MouseAdapter() {
                  @Override
                  public void mouseClicked(MouseEvent evt) {
                        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() % 2 == 0) {
                              Project p = recentProjectsList.getSelectedValue();
                              ProjectWindow pw = ProjectWindow.getWindowForProject(p);
                              pw.setVisible(true);
                              WelcomeWindow.this.setVisible(false);
                        }
                  }
            });

            JScrollPane recentProjectsScrollPane = new JScrollPane(recentProjectsList);
            recentProjectsScrollPane.setBorder(BorderFactory.createEmptyBorder());
            contentPane.add(recentProjectsScrollPane);

            layout.putConstraint(EAST, recentProjectsScrollPane, 0, EAST, contentPane);
            layout.putConstraint(NORTH, recentProjectsScrollPane, 0, NORTH, contentPane);
            layout.putConstraint(SOUTH, recentProjectsScrollPane, 0, SOUTH, contentPane);
            layout.putConstraint(WEST, recentProjectsScrollPane, -256, EAST, recentProjectsScrollPane);

            layout.putConstraint(WEST, newProjectButton, 48, WEST, contentPane);
            layout.putConstraint(EAST, newProjectButton, -48, WEST, recentProjectsScrollPane);
            layout.putConstraint(SOUTH, newProjectButton, -48, SOUTH, contentPane);
            layout.putConstraint(NORTH, newProjectButton, -48, SOUTH, newProjectButton);

            layout.putConstraint(NORTH, appIcon, 64, NORTH, contentPane);
            layout.putConstraint(HORIZONTAL_CENTER, appIcon, 0, HORIZONTAL_CENTER, newProjectButton);
            layout.putConstraint(NORTH, appTitle, 12, SOUTH, appIcon);
            layout.putConstraint(HORIZONTAL_CENTER, appTitle, 0, HORIZONTAL_CENTER, newProjectButton);
            layout.putConstraint(NORTH, versionLabel, 0, SOUTH, appTitle);
            layout.putConstraint(HORIZONTAL_CENTER, versionLabel, 0, HORIZONTAL_CENTER, newProjectButton);

            setSize(640, 480);
            setResizable(false);
            setLocation(WindowPositionUtil.idealPosition(this));
      }

      private static class AppIconImagePanel extends JComponent {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                  Graphics2D g2 = (Graphics2D) g;
                  g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                  g.drawImage(Icons.APP_ICON.getImage(), 0, 0, 160, 160, null);
            }

            @Override
            public Dimension getPreferredSize() {
                  return new Dimension(160, 160);
            }
      }

}