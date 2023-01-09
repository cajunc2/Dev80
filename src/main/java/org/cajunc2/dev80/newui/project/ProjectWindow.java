package org.cajunc2.dev80.newui.project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.cajunc2.dev80.newui.EditorWindow;
import org.cajunc2.dev80.newui.menu.MainMenuBar;
import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.worker.ProjectAssemblerWorker;
import org.cajunc2.util.io.BufferedCopy;

public class ProjectWindow extends JFrame {
      private static final long serialVersionUID = 1L;

      private final JTable fileTable;
      private final Project project;

      public ProjectWindow(Project project) {
            this.project = project;
            this.setLayout(new BorderLayout());
            this.setTitle(project.getTitle());

            this.add(new ProjectToolbar(this), BorderLayout.SOUTH);

            fileTable = new JTable(new ProjectFileTableModel(project));
            fileTable.setFont(fileTable.getFont().deriveFont(11f));
            fileTable.setRowHeight(19);
            fileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            fileTable.setTableHeader(new JTableHeader(fileTable.getColumnModel()) {
                  private static final long serialVersionUID = 1L;

                  @Override
                  public Dimension getPreferredSize() {
                        Dimension d = super.getPreferredSize();
                        d.height = 19;
                        return d;
                  }

            });
            fileTable.getTableHeader().setFont(fileTable.getTableHeader().getFont().deriveFont(11f));
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(fileTable.getModel());
            sorter.setSortsOnUpdates(true);
            sorter.toggleSortOrder(1);
            fileTable.setRowSorter(sorter);

            JScrollPane scrollPane = new JScrollPane(fileTable);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            this.add(scrollPane, BorderLayout.CENTER);

            new DropTarget(scrollPane, new DropTargetAdapter() {

                  @Override
                  public void drop(DropTargetDropEvent dtde) {
                        try {
                              dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                              @SuppressWarnings("unchecked")
                              List<File> droppedFiles = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                              project.addFiles(droppedFiles);
                              ((AbstractTableModel) fileTable.getModel()).fireTableDataChanged();
                              dtde.dropComplete(true);
                        } catch (RuntimeException e) {
                              dtde.rejectDrop();
                              throw e;
                        } catch (Exception e) {
                              dtde.rejectDrop();
                              e.printStackTrace();
                        }
                  }
            });

            this.addComponentListener(new ComponentAdapter() {
                  @Override
                  public void componentMoved(ComponentEvent e) {
                        project.updateWindowPosition("<PROJECT>", ProjectWindow.this);
                  }

                  @Override
                  public void componentResized(ComponentEvent e) {
                        project.updateWindowPosition("<PROJECT>", ProjectWindow.this);
                  }
            });

            fileTable.addMouseListener(new MouseAdapter() {
                  @Override
                  public void mouseClicked(MouseEvent evt) {
                        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() % 2 == 0) {
                              try {
                                    File f = (File) fileTable.getValueAt(fileTable.getSelectedRow(), 1);
                                    EditorWindow ew = EditorWindow.getEditorForFile(ProjectWindow.this, f);
                                    ew.setVisible(true);
                              } catch (IOException e) {
                                    e.printStackTrace();
                              }
                        }
                  }
            });

            fileTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            fileTable.getColumnModel().getColumn(0).setMaxWidth(32);
            fileTable.getColumnModel().getColumn(1).setCellRenderer(new ProjectFileTableCellRenderer(this.project));
            this.setSize(320, 480);
            this.setLocationByPlatform(true);
            project.positionWindow("<PROJECT>", this);
            this.setJMenuBar(new MainMenuBar(null, this));
            this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new WindowAdapter() {
                  @Override
                  public void windowClosing(WindowEvent e) {
                        dispose();
                  }
            });
      }


      public void addFiles() {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(project.getProjectDir());
            chooser.setMultiSelectionEnabled(true);
            if (chooser.showOpenDialog(this.getParent()) == JFileChooser.APPROVE_OPTION) {
                  try {
                        project.addFiles(Arrays.asList(chooser.getSelectedFiles()));
                        ((AbstractTableModel) fileTable.getModel()).fireTableDataChanged();
                  } catch (RuntimeException e) {
                        throw e;
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
            }
      }

      public void removeFile() {
            try {
                  project.removeFile(fileTable.getSelectedRow());
                  ((AbstractTableModel) fileTable.getModel()).fireTableDataChanged();
            } catch (RuntimeException e) {
                  throw e;
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public Project getProject() {
            return this.project;
      }

      public void setMainFile() throws Exception {
            File f = project.getFiles().get(fileTable.getSelectedRow());
            this.project.setCompileFile(f);
            ((AbstractTableModel) fileTable.getModel()).fireTableDataChanged();
      }

      public void selectFile(File fileToSelect) {
            List<File> projectFiles = project.getFiles();
            for (int i = 0; i < projectFiles.size(); i++) {
                  if (fileToSelect.equals(projectFiles.get(i))) {
                        fileTable.changeSelection(i, 1, false, false);
                  }
            }
      }

      public void buildProject() {
            File outputFile = project.getOutputFile();
            if (outputFile == null) {
                  JFileChooser chooser = new JFileChooser();
                  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                  chooser.setCurrentDirectory(project.getProjectDir());
                  if (JFileChooser.APPROVE_OPTION != chooser.showSaveDialog(ProjectWindow.this)) {
                        return;
                  }
                  outputFile = chooser.getSelectedFile();
                  project.setOutputFile(outputFile);
                  try {
                        project.save();
                  } catch (Exception e) {
                        e.printStackTrace();
                        // Continue on. This isn't fatal, just annoying
                  }
            }
            ProjectAssemblerWorker worker = new ProjectAssemblerWorker(project);
            try {
                  worker.execute();
                  byte[] builtRom = worker.get();
                  if (builtRom == null) {
                        return;
                  }
                        try (InputStream is = new ByteArrayInputStream(builtRom)) {
                              try (OutputStream os = new FileOutputStream(project.getOutputFile())) {
                                    new BufferedCopy().copy(is, os);
                              }
                        }
            } catch (RuntimeException e) {
                  throw e;
            } catch (Exception e) {
                  e.printStackTrace();
                  JOptionPane.showMessageDialog(this, "Failed to write binary file", "Error", JOptionPane.ERROR_MESSAGE);
            }
      }

      public static ProjectWindow getWindowForProject(Project p) {
            for (Window win : Window.getWindows()) {
                  if (win instanceof ProjectWindow) {
                        ProjectWindow projectWindow = (ProjectWindow) win;
                        if (projectWindow.project.equals(p) && projectWindow.isDisplayable()) {
                              return projectWindow;
                        }
                  }
            }
            return new ProjectWindow(p);
      }
}