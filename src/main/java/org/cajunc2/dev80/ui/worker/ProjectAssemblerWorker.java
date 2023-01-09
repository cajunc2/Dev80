package org.cajunc2.dev80.ui.worker;

import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.topic.Events;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Source;
import nl.grauw.glass.SourceBuilder;

public class ProjectAssemblerWorker extends SwingWorker<byte[], Void> {
	private static final Logger logger = Logger.getLogger(ProjectAssemblerWorker.class.getName());
	private final Project project;

	public ProjectAssemblerWorker(Project project) {
		this.project = project;
	}

	@Override
	protected byte[] doInBackground() throws Exception {
		try {
			List<File> includeDirs = new ArrayList<File>();
			includeDirs.add(this.project.getProjectDir());

			List<Path> includePaths = includeDirs.stream().map(f -> f.toPath()).collect(Collectors.toList());
			Source source = new SourceBuilder(includePaths).parse(project.getCompileFile().toPath());
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				source.assemble(baos);
				byte[] objectCode = baos.toByteArray();
				Events.COMPILE_SUCCESS.publish(objectCode);
				return objectCode;
			}
		} catch (AssemblyException e) {
			logger.log(Level.INFO, "Compile Error", e);
			CompileError err = new CompileError(e.getPlainMessage(), e);
			Events.COMPILE_ERROR.publish(err);
			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unexpected compile error", e);
			JLabel label = new JLabel("<html><pre>" + e.getMessage() + "</pre>");
			label.setFont(new Font("Consolas", Font.PLAIN, 12));
			JOptionPane.showMessageDialog(null, label, "Compile Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

}
