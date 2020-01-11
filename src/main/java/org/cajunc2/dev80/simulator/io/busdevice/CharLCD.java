package org.cajunc2.dev80.simulator.io.busdevice;

import java.util.Arrays;

import javax.swing.JFrame;

import org.cajunc2.dev80.simulator.io.BusDevice;

@SuppressWarnings("unused")
public class CharLCD {

	private final BusDevice controlDevice = new ControlDevice();
	private final BusDevice dataDevice = new DataDevice();
	private final int[] ddram = new int[80];
	private final CharLCDWindow window;
	int cursorIndex = 0;
	private boolean dirRight;
	private boolean shift;
	private boolean display;
	private boolean cursor;
	private boolean blink;
	private boolean eightBit;
	private boolean twoLine;
	private boolean highFont;

	public CharLCD(JFrame parent) {
		window = new CharLCDWindow(parent, 16, 2);
		window.pack();
		// window.setVisible(true);
	}

	public BusDevice getControlDevice() {
		return controlDevice;
	}

	public BusDevice getDataDevice() {
		return dataDevice;
	}

	void clearDisplay() {
		this.cursorIndex = 0;
		Arrays.fill(this.ddram, 0x20);
		window.displayData(ddram);
	}

	void returnHome() {
		this.cursorIndex = 0;
	}

	void entryModeSet(boolean dirRight, boolean shift) {
		this.dirRight = dirRight;
		this.shift = shift;
	}

	void displayOnOff(boolean display, boolean cursor, boolean blink) {
		this.display = display;
		this.cursor = cursor;
		this.blink = blink;
	}

	void shiftCursor(boolean toRight) {
		cursorIndex += toRight ? 1 : -1;
		if (cursorIndex == 80) {
			cursorIndex = 0;
		}
		if (cursorIndex == -1) {
			cursorIndex = 79;
		}
	}

	@SuppressWarnings("static-method")
	void shiftDisplay(boolean toRight) {
		System.err.println("TODO: CharLCD#shiftDisplay(" + toRight + ")");
	}

	void functionSet(boolean eightBit, boolean twoLine, boolean highFont) {
		this.eightBit = eightBit;
		this.twoLine = twoLine;
		this.highFont = highFont;
	}

	void ddWrite(int data) {
		this.ddram[cursorIndex] = data;
		if (shift) {
			// TODO
			System.err.println("TODO: CharLCD#ddWrite(" + Integer.toHexString(data) + ") in shift mode");
		} else {
			cursorIndex += dirRight ? 1 : -1;
			if (cursorIndex == 80) {
				cursorIndex = 0;
			}
			if (cursorIndex == -1) {
				cursorIndex = 79;
			}
		}
		window.displayData(ddram);
	}

	private class ControlDevice implements BusDevice {

		public ControlDevice() {
		}

		@Override
		public int read() {
			return 0;
		}

		@Override
		public void write(int newData) {
			if ((newData & 0b11111111) == 0b00000001) {
				clearDisplay();
				return;
			}
			if ((newData & 0b11111110) == 0b00000010) {
				returnHome();
				return;
			}
			if ((newData & 0b11111100) == 0b00000100) {
				boolean newDirRight = (newData & 0b00000010) == 0b00000010;
				boolean newShift = (newData & 0b00000001) == 0b00000001;
				entryModeSet(newDirRight, newShift);
				return;
			}
			if ((newData & 0b11111000) == 0b00001000) {
				boolean newDisplay = (newData & 0b00000100) == 0b00000100;
				boolean newCursor = (newData & 0b00000010) == 0b00000010;
				boolean newBlink = (newData & 0b00000001) == 0b00000001;
				displayOnOff(newDisplay, newCursor, newBlink);
				return;
			}
			if ((newData & 0b11110000) == 0b00010000) {
				boolean shiftMode = (newData & 0b00000010) == 0b00000010;
				boolean direction = (newData & 0b00000001) == 0b00000001;
				if (shiftMode) {
					shiftDisplay(direction);
					return;
				}
				shiftCursor(direction);
				return;
			}
			if ((newData & 0b11100000) == 0b00100000) {
				boolean twoLine = (newData & 0b00001000) == 0b00001000;
				boolean highFont = (newData & 0b00000100) == 0b00000100;
				functionSet(true, twoLine, highFont);
			}
			if ((newData & 0b11000000) == 0b01000000) {
				// set CGRAM address
			}
			if ((newData & 0b10000000) == 0b10000000) {
				int addr = newData & 0b01111111;
				CharLCD.this.cursorIndex = addr;
			}
		}

	}

	private class DataDevice implements BusDevice {

		public DataDevice() {
		}

		@Override
		public int read() {
			return 0;
		}

		@Override
		public void write(int data) {
			ddWrite(data);
		}

	}
}
