package org.cajunc2.dev80.newui.project;

import java.io.File;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.cajunc2.dev80.project.Project;

class ProjectFileTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] COLUMN_NAMES = new String[] { "", "File" };
	private final Project project;
	private final List<File> projectFiles;

	public ProjectFileTableModel(Project project) {
		this.project = project;
		this.projectFiles = project.getFiles();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		if (this.projectFiles == null) {
			return 0;
		}
		return this.projectFiles.size();
	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
			case 0:
				return projectFiles.get(row).equals(project.getCompileFile()) ? "*" : "";
			case 1:
				return projectFiles.get(row);
		}
		return "";
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
