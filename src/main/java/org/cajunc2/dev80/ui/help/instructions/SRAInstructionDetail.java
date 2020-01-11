package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class SRAInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("SRA (HL)", "CB 2E", "15", "2"),
	        new InstructionVariant("SRA (IX+o)", "DD CB o 2E", "23", "4"),
	        new InstructionVariant("SRA (IY+o)", "FD CB o 2E", "23", "4"),
	        new InstructionVariant("SRA r", "CB 28+r", "8", "2")
	});

	@Override
	public String instructionName() {
		return "sra";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The bits of the operand are shifted rightwards, except for bit 7 (the most significant bit), whose value is left intact (the sign is preserved). The bit leaving the operand on the right appears in the carry. The H and N flags are reset, P/V is parity, S and Z are modified by definition.";
	}

}
