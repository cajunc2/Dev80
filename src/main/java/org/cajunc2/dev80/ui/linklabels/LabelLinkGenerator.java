package org.cajunc2.dev80.ui.linklabels;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.event.HyperlinkEvent;

import org.cajunc2.dev80.project.ProjectLabelIndex;
import org.cajunc2.dev80.ui.topic.Commands;
import org.fife.ui.rsyntaxtextarea.LinkGenerator;
import org.fife.ui.rsyntaxtextarea.LinkGeneratorResult;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public final class LabelLinkGenerator implements LinkGenerator {
	private static final Logger logger = Logger.getLogger(LabelLinkGenerator.class.getName());
	static final Pattern WORD_PATTERN = Pattern.compile("\\w+");

	private final ProjectLabelIndex labelIndex;

	public LabelLinkGenerator(ProjectLabelIndex labelIndex) {
		super();
		this.labelIndex = labelIndex;
	}

	@Override
	public LinkGeneratorResult isLinkAtOffset(final RSyntaxTextArea textArea, final int mousePointerCharacter) {
		final String wordAtPosition = getWordAtPosition(textArea, mousePointerCharacter);
		if (!labelIndex.hasLabel(wordAtPosition)) {
			return null;
		}
		return new LinkGeneratorResult() {

			@Override
			public int getSourceOffset() {
				return mousePointerCharacter;
			}

			@Override
			public HyperlinkEvent execute() {
				Commands.GO_TO_LABEL.publish(labelIndex.find(wordAtPosition));
				return null;
			}

		};
	}

	public static String getWordAtPosition(final RSyntaxTextArea textArea, final int mousePointerCharacter) {
		try {
			int lineNumber = textArea.getLineOfOffset(mousePointerCharacter);
			int lineStart = textArea.getLineStartOffset(lineNumber);
			int lineEnd = textArea.getLineEndOffset(lineNumber);
			int offsetInLine = mousePointerCharacter - lineStart;
			String lineText = textArea.getText(lineStart, lineEnd - lineStart);

			final Matcher matcher = WORD_PATTERN.matcher(lineText);
			int start = 0;
			int end = 0;

			String currentWord = "";
			while (matcher.find()) {
				start = matcher.start();
				end = matcher.end();
				if (start <= offsetInLine && offsetInLine <= end) {
					currentWord = lineText.substring(start, end);
					break;
				}
			}

			return currentWord; // This is current word
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Something calculated wrong...", e);
			return null;
		}
	}
}