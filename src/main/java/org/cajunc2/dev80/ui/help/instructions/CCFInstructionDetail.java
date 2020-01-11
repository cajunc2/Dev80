package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class CCFInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(
	        new InstructionVariant("CCF", "3F", "4", "1"));

	@Override
	public String instructionName() {
		return "ccf";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Complement Carry Flag: Negates the C flag and clears the N flag. H holds an undefined value after execution.";
	}

}
