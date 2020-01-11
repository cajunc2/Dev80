package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class SLAInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("SLA (HL)", "CB 26", "15", "2"),
	        new InstructionVariant("SLA (IX+o)", "DD CB o 26", "23", "4"),
	        new InstructionVariant("SLA (IY+o)", "FD CB o 26", "23", "4"),
	        new InstructionVariant("SLA r", "CB 20+r", "8", "2")
	});

	@Override
	public String instructionName() {
		return "sla";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The bits of the operand are shifted leftwards, bit 0 (the least significant bit) is reset. The bit leaving the operand on the left appears in the carry. The H and N flags are reset, P/V is parity, S and Z are modified by definition.";
	}

}
