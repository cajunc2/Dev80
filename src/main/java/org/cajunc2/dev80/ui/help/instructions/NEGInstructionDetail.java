package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class NEGInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("NEG", "ED 44", "8", "2")
	});

	@Override
	public String instructionName() {
		return "neg";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "This instruction has no operands. When it is executed, the value in A is multiplied by -1 (two’s complement). The N flag is set, P/V is interpreted as overflow. The rest of the flags is modified by definition. This instruction is completely equivalent to cpl a followed by inc a (both in execution time and in size).";
	}

}
