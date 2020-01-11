package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class EXXInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("EXX", "D9", "4", "1")
	});

	@Override
	public String instructionName() {
		return "exx";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "There are no operands. This instruction exchanges BC with BC’, DE with DE’ and HL with HL’ at the same time. What’s very important to note that it is very fast.";
	}

}
