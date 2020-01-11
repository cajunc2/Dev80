package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class IMInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("IM 0", "ED 46", "8", "2"),
	        new InstructionVariant("IM 1", "ED 56", "8", "2"),
	        new InstructionVariant("IM 2", "ED 5E", "8", "2")
	});

	@Override
	public String instructionName() {
		return "im";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Sets the interrupt mode.";
	}

}
