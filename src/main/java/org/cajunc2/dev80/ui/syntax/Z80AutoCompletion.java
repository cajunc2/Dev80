package org.cajunc2.dev80.ui.syntax;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;

public class Z80AutoCompletion extends AutoCompletion {

	public Z80AutoCompletion() {
		super(new Z80CompletionProvider());
	}

	private static class Z80CompletionProvider extends DefaultCompletionProvider {

		public Z80CompletionProvider() {
			super();
		}
	}
}
