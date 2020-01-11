package org.cajunc2.dev80.simulator.io;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.util.topic.TopicHandler;

public class ConsoleIOView extends JTextArea implements IOView {

	private static final long serialVersionUID = -5322584024761040318L;
	private final StringBuilder memoryContents = new StringBuilder();

	public ConsoleIOView() {
		setFont(new Font("Consolas", Font.PLAIN, 12));
		setLineWrap(true);
		setWrapStyleWord(false);
		setBackground(Color.BLACK);
		setForeground(Color.GREEN);
		setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
		Events.SYSTEM_RESET.subscribe(new TopicHandler<Void>() {

			@Override
			public void topicReceived(Void v) {
				clear();
			}
		});
	}

	@Override
	public int IORead(int address) {
		if (memoryContents.length() <= address) {
			memoryContents.setLength(address + 1);
		}
		return memoryContents.charAt(address);
	}

	@Override
	public void IOWrite(int address, int data) {
		memoryContents.append((char) data);
		this.setText(memoryContents.toString());
	}

	@Override
    public void clear() {
		memoryContents.setLength(0);
		this.setText("");
	}

	@Override
    public Component getUIComponent() {
	    return this;
    }

}
