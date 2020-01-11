package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RRInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RR (HL)", "CB 1E", "15", "2"),
	        new InstructionVariant("RR (IX+o)", "DD CB o 1E", "23", "4"),
	        new InstructionVariant("RR (IY+o)", "FD CB o 1E", "23", "4"),
	        new InstructionVariant("RR r", "CB 18+r", "8", "2")
	});

	@Override
	public String instructionName() {
		return "rr";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "9-bit rotation to the right. The bit leaving on the right is copied into the carry, while the old value of the carry appears in bit 7 of the operand. The H and N flags are reset, P/V is parity, S and Z are modified by definition.";
	}

}
