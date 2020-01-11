package org.cajunc2.dev80.ui.worker;

import java.awt.Font;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

			FileReader reader = new FileReader(project.getCompileFile());
			try {
				Source source = new SourceBuilder(includeDirs).parse(reader, project.getCompileFile());
				source.register();
				source.expand();
				source.resolve();
				byte[] objectCode = source.generateObjectCode();
				Events.COMPILE_SUCCESS.publish(objectCode);
				return objectCode;
			} finally {
				reader.close();
			}
		} catch (AssemblyException e) {
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
