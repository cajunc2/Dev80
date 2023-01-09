package org.cajunc2.dev80.newui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;
import java.util.prefs.Preferences;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;

import org.cajunc2.dev80.ui.AssemblyEditor;
import org.cajunc2.dev80.ui.help.HelpPane;
import org.cajunc2.dev80.ui.help.InstructionHelp;
import org.cajunc2.dev80.ui.help.OpcodeTable;
import org.cajunc2.dev80.ui.help.instructions.InstructionDetail;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.util.topic.TopicHandler;

public class HelpDialog extends JDialog implements TopicHandler<AssemblyEditor> {
	private static final long serialVersionUID = 1L;
	private static final Preferences prefs = Preferences.userNodeForPackage(HelpPane.class);

	private static final HelpDialog SINGLETON_INSTANCE = new HelpDialog();

	private static final InstructionHelp instructions = new InstructionHelp();

	private final JLabel mnemonic = new JLabel();
	private final JLabel description = new JLabel();
	private final JScrollPane opcodeScrollPane;
	private final OpcodeTable opcodes = new OpcodeTable();

	public static HelpDialog getInstance() {
		return SINGLETON_INSTANCE;
	}

	public HelpDialog() {
		this.setTitle("Quick Help");
		this.setType(Type.UTILITY);
		this.setFocusableWindowState(false);

            opcodes.setTableHeader(new JTableHeader(opcodes.getColumnModel()) {
                  private static final long serialVersionUID = 1L;

                  @Override
                  public Dimension getPreferredSize() {
                        Dimension d = super.getPreferredSize();
                        d.height = 19;
                        return d;
                  }
			
            });
		opcodes.getTableHeader().setFont(opcodes.getTableHeader().getFont().deriveFont(11f));

		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		Container contentPane = this.getContentPane();

		Font f = new Font("Consolas", Font.BOLD, 24);
		mnemonic.setFont(f);
		mnemonic.setHorizontalAlignment(SwingConstants.LEFT);
		layout.putConstraint(SpringLayout.WEST, mnemonic, 12, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, mnemonic, 12, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, mnemonic, -12, SpringLayout.EAST, contentPane);
		add(mnemonic);

		description.setFont(description.getFont().deriveFont(11f));
		layout.putConstraint(SpringLayout.WEST, description, 12, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, description, 12, SpringLayout.SOUTH, mnemonic);
		layout.putConstraint(SpringLayout.EAST, description, -12, SpringLayout.EAST, contentPane);
		add(description);

		this.opcodeScrollPane = new JScrollPane(opcodes);
		this.opcodeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		layout.putConstraint(SpringLayout.WEST, opcodeScrollPane, 6, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, opcodeScrollPane, 6, SpringLayout.SOUTH, description);
		layout.putConstraint(SpringLayout.SOUTH, opcodeScrollPane, -6, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, opcodeScrollPane, -6, SpringLayout.EAST, contentPane);
		add(this.opcodeScrollPane);

		Events.CARET_MOVED.subscribe(this);

		setSize(new Dimension(320, 320));

		Rectangle frameBounds = new Rectangle();
		frameBounds.x = prefs.getInt("helpPaneX", 0);
		frameBounds.y = prefs.getInt("helpPaneY", 0);
		frameBounds.width = prefs.getInt("helpPaneWidth", 320);
		frameBounds.height = prefs.getInt("helpPaneHeight", 320);
		setBounds(frameBounds);

		boolean show = prefs.getBoolean("showHelpPane", true);
		setVisible(show);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				saveWindowPosition();
			}

			@Override
			public void componentResized(ComponentEvent e) {
				saveWindowPosition();
			}
		});
		
            this.addWindowListener(new WindowAdapter() {
                  @Override
                  public void windowClosing(WindowEvent e) {
				prefs.putBoolean("showHelpPane", false);
                  }
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
		String[] pieces = code.split("\\s+");
		display(instructions.get(pieces[0]));
	}

	public void toggle() {
		setVisible(!isVisible());
	}

	private void saveWindowPosition() {
		Rectangle b = getBounds();
		prefs.putInt("helpPaneX", b.x);
		prefs.putInt("helpPaneY", b.y);
		prefs.putInt("helpPaneWidth", b.width);
		prefs.putInt("helpPaneHeight", b.height);
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		prefs.putBoolean("showHelpPane", b);
	}
}
