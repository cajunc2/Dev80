package org.cajunc2.dev80.ui.worker;

import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.cajunc2.dev80.simulator.memory.MemoryView;
import org.cajunc2.dev80.ui.topic.Events;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Source;
import nl.grauw.glass.SourceBuilder;

public class AssemblerWorker extends SwingWorker<Void, Void> {

	private final MemoryView memoryView;
	private final File workingFile;

	public AssemblerWorker(MemoryView memoryView, File workingFile) {
		this.memoryView = memoryView;
		this.workingFile = workingFile;
	}

	@Override
	protected Void doInBackground() throws Exception {
		try {
			List<File> includeDirs = new ArrayList<>();
			if (this.workingFile != null) {
				includeDirs.addAll(Arrays.asList(this.workingFile.getParentFile().listFiles()));
			}
			List<Path> includePaths = includeDirs.stream().map(f -> f.toPath()).collect(Collectors.toList());
			Source sourceBuilder = new SourceBuilder(includePaths).parse(workingFile.toPath());
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				sourceBuilder.assemble(baos);
				memoryView.writeROM(baos.toByteArray());
			}
		} catch (AssemblyException e) {
			e.printStackTrace();
			CompileError err = new CompileError(e.getPlainMessage(), e);
			Events.COMPILE_ERROR.publish(err);
		} catch (Exception e) {
			e.printStackTrace();
			JLabel label = new JLabel("<html><pre>" + e.getMessage() + "</pre>");
			label.setFont(new Font("Consolas", Font.PLAIN, 12));
			JOptionPane.showMessageDialog(null, label, "Compile Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

}
