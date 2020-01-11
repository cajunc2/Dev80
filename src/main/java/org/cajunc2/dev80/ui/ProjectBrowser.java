package org.cajunc2.dev80.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.util.io.BufferedCopy;
import org.cajunc2.util.topic.TopicHandler;

public class ProjectBrowser extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProjectBrowser.class.getName());
	Project project;
	JTree tree;

	ProjectBrowser(Project project) {
		this.project = project;
		File fileRoot = project.getProjectDir();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(fileRoot);
		DefaultTreeModel treeModel = new DefaultTreeModel(root);

		tree = new JTree(treeModel);
		tree.setBorder(BorderFactory.createEmptyBorder());
		tree.setShowsRootHandles(true);
		tree.setCellRenderer(new ProjectTreeCellRenderer());
		tree.setToggleClickCount(0);

		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(256, 0));

		// this.add(new ProjectBrowserToolbar(this), BorderLayout.NORTH);

		MouseListener ml = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {

					TreePath selPath = tree.getClosestPathForLocation(e.getX(), e.getY());
					if (selPath == null) {
						return;
					}
					tree.setSelectionPath(selPath);
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
					File dclickedFile = (File) node.getUserObject();
					new ProjectNodePopupMenu(dclickedFile).show(e.getComponent(), e.getX(), e.getY());
					return;
				}

				if (e.getClickCount() == 2) {
					TreePath selPath = tree.getClosestPathForLocation(e.getX(), e.getY());
					if (selPath == null) {
						return;
					}
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
					File dclickedFile = (File) node.getUserObject();
					if (dclickedFile.isFile()) {
						Commands.OPEN_SPECIFIC_FILE.publish(dclickedFile);
						return;
					}
					if (dclickedFile.isDirectory()) {
						if (tree.getExpandedDescendants(selPath) == null) {
							tree.expandPath(selPath);
						} else {
							tree.collapsePath(selPath);
						}
					}
					return;
				}
				TreePath selPath = tree.getClosestPathForLocation(e.getX(), e.getY());
				if (selPath == null) {
					return;
				}
				tree.setSelectionPath(selPath);

			}
		};
		tree.addMouseListener(ml);

		if (project == Project.EMPTY) {
			tree.setVisible(false);
		} else {
			CreateChildNodes ccn = new CreateChildNodes(fileRoot, root);
			ccn.run();
			tree.expandRow(0);
		}

		Commands.EXPORT_ROM.subscribe(new ExportRomTopicHandler());
		Commands.REFRESH_PROJECT.subscribe(new RefreshHandler());
	}

	private class RefreshHandler implements TopicHandler<Void> {

		public RefreshHandler() {
		}

		@Override
		public void topicReceived(Void payload) {
			refresh();
		}
	}

	void refresh() {
		File fileRoot = project.getProjectDir();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(fileRoot);
		this.tree.setModel(new DefaultTreeModel(root));
		CreateChildNodes ccn = new CreateChildNodes(fileRoot, root);
		ccn.run();
		tree.expandRow(0);
	}

	public void changeProject(Project newProject) {
		if (this.project.equals(newProject)) {
			return;
		}
		this.project = newProject;
		File fileRoot = newProject.getProjectDir();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(fileRoot);
		this.tree.setModel(new DefaultTreeModel(root));
		CreateChildNodes ccn = new CreateChildNodes(fileRoot, root);
		ccn.run();
		tree.expandRow(0);
		tree.setVisible(true);
	}

	private class CreateChildNodes implements Runnable {

		private DefaultMutableTreeNode root;

		private File fileRoot;

		CreateChildNodes(File fileRoot, DefaultMutableTreeNode root) {
			this.fileRoot = fileRoot;
			this.root = root;
		}

		@Override
		public void run() {
			createChildren(fileRoot, root);
		}

		private void createChildren(File parent, DefaultMutableTreeNode node) {
			File[] files = parent.listFiles();
			if (files == null)
				return;
			Arrays.sort(files, new FileSorter());
			for (File file : files) {
				if (file.getName().startsWith(".") || (file.isFile() && !(file.getName().toUpperCase().endsWith(".ASM") || file.getName().toUpperCase().endsWith(".Z80")))) {
					continue;
				}
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(file);
				node.add(childNode);
				if (file.isDirectory()) {
					createChildren(file, childNode);
				}
			}
		}

	}

	private class FileSorter implements Comparator<File> {

		public FileSorter() {
		}

		@Override
		public int compare(File o1, File o2) {
			if (o1.isDirectory() && !o2.isDirectory()) {
				return -1;
			} else if (!o1.isDirectory() && o2.isDirectory()) {
				return 1;
			}
			return String.CASE_INSENSITIVE_ORDER.compare(o1.getName(), o2.getName());
		}
	}

	private class ProjectTreeCellRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;

		public ProjectTreeCellRenderer() {
		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

			ProjectTreeCellRenderer c = (ProjectTreeCellRenderer) super.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			File data = (File) node.getUserObject();
			c.setText(data.getName());
			c.setFont(c.getFont().deriveFont(Font.PLAIN));
			if (data.isDirectory()) {
				if (data.equals(project.getProjectDir())) {
					c.setIcon(Icons.TREE_PROJECT);
					c.setText(project.getTitle());
				} else {
					c.setClosedIcon(Icons.TREE_FOLDER);
					c.setOpenIcon(Icons.TREE_FOLDER_OPEN);
					c.setLeafIcon(Icons.TREE_FOLDER);
				}
			} else {
				c.setIcon(Icons.TREE_FILE);
				if (data.equals(project.getCompileFile())) {
					c.setFont(c.getFont().deriveFont(Font.BOLD));
				}
			}
			return c;
		}
	}

	private class ProjectNodePopupMenu extends JPopupMenu {
		private static final long serialVersionUID = 1L;

		ProjectNodePopupMenu(File selectedFile) {
			super();

			if (selectedFile.equals(project.getProjectDir())) {
				JMenuItem renameMenuItem = new JMenuItem();
				renameMenuItem.setAction(new AbstractAction("Rename '" + project.getTitle() + "'") {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						logger.finer("Rename!");
						String newName = JOptionPane.showInputDialog("Rename '" + project.getTitle() + "' to:", project.getTitle());
						if (newName == null) {
							return;
						}
						project.setTitle(newName);
						((DefaultTreeModel) ProjectBrowser.this.tree.getModel()).reload();
					}
				});
				add(renameMenuItem);
			}
			JMenuItem revealMenuItem = new RevealFileMenuItem(selectedFile);
			add(revealMenuItem);
		}
	}

	private class ExportRomTopicHandler implements TopicHandler<byte[]> {

		ExportRomTopicHandler() {
		}

		@Override
		public void topicReceived(byte[] payload) {
			try {
				InputStream is = new ByteArrayInputStream(payload);
				try {
					OutputStream os = new FileOutputStream(project.getOutputFile());
					try {
						new BufferedCopy().copy(is, os);
					} finally {
						os.close();
					}
				} finally {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(ProjectBrowser.this, "Failed to write binary file", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
