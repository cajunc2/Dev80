package org.cajunc2.dev80.newui.project;

import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.nio.file.Path;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.Icons;

class ProjectFileTableCellRenderer extends DefaultTableCellRenderer{
	private static final long serialVersionUID = 1L;

	private final Project project;

	ProjectFileTableCellRenderer(Project project) {
		this.project = project;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
				column);
		if (value instanceof File) {
			File f = (File) value;
			c.setIcon(Icons.DOCUMENT);
			Path relPath = project.getProjectDir().toPath().relativize(f.toPath());
			if (relPath.toString().startsWith(".")) {
				c.setText(f.getName());
				c.setFont(c.getFont().deriveFont(Font.ITALIC));
			} else {
				c.setText(relPath.toString());
			}
			if (f.equals(project.getCompileFile())) {
				c.setFont(c.getFont().deriveFont(Font.BOLD));
			}
		}
		return c;
	}

}
