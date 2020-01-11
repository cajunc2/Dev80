package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RESInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RES b,(HL)", "CB 86+8*b", "15", "2"),
	        new InstructionVariant("RES b,(IX+o)", "DD CB o 86+8*b", "23", "4"),
	        new InstructionVariant("RES b,(IY+o)", "FD CB o 86+8*b", "23", "4"),
	        new InstructionVariant("RES b,r", "CB 80+8*b+r", "8", "2")
	});

	@Override
	public String instructionName() {
		return "res";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Resets the nth bit of the operand given. Again, the flags are preserved.";
	}

}
