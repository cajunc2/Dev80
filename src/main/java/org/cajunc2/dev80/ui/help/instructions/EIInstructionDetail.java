package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class EIInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("EI", "FB", "4", "1")
	});

	@Override
	public String instructionName() {
		return "ei";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "There are no operands. When ei is executed, the (maskable) interrupts are enabled. The flags are preserved.";
	}

}
