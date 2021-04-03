package org.cajunc2.dev80.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.MenuBar;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.thizzer.jtouchbar.JTouchBar;

import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.simulator.ui.SimulatorWindow;
import org.cajunc2.dev80.ui.help.HelpPane;
import org.cajunc2.dev80.ui.linklabels.LabelParseWorker;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.touchbar.MainWindowTouchbar;
import org.cajunc2.dev80.ui.worker.ProjectAssemblerWorker;
import org.cajunc2.dev80.ui.worker.ROMProgrammerWorker;
import org.cajunc2.util.topic.TopicHandler;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 8310004670833073605L;

	final FileDialog fd = new FileDialog(this);
	CodeEditorTabPane codeEditorTabs = new CodeEditorTabPane(null);
	SimulatorWindow simulatorWindow = new SimulatorWindow(this);
	ProjectBrowser projectBrowser;
	JTouchBar touchBar = new MainWindowTouchbar();

	public MainWindow(MenuBar menuBar) {
		// FullScreenUtilities.setWindowCanFullScreen(this, true);
		this.setTitle("Dev80");
		// this.setMenuBar(menuBar);
		this.setIconImage(Icons.APP_ICON.getImage());

		JPanel contentPane = new JPanel();
		// contentPane.setBorder(BorderFactory.createEmptyBorder());
		this.setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout());
		// contentPane.add(new MainToolbar(), BorderLayout.NORTH);
		JSplitPane projectBrowserSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		// projectBrowserSplit.setBorder(BorderFactory.createEmptyBorder());
		// projectBrowserSplit.setDividerSize(8);

		projectBrowser = new ProjectBrowser(null);
		projectBrowserSplit.add(projectBrowser, JSplitPane.LEFT);
		projectBrowserSplit.add(new CodeEditorPanel(codeEditorTabs), JSplitPane.RIGHT);
		contentPane.add(projectBrowserSplit, BorderLayout.CENTER);

		contentPane.add(new HelpPane(), BorderLayout.EAST);

		contentPane.setPreferredSize(new Dimension(1280, 768));
		this.pack();

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) * (381.0 / 1000));
		this.setLocation(x, y);

		setupWindowHotkeys();

		Commands.BURN_ROM.subscribe(new BurnRomTopicHandler());
		Commands.BUILD_PROJECT.subscribe(new BuildProjectTopicHandler());
		Commands.OPEN_PROJECT.subscribe(new OpenProjectTopicHandler(this));
		Commands.NEW_PROJECT.subscribe(new NewProjectTopicHandler(this));
		Commands.OPEN_SPECIFIC_PROJECT.subscribe(new OpenSpecificProjectTopicHandler(this));
		Commands.SAVE_FILE.subscribe((v) -> {
			LabelParseWorker worker = new LabelParseWorker(projectBrowser.project);
			SwingUtilities.invokeLater(worker);
		});
		touchBar.show(this);
	}

	FileDialog getFileDialog() {
		return fd;
	}

	private class BuildProjectTopicHandler implements TopicHandler<Void> {

		BuildProjectTopicHandler() {
		}

		@Override
		public void topicReceived(Void payload) {
			ProjectAssemblerWorker worker = new ProjectAssemblerWorker(projectBrowser.project);
			try {
				worker.execute();
				byte[] builtRom = worker.get();
				if (builtRom != null) {
					Commands.EXPORT_ROM.publish(builtRom);
				}
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private class BurnRomTopicHandler implements TopicHandler<Void> {

		BurnRomTopicHandler() {
		}

		@Override
		public void topicReceived(Void payload) {
			File romFile = projectBrowser.project.getOutputFile();
			try {
				ROMProgrammerWorker w = new ROMProgrammerWorker(romFile, MainWindow.this);
				SwingUtilities.invokeLater(w);
			} catch (Exception e) {
				System.err.println("Error programming EEPROM");
				System.err.println(e.getMessage());
			}

		}
	}

	private void setupWindowHotkeys() {
		Action saveHotkeyAction = new AbstractAction("SaveHotkeyAction") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Commands.SAVE_FILE.publish();
			}
		};

		KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_DOWN_MASK);
		getRootPane().getActionMap().put("SaveHotkeyAction", saveHotkeyAction);
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "SaveHotkeyAction");
	}

	public void changeProject(Project project) {
		projectBrowser.changeProject(project);
		codeEditorTabs.changeProject(project);

	}

}
