package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class HALTInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("HALT", "76", "4", "1")
	});

	@Override
	public String instructionName() {
		return "halt";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The execution of instructions is suspended and the CPU enters the low power state until an interrupt occurs.";
	}

}
