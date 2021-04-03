package org.cajunc2.dev80.ui.help;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.cajunc2.dev80.ui.AssemblyEditor;
import org.cajunc2.dev80.ui.help.instructions.InstructionDetail;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.dev80.ui.util.BorderUtil;
import org.cajunc2.util.topic.TopicHandler;

public class HelpPane extends JPanel implements TopicHandler<AssemblyEditor> {

	private static final long serialVersionUID = 1L;
	private static final Preferences prefs = Preferences.userNodeForPackage(HelpPane.class);

	private static final InstructionHelp instructions = new InstructionHelp();

	private final JLabel mnemonic = new JLabel();
	private final JLabel description = new JLabel();
	private final JScrollPane opcodeScrollPane;
	private final OpcodeTable opcodes = new OpcodeTable();

	public HelpPane() {
		boolean show = prefs.getBoolean("showHelpPane", true);
		setVisible(show);

		Border leftLine = BorderFactory.createMatteBorder(0, 1, 0, 0, UIManager.getColor("TabbedPane.shadow"));
		setBorder(BorderUtil.createCompoundBorder(leftLine, BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		setPreferredSize(new Dimension(320, 0));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		Font f = new Font("Consolas", Font.BOLD, 48);
		mnemonic.setFont(f);
		mnemonic.setHorizontalAlignment(SwingConstants.CENTER);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(16, 0, 4, 0);
		c.weightx = 0;
		c.gridy = 0;
		c.gridx = 0;
		add(mnemonic, c);

		c.gridy = 1;
		c.gridx = 0;
		c.weighty = 0;
		c.weightx = 1;
		c.insets = new Insets(8, 0, 0, 0);
		add(description, c);

		c.gridy = 2;
		c.gridx = 0;
		c.weighty = 1;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(8, 0, 0, 0);
		c.gridheight = GridBagConstraints.REMAINDER;
		this.opcodeScrollPane = new JScrollPane(opcodes);
		this.opcodeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(this.opcodeScrollPane, c);

		Events.CARET_MOVED.subscribe(this);
		Commands.TOGGLE_HELP_PANEL.subscribe((payload) -> {
			SwingUtilities.invokeLater(() -> {
				toggle();
			});
		});
	}

	public void display(Optional<InstructionDetail> i) {
		if (!i.isPresent()) {
			this.opcodes.changeInstruction(null);
			this.opcodeScrollPane.setVisible(false);
			this.description.setText("No instruction selected");
			this.description.setHorizontalAlignment(SwingConstants.CENTER);
			this.mnemonic.setText("");
			return;
		}
		InstructionDetail id = i.get();
		this.opcodeScrollPane.setVisible(id.variants() != null && !id.variants().isEmpty());
		this.mnemonic.setText(id.instructionName());
		this.description.setText("<html>" + id.description());
		this.description.setHorizontalAlignment(SwingConstants.LEADING);
		this.opcodes.changeInstruction(id);
	}

	@Override
	public void topicReceived(AssemblyEditor payload) {
		String code = payload.getCurrentLineCode();
		String[] pieces = code.split(" ");
		display(instructions.get(pieces[0]));
	}

	public void toggle() {
		setVisible(!isVisible());
		prefs.putBoolean("showHelpPane", isVisible());
	}
}
