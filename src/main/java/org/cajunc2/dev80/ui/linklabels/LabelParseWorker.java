package org.cajunc2.dev80.ui.linklabels;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiPredicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

import org.cajunc2.dev80.project.Project;

public class LabelParseWorker extends SwingWorker<Void, Void> {
	private static final Logger logger = Logger.getLogger(LabelParseWorker.class.getName());
	private static final Pattern LABEL_PATTERN = Pattern.compile("(^\\w*):.*");

	private final Project project;

	public LabelParseWorker(Project project) {
		this.project = project;
	}

	@Override
	protected Void doInBackground() throws Exception {
		project.getLabelIndex().clear();
		Path projectDir = project.getProjectDir().toPath();
		BiPredicate<Path, BasicFileAttributes> matcher = (t, u) -> {
			return u.isRegularFile() && (t.toString().toLowerCase().endsWith(".asm") || t.toString().toLowerCase().endsWith(".z80"));
		};

		Files.find(projectDir, 999, matcher, FileVisitOption.FOLLOW_LINKS).forEach((t) -> {
			int lineNumber = 0;
			File file = t.toFile();
			try {
				BufferedReader br = new BufferedReader(new FileReader(t.toFile()));
				try {
					String line = br.readLine();
					while (line != null) {
						Matcher m = LABEL_PATTERN.matcher(line);
						if (m.matches()) {
							project.getLabelIndex().add(m.group(1), file, lineNumber);
						}
						line = br.readLine();
						lineNumber++;
					}
				} finally {
					br.close();
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, "", e);
			}
		});
		return null;
	}
}
