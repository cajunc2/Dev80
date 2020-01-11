package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class POPInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("POP AF", "F1", "10", "1"),
	        new InstructionVariant("POP BC", "C1", "10", "1"),
	        new InstructionVariant("POP DE", "D1", "10", "1"),
	        new InstructionVariant("POP HL", "E1", "10", "1"),
	        new InstructionVariant("POP IX", "DD E1", "14", "2"),
	        new InstructionVariant("POP IY", "FD E1", "14", "2")
	});

	@Override
	public String instructionName() {
		return "pop";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "When the instruction is executed, the value of the word found at the memory location pointed by SP is copied into reg16, then SP is increased by 2. No flags are affected (except for the case of popping into AF).";
	}

}
