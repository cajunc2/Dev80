package org.cajunc2.dev80.eeprom;

public interface ROMProgrammer {
	void write(byte[] bytes) throws Exception;
}
