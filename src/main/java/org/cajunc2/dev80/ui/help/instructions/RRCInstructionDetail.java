package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RRCInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RRC (HL)", "CB 0E", "15", "2"),
	        new InstructionVariant("RRC (IX+o)", "DD CB o 0E", "23", "4"),
	        new InstructionVariant("RRC (IY+o)", "FD CB o 0E", "23", "4"),
	        new InstructionVariant("RRC r", "CB 08+r", "8", "2")
	});

	@Override
	public String instructionName() {
		return "rrc";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "8-bit rotation to the right. The bit leaving on the right is copied into the carry, and to bit 7 of the operand as well. The H and N flags are reset, P/V is parity, S and Z are modified by definition.";
	}

}
