package org.cajunc2.dev80.ui.help.instructions;

public class InstructionVariant {
	private final String mnemonic;
	private final String opcode;
	private final String cycles;
	private final String size;

	InstructionVariant(String mnemonic, String opcode, String cycles, String size) {
		this.mnemonic = mnemonic;
		this.opcode = opcode;
		this.cycles = cycles;
		this.size = size;
	}

	public String getMnemonic() {
		return mnemonic;
	}

	public String getOpcode() {
		return opcode;
	}

	public String getCycles() {
		return cycles;
	}

	public String getSize() {
		return size;
	}

}
