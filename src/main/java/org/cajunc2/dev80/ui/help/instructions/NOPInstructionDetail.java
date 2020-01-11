package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class NOPInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("NOP", "00", "4", "1"),
	});

	@Override
	public String instructionName() {
		return "nop";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "This instruction waits for 4 clock cycles and does nothing else.";
	}

}
