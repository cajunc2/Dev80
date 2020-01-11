package org.cajunc2.dev80.ui.worker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import org.cajunc2.dev80.eeprom.MiniPROProgrammer;
import org.cajunc2.dev80.ui.MainWindow;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.util.io.BufferedCopy;

public class ROMProgrammerWorker extends SwingWorker<Void, Void> implements ActionListener {
	private static final Logger logger = Logger.getLogger(ROMProgrammerWorker.class.getName());
	private final byte[] romContents;
	private final MainWindow mainWindow;
	private final Timer timer = new Timer(250, this);
	private final MiniPROProgrammer programmer;

	public ROMProgrammerWorker(byte[] romContents, MainWindow mainWindow) {
		this.romContents = romContents;
		this.mainWindow = mainWindow;
		this.programmer = new MiniPROProgrammer();
	}

	public ROMProgrammerWorker(File romFile, MainWindow mainWindow) throws Exception {
		InputStream is = new FileInputStream(romFile);
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			BufferedCopy copy = new BufferedCopy();
			copy.copy(is, os);
			this.romContents = os.toByteArray();
		} finally {
			is.close();
		}

		this.mainWindow = mainWindow;
		this.programmer = new MiniPROProgrammer();
	}

	@Override
	protected Void doInBackground() throws Exception {
		try {
			timer.start();
			programmer.write(romContents);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
			JOptionPane.showMessageDialog(mainWindow, "Failed to burn EEPROM:\n\n" + e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			Events.ROM_BURN_FINISHED.publish(Boolean.FALSE);
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (programmer.isBusy()) {
			return;
		}
		Events.ROM_BURN_FINISHED.publish(Boolean.valueOf(programmer.didSucceed()));
		timer.stop();
	}
}
