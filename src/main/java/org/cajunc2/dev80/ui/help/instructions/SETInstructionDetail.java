package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class SETInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("SET b,(HL)", "CB C6+8*b", "15", "2"),
	        new InstructionVariant("SET b,(IX+o)", "DD CB o C6+8*b", "23", "4"),
	        new InstructionVariant("SET b,(IY+o)", "FD CB o C6+8*b", "23", "4"),
	        new InstructionVariant("SET b,r", "CB C0+8*b+r", "8", "2"),
	});

	@Override
	public String instructionName() {
		return "set";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Sets the nth bit of the operand given. The flags are preserved.";
	}

}
