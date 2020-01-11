package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class DIInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("DI", "F3", "4", "1")
	});

	@Override
	public String instructionName() {
		return "di";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "There are no operands. When di is executed, the (maskable) interrupts are disabled. The flags are preserved.";
	}

}
