package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class ORInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("OR (HL)", "B6", "7", "1"),
	        new InstructionVariant("OR (IX+o)", "DD B6 o", "19", "3"),
	        new InstructionVariant("OR (IY+o)", "FD B6 o", "19", "3"),
	        new InstructionVariant("OR n", "F6 n", "7", "2"),
	        new InstructionVariant("OR r", "B0+r", "4", "1"),
	        new InstructionVariant("OR IXp", "DD B0+p", "8", "2"),
	        new InstructionVariant("OR IYq", "FD B0+q", "8", "2")
	});

	@Override
	public String instructionName() {
		return "or";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Performs bit-wise OR between A and the operand, and writes the result back to A. The C and N flags are cleared, P/V is parity, and the others are altered by definition.";
	}

}
