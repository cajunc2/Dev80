package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RLAInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RLA", "17", "4", "1")
	});

	@Override
	public String instructionName() {
		return "RLA";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "There are no operands to this instruction. It is the same as rl a except that it is faster and does not change the S, Z and P/V flags.";
	}

}
