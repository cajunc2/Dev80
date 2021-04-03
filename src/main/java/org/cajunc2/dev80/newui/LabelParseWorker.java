package org.cajunc2.dev80.newui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.SortedMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

public class LabelParseWorker extends SwingWorker<Void, Void> {
	private static final Logger logger = Logger.getLogger(LabelParseWorker.class.getName());
	private static final Pattern LABEL_PATTERN = Pattern.compile("(^\\w*):.*");

	private final File file;
	private final SortedMap<Integer, String> labelLocations;

	public LabelParseWorker(File file, final SortedMap<Integer, String> labelLocations) {
		this.file = file;
		this.labelLocations = labelLocations;
	}

	@Override
	protected Void doInBackground() throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			int lineNumber = 1;
			String line = br.readLine();
			while (line != null) {
				Matcher m = LABEL_PATTERN.matcher(line);
				if (m.matches()) {
					labelLocations.put(lineNumber, m.group(1));
				}
				line = br.readLine();
				lineNumber++;
			}
		} finally {
			br.close();
		}
		return null;
	}
}
