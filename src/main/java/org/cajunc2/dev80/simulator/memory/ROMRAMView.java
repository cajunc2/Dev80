package org.cajunc2.dev80.simulator.memory;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

import org.cajunc2.dev80.ui.hex.HexEditor;

public class ROMRAMView extends JSplitPane implements MemoryView {

	private static final long serialVersionUID = -5322584024761040318L;
	private final int size;
	private final HexEditor romView;
	private final HexEditor ramView;

	public ROMRAMView() {
		this(32768);
	}

	public ROMRAMView(int size) {
		super(JSplitPane.HORIZONTAL_SPLIT);
		this.setResizeWeight(0.5);
		this.size = size;
		this.romView = new HexEditor(new byte[size]);
		this.ramView = new HexEditor(new byte[size], size);
		this.add(romView, 0);
		this.add(ramView, 1);
		setBorder(BorderFactory.createEmptyBorder());
	}

	@Override
	public int readByte(int address) {
		HexEditor source = (address < size) ? romView : ramView;
		int realAddress = (address < size) ? address : (address - size);
		byte[] contents = source.getByteContent();
		if (realAddress >= contents.length) {
			return 0;
		}
		return contents[realAddress] & 0xFF;
	}

	@Override
	public int readWord(int address) {
		return readByte(address) + readByte(address + 1) * 256;
	}

	@Override
	public void writeByte(int address, int data) {
		if (address < size) {
			System.err.println("Attempt to write to ROM at address $" + Integer.toHexString(address));
			return;
		}
		int realAddress = address - size;
		byte[] contents = ramView.getByteContent();
		if (realAddress >= contents.length) {
			return;
		}
		contents[realAddress] = (byte) data;
		repaint();
	}

	@Override
	public void writeWord(int address, int data) {
		writeByte(address, (data & 0xFF));
		writeByte(address + 1, (data >> 8) & 0xFF);
	}

	@Override
	public void writeROM(byte[] bytes) {
		if (bytes.length > size) {
			System.err.println("Attempt to write " + bytes.length + " bytes to a ROM of size " + size
					+ ". Output truncated.");
		}
		byte[] romContents = romView.getByteContent();
		for (int i = 0; i < size; i++) {
			if (i >= bytes.length) {
				romContents[i] = 0;
				continue;
			}
			romContents[i] = bytes[i];
		}
		romView.repaint();
	}

	@Override
	public byte[] readROM() {
		return romView.getByteContent();
	}

	@Override
	public Component getUIComponent() {
		return this;
	}

	@Override
	public void resetRAM() {
		this.ramView.setByteContent(new byte[size]);
	}

}
