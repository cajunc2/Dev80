package org.cajunc2.dev80.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.cajunc2.util.json.JSONUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Project {
	public static final Project EMPTY = new Project();
	public static final String FILE_NAME = ".d80project";

	private final File projectDir;
	private final List<File> openFiles = new ArrayList<File>();
	private File compileFile;
	private File outputFile;
	private String title;

	private final ProjectLabelIndex labelIndex = new ProjectLabelIndex();

	public static Project createNew(File projectDir, String title) throws Exception {
		File compileFile = new File(projectDir, title + ".asm");
		File outputFile = new File(projectDir, title + ".rom");
		return new Project(projectDir, compileFile, outputFile, title);
	}

	public static Project load(File projectFile) throws Exception {
		File projectDir = projectFile.getParentFile();
		JSONObject object = JSONUtil.parse(projectFile);
		String title = (String) object.get("title");
		File compileFile = new File(projectFile.getParent() + File.separator + object.get("compileFile").toString());
		File outputFile = new File(projectFile.getParent() + File.separator + object.get("outputFile").toString());
		Project result = new Project(projectDir, compileFile, outputFile, title);
		JSONArray openFiles = (JSONArray) object.get("openFiles");
		for (Object o : openFiles) {
			File openFile = new File(projectFile.getParent() + File.separator + o.toString());
			result.openFiles.add(openFile);
		}
		return result;
	}

	private Project() {
		this.projectDir = new File("/dev/null");
		this.compileFile = new File("/dev/null");
		this.outputFile = new File("/dev/null");
		this.title = "Untitled";
	}

	private Project(File projectDir, File compileFile, File outputFile, String title) {
		this.projectDir = projectDir;
		this.compileFile = compileFile;
		this.outputFile = outputFile;
		this.title = title;
	}

	public void save(File f) {

	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public File getCompileFile() {
		return this.compileFile;
	}

	public File getProjectDir() {
		return this.projectDir;
	}

	public List<File> getOpenFiles() {
		return openFiles;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	public ProjectLabelIndex getLabelIndex() {
		return labelIndex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compileFile == null) ? 0 : compileFile.hashCode());
		result = prime * result + ((openFiles == null) ? 0 : openFiles.hashCode());
		result = prime * result + ((outputFile == null) ? 0 : outputFile.hashCode());
		result = prime * result + ((projectDir == null) ? 0 : projectDir.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (compileFile == null) {
			if (other.compileFile != null)
				return false;
		} else if (!compileFile.equals(other.compileFile))
			return false;
		if (openFiles == null) {
			if (other.openFiles != null)
				return false;
		} else if (!openFiles.equals(other.openFiles))
			return false;
		if (outputFile == null) {
			if (other.outputFile != null)
				return false;
		} else if (!outputFile.equals(other.outputFile))
			return false;
		if (projectDir == null) {
			if (other.projectDir != null)
				return false;
		} else if (!projectDir.equals(other.projectDir))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
