package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class PUSHInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("PUSH AF", "F5", "11", "1"),
	        new InstructionVariant("PUSH BC", "C5", "11", "1"),
	        new InstructionVariant("PUSH DE", "D5", "11", "1"),
	        new InstructionVariant("PUSH HL", "E5", "11", "1"),
	        new InstructionVariant("PUSH IX", "DD E5", "15", "2"),
	        new InstructionVariant("PUSH IY", "FD E5", "15", "2")
	});

	@Override
	public String instructionName() {
		return "push";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "When the instruction is executed, SP is decreased by two and the value of the register is copied to the memory location pointed by the new value of SP. It does not affect the flags.";
	}

}
