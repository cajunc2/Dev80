package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class SRLInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("SRL (HL)", "CB 3E", "15", "2"),
	        new InstructionVariant("SRL (IX+o)", "DD CB o 3E", "23", "4"),
	        new InstructionVariant("SRL (IY+o)", "FD CB o 3E", "23", "4"),
	        new InstructionVariant("SRL r", "CB 38+r", "8", "2")
	});

	@Override
	public String instructionName() {
		return "srl";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The bits of the operand are shifted rightwards, bit 7 is reset. The bit leaving the operand on the right appears in the carry. The H and N flags are reset, P/V is parity, S and Z are modified by definition.";
	}

}
