package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RETIInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RETI", "ED 4D", "14", "2")
	});

	@Override
	public String instructionName() {
		return "reti";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "This instruction returns from the interrupt routine. From the programmer’s point of view it is no more than a simple ret, but it has to be used to properly handle the interrupt. Note that you cannot use conditions with reti. The flags are not affected.";
	}

}
