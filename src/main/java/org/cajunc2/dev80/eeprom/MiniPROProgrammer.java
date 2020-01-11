package org.cajunc2.dev80.eeprom;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MiniPROProgrammer implements ROMProgrammer {
	private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
	private static final Path TEMP_ROM_FILE = Paths.get(TEMP_DIR + "/dev80-tmp.rom");
	private static final File LOG_FILE = new File("MiniPROProgrammer.log");
	private static final String MINIPRO_PATH = "/Users/cajuncc/Desktop/z80/System/minipro";
	private Process process;

	@Override
	public void write(byte[] bytes) throws Exception {
		Files.write(TEMP_ROM_FILE, bytes);
		ProcessBuilder pb = new ProcessBuilder(MINIPRO_PATH, "-p", "28C256", "-w", TEMP_ROM_FILE.toString());
		pb.redirectError(LOG_FILE);
		pb.redirectOutput(LOG_FILE);
		process = pb.start();
	}

	public boolean isBusy() {
		return process.isAlive();
	}

	public boolean didSucceed() {
		return process.exitValue() == 0;
	}
}
