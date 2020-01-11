package org.cajunc2.dev80.ui.worker;

import java.awt.Font;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.cajunc2.dev80.simulator.memory.MemoryView;
import org.cajunc2.dev80.ui.topic.Events;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Source;
import nl.grauw.glass.SourceBuilder;

public class AssemblerWorker extends SwingWorker<Void, Void> {

	private final String sourceCode;
	private final MemoryView memoryView;
	private final File workingFile;

	public AssemblerWorker(String sourceCode, MemoryView memoryView, File workingFile) {
		this.sourceCode = sourceCode;
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
			Source source = new SourceBuilder(includeDirs).parse(new StringReader(sourceCode), workingFile);
			source.register();
			source.expand();
			source.resolve();
			byte[] objectCode = source.generateObjectCode();
			memoryView.writeROM(objectCode);
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
