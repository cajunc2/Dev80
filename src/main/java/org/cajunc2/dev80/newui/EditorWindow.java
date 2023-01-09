package org.cajunc2.dev80.newui;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.MatteBorder;

import org.cajunc2.dev80.newui.menu.MainMenuBar;
import org.cajunc2.dev80.newui.project.ProjectWindow;
import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.AssemblyEditor;
import org.fife.ui.rtextarea.RTextScrollPane;

public class EditorWindow extends JFrame {
      private static final long serialVersionUID = 1L;
      private final AssemblyEditor editor;
      private final Project project;
      private final File file;
      private final SortedMap<Integer, String> labelLocations = new TreeMap<>();

      private final JButton lineLabel;
      private final JButton sectionLabel;

      public EditorWindow(ProjectWindow projectWindow, File file) throws IOException {
            this.project = projectWindow.getProject();
            this.file = file;
            this.setTitle(file.getName());
            this.setLayout(new BorderLayout());
            this.editor = new AssemblyEditor(project, file);
            RTextScrollPane scrollPane = new RTextScrollPane(editor);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.setLineNumbersEnabled(false);
            this.add(scrollPane, BorderLayout.CENTER);

            JToolBar statusBar = new JToolBar();

            statusBar.setBorder(new MatteBorder(1, 0, 0, 0, UIManager.getColor("Separator.foreground")));

            lineLabel = new JButton();
            lineLabel.setFont(lineLabel.getFont().deriveFont(11f));
            lineLabel.addActionListener((evt) -> {
                  try {
                        int caretpos = editor.getCaretPosition();
                        int linenum = editor.getLineOfOffset(caretpos) + 1;
                        Object input = JOptionPane.showInputDialog(EditorWindow.this, "Go to line:", "Go to line",
                                    JOptionPane.PLAIN_MESSAGE, null, null, linenum);
                        if (input == null) {
                              return;
                        }
                        int inputLineNumber = Integer.parseInt(input.toString());
                        if (inputLineNumber < 1) {
                              inputLineNumber = 1;
                        }
                        if (inputLineNumber > editor.getLineCount()) {
                              inputLineNumber = editor.getLineCount();
                        }
                        int offs = editor.getLineStartOffset(inputLineNumber - 1);
                        editor.setCaretPosition(offs);
                        editor.getLineCount();
                  } catch (RuntimeException e) {
                        throw e;
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
            });
            statusBar.add(lineLabel);

            statusBar.addSeparator();

            sectionLabel = new JButton();
            sectionLabel.setFont(sectionLabel.getFont().deriveFont(11f));
            sectionLabel.addActionListener((evt) -> {
                  JPopupMenu sectionMenu = new JPopupMenu();

                  for (Entry<Integer, String> label : labelLocations.entrySet()) {
                        JMenuItem sectionItem = new JMenuItem(label.getValue());
                        sectionItem.setFont(sectionMenu.getFont().deriveFont(11f));
                        sectionItem.addActionListener((itemEvt) -> {
                              try {
                                    int offs = editor.getLineStartOffset(label.getKey() - 1);
                                    editor.setCaretPosition(offs);
                              } catch (RuntimeException e) {
                                    throw e;
                              } catch (Exception e) {
                                    e.printStackTrace();
                              }
                        });
                        sectionMenu.add(sectionItem);
                  }
                  sectionMenu.setSize(sectionLabel.getWidth(), 300);
                  sectionMenu.show(sectionLabel, 0, 0);
            });
            statusBar.add(sectionLabel);

            statusBar.addSeparator();

            this.add(statusBar, BorderLayout.SOUTH);
            this.pack();
            this.setSize(640, 768);

            this.editor.addCaretListener((evt) -> {
                  this.updateCaretLocation();
            });
            this.setLocationByPlatform(true);

            this.setJMenuBar(new MainMenuBar(this, projectWindow));

            this.addComponentListener(new ComponentAdapter() {
                  @Override
                  public void componentMoved(ComponentEvent e) {
                        project.updateWindowPosition(file.getName(), EditorWindow.this);
                  }

                  @Override
                  public void componentResized(ComponentEvent e) {
                        project.updateWindowPosition(file.getName(), EditorWindow.this);
                  }
            });
            project.positionWindow(file.getName(), this);
            this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            this.addWindowFocusListener(new WindowAdapter() {
                  @Override
                  public void windowGainedFocus(WindowEvent e) {
                        projectWindow.selectFile(file);
                  }
            });
            this.addWindowListener(new WindowAdapter() {

                  @Override
                  public void windowClosing(WindowEvent evt) {
                        if (editor.isDirty()) {
                              int confirmResponse = JOptionPane.showConfirmDialog(EditorWindow.this,
                                          "Save file before closing?", "Close Editor",
                                          JOptionPane.YES_NO_CANCEL_OPTION);
                              switch (confirmResponse) {
                                    case JOptionPane.CANCEL_OPTION:
                                          return;
                                    case JOptionPane.NO_OPTION:
                                          try {
                                                editor.reload();
                                          } catch (IOException e) {
                                                e.printStackTrace();
                                          }
                                          dispose();
                                          return;
                                    case JOptionPane.YES_OPTION:
                                          try {
                                                editor.save();
                                          } catch (IOException e) {
                                                e.printStackTrace();
                                          }
                                          dispose();
                                          return;
                              }
                        }
                        dispose();
                  }
            });

            SwingWorker<Void, Void> worker = new LabelParseWorker(file, labelLocations);
            worker.execute();
            this.updateCaretLocation();
      }

      public EditorWindow(File file) throws IOException {
            this(null, file);
      }

      private void updateCaretLocation() {
            int linenum = editor.getCaretLineNumber() + 1;
            lineLabel.setText("Line " + linenum);
            String foundLabel = "<no label>";
            for (Entry<Integer, String> label : labelLocations.entrySet()) {
                  if (label.getKey() <= linenum) {
                        foundLabel = label.getValue();
                  }
            }
            sectionLabel.setText(foundLabel);
            if (editor.isDirty()) {
                  this.setTitle(this.file.getName() + " *");
            } else {
                  this.setTitle(this.file.getName());
            }

      }

      public static EditorWindow getEditorForFile(ProjectWindow projectWindow, File file) throws IOException {
            for (Window win : Window.getWindows()) {
                  if (win instanceof EditorWindow) {
                        EditorWindow ew = (EditorWindow) win;
                        if (ew.file.equals(file) && ew.project.equals(projectWindow.getProject())
                                    && ew.isDisplayable()) {
                              return ew;
                        }
                  }
            }
            return new EditorWindow(projectWindow, file);
      }

      public void save() {
            try {
                  this.editor.save();
                  this.setTitle(this.file.getName());
            } catch (IOException e) {
                  e.printStackTrace();
                  JOptionPane.showMessageDialog(this, "Save Error", "Error", JOptionPane.ERROR_MESSAGE);
            }

      }

      public void saveAs() {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            if (JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(this)) {
                  try {
                        this.editor.saveAs(new LocalFileLocation(chooser.getSelectedFile()));
                        this.setTitle(this.file.getName());
                  } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Save Error", "Error", JOptionPane.ERROR_MESSAGE);
                  }
            }
      }
}