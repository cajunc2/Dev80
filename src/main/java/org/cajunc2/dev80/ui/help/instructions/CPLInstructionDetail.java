package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class CPLInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(
	        new InstructionVariant("CPL", "2F", "4", "1"));

	@Override
	public String instructionName() {
		return "cpl";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "This instruction has no operands. It gives the one’s complement of A, i. e. all the bits of A are reversed individually. It sets the H and N flags, and leaves the others intact.";
	}

}
