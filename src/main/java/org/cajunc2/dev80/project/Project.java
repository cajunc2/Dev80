package org.cajunc2.dev80.project;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;

import com.google.gson.Gson;

public class Project {
	public static final String FILE_NAME = ".d80project";

	private final File projectFile;
	private final File projectDir;
	private ProjectData projectData;

	private transient final ProjectLabelIndex labelIndex = new ProjectLabelIndex();

	public Project(File projectDir, String title) throws Exception {
		this.projectDir = projectDir;
		this.projectFile = new File(projectDir.getAbsolutePath() + File.separator + FILE_NAME);
		this.projectData = new ProjectData();
		this.projectData.title = title;
	}

	public Project(File projectFile) throws Exception {
		this.projectFile = projectFile;
		this.projectDir = projectFile.getParentFile();
		String jsonString = new String(Files.readAllBytes(projectFile.toPath()));
		this.projectData = new Gson().fromJson(jsonString, ProjectData.class);
	}

	public void save() throws Exception {
		if (!this.projectFile.exists()) {
			this.projectFile.createNewFile();
		}
		String jsonData = new Gson().toJson(this.projectData, ProjectData.class);
		Files.write(this.projectFile.toPath(), jsonData.getBytes(), StandardOpenOption.TRUNCATE_EXISTING,
				StandardOpenOption.WRITE);
	}

	public String getTitle() {
		return this.projectData.title;
	}

	public void setTitle(String title) {
		this.projectData.title = title;
	}

	public File getCompileFile() {
		return this.projectData.compileFile;
	}

	public File getProjectDir() {
		return this.projectDir;
	}

	public File getOutputFile() {
		return this.projectData.outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.projectData.outputFile = outputFile;
	}

	public ProjectLabelIndex getLabelIndex() {
		return labelIndex;
	}

	public void positionWindow(String windowIdentifier, JFrame frame) {
		WindowParam wp = this.projectData.windowParams.get(windowIdentifier);
		if (wp == null) {
			return;
		}
		frame.setLocationByPlatform(false);
		frame.setLocation(wp.x, wp.y);
		frame.setSize(wp.width, wp.height);

		// check if the window is on screen... if not, move it so it's visible
	}

	public void updateWindowPosition(String windowIdentifier, JFrame frame) {
		WindowParam wp = new WindowParam();
		wp.x = frame.getLocation().x;
		wp.y = frame.getLocation().y;
		wp.width = frame.getWidth();
		wp.height = frame.getHeight();
		this.projectData.windowParams.put(windowIdentifier, wp);
		try {
			this.save();
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			// might not be the end of the world since we're just saving window placement
			e.printStackTrace();
		}
	}

	public List<File> getFiles() {
		return this.projectData.files;
	}

	public void addFiles(List<File> files) throws Exception {
		this.projectData.files.addAll(files);
		save();
	}

	public void removeFile(File file) throws Exception {
		this.projectData.files.remove(file);
		save();
	}

	public void removeFile(int index) throws Exception {
		this.projectData.files.remove(index);
		save();
	}

	@Override
	public int hashCode() {
		return Objects.hash(labelIndex, projectData, projectDir, projectFile);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Project other = (Project) obj;
		return Objects.equals(labelIndex, other.labelIndex) && Objects.equals(projectData, other.projectData)
				&& Objects.equals(projectDir, other.projectDir)
				&& Objects.equals(projectFile, other.projectFile);
	}

	public void setCompileFile(File compileFile) throws Exception {
		this.projectData.compileFile = compileFile;
		save();
	}

}
