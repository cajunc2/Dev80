package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RRCAInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RRCA", "0F", "4", "1")
	});

	@Override
	public String instructionName() {
		return "rrca";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "There are no operands to this instruction. It is the same as rrc a except that it is faster and preserves the S, Z and P/V flags.";
	}

}
