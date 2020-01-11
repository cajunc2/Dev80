package org.cajunc2.dev80.simulator.memory;

import java.awt.Component;

import javax.swing.BorderFactory;

import org.cajunc2.dev80.ui.hex.HexEditor;

public class BasicMemoryView extends HexEditor implements MemoryView {

	private static final long serialVersionUID = -5322584024761040318L;

	public BasicMemoryView() {
		super(new byte[0]);
		setBorder(BorderFactory.createEmptyBorder());
	}

	@Override
	public int readByte(int address) {
		byte[] contents = getByteContent();
		if (address >= contents.length) {
			return 0;
		}
		return contents[address] & 0xFF;
	}

	@Override
	public int readWord(int address) {
		return readByte(address) + readByte(address + 1) * 256;
	}

	@Override
	public void writeByte(int address, int data) {
		byte[] contents = getByteContent();
		if (address >= contents.length) {
			return;
		}
		contents[address] = (byte) data;
		repaint();
	}

	@Override
	public void writeWord(int address, int data) {
		writeByte(address, (data & 0xFF));
		writeByte(address + 1, (data >> 8) & 0xFF);
	}

	@Override
	public void writeROM(byte[] bytes) {
		this.setByteContent(bytes);
	}

	@Override
	public byte[] readROM() {
		return this.getByteContent();
	}

	@Override
	public Component getUIComponent() {
		return this;
	}

	@Override
	public void resetRAM() {
		// do nothing
	}

}
