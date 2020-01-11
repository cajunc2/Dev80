package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class CPIRInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(
	        new InstructionVariant("CPIR", "ED B1", "21/16", "2"));

	@Override
	public String instructionName() {
		return "cpir";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Simply cpi repeated until either BC becomes zero or A is equal to (HL).";
	}

}
