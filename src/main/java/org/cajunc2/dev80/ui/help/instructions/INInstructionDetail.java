package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class INInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("IN A,(C)", "ED 78", "12", "2"),
	        new InstructionVariant("IN A,(n)", "DB n", "11", "2"),
	        new InstructionVariant("IN B,(C)", "ED 40", "12", "2"),
	        new InstructionVariant("IN C,(C)", "ED 48", "12", "2"),
	        new InstructionVariant("IN D,(C)", "ED 50", "12", "2"),
	        new InstructionVariant("IN E,(C)", "ED 58", "12", "2"),
	        new InstructionVariant("IN H,(C)", "ED 60", "12", "2"),
	        new InstructionVariant("IN L,(C)", "ED 68", "12", "2"),
	        new InstructionVariant("IN F,(C)", "ED 70", "12", "3")
	});

	@Override
	public String instructionName() {
		return "in";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Reading from the port marked by the second operand, and storing the result into the first operand. The carry is preserved, N is cleared, P/V is parity and the other flags are affected by definition.";
	}

}
