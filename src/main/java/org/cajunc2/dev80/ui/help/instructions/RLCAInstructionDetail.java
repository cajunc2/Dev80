package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RLCAInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RLCA", "07", "4", "1")
	});

	@Override
	public String instructionName() {
		return "rlca";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "There are no operands to this instruction. It is the same as rlc a except that it is faster and preserves the S, Z and P/V flags.";
	}

}
