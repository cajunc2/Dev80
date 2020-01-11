package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RLCInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RLC (HL)", "CB 06", "15", "2"),
	        new InstructionVariant("RLC (IX+o)", "DD CB o 06", "23", "4"),
	        new InstructionVariant("RLC (IY+o)", "FD CB o 06", "23", "4"),
	        new InstructionVariant("RLC r", "CB 00+r", "8", "2")
	});

	@Override
	public String instructionName() {
		return "rlc";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "8-bit rotation to the left. The bit leaving on the left is copied into the carry, and to bit 0 of the operand as well. The H and N flags are reset, P/V is parity, S and Z are modified by definition.";
	}

}
