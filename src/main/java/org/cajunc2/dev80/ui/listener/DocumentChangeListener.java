package org.cajunc2.dev80.ui.listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/** An abstract listener that does nothing for insert/remove, but passes along changes */
public abstract class DocumentChangeListener implements DocumentListener {

	@Override
	public void insertUpdate(DocumentEvent e) {
		// do nothing
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// do nothing
	}

	@Override
	public abstract void changedUpdate(DocumentEvent evt);

}
