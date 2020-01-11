package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class LDIInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("LDI", "ED A0", "16", "2")
	});

	@Override
	public String instructionName() {
		return "ldi";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The instruction copies a byte from (HL) to (DE) (i. e. it does an ld (de),(hl)), then increases both HL and DE to advance to the next byte, decreases BC, and sets the P/V flag in the case of overflowing.";
	}

}
