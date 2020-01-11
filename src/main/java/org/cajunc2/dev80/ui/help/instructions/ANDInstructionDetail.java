package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class ANDInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(
	        new InstructionVariant("AND (HL)", "A6", "7", "1"),
	        new InstructionVariant("AND (IX+o)", "DD A6 o", "19", "3"),
	        new InstructionVariant("AND (IY+o)", "FD A6 o", "19", "3"),
	        new InstructionVariant("AND n", "E6 n", "7", "2"),
	        new InstructionVariant("AND r", "A0+r", "4", "1"),
	        new InstructionVariant("AND IXp", "DD A0+p", "8", "2"),
	        new InstructionVariant("AND IYq", "FD A0+q", "8", "2"));

	@Override
	public String instructionName() {
		return "and";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Performs bit-wise AND between A and the operand, and writes the result back to A. The C and N flags are cleared, P/V is parity, and the others are altered by definition.";
	}

}
