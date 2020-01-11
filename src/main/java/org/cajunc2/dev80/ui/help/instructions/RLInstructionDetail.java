package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RLInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RL (HL)", "CB 16", "15", "2"),
	        new InstructionVariant("RL (IX+o)", "DD CB o 16", "23", "4"),
	        new InstructionVariant("RL (IY+o)", "FD CB o 16", "23", "4"),
	        new InstructionVariant("RL r", "CB 10+r", "8", "2")
	});

	@Override
	public String instructionName() {
		return "rl";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "9-bit rotation to the left. The bit leaving on the left is copied into the carry, while the old value of the carry appears in bit 0 of the operand. The H and N flags are reset, P/V is parity, S and Z are modified by definition.";
	}

}
