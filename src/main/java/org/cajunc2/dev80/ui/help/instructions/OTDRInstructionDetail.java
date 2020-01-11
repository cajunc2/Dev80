package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class OTDRInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("OTDR", "ED BB", "21/16", "2")
	});

	@Override
	public String instructionName() {
		return "otdr";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "This is outd repeated until B becomes zero. Therefore the Z flag is always set on leaving the instruction.";
	}

}
