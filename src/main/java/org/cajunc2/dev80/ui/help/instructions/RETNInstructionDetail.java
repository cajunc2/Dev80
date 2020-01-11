package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RETNInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RETN", "ED 45", "14", "2")
	});

	@Override
	public String instructionName() {
		return "retn";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "This instruction returns from the non-maskable interrupt routine. From the programmer’s point of view it is no more than a simple ret, but it has to be used to properly handle the interrupt. Note that you cannot use conditions with retn. The flags are not affected.";
	}

}
