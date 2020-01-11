package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class OUTInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("OUT (C),A", "ED 79", "12", "2"),
	        new InstructionVariant("OUT (C),B", "ED 41", "12", "2"),
	        new InstructionVariant("OUT (C),C", "ED 49", "12", "2"),
	        new InstructionVariant("OUT (C),D", "ED 51", "12", "2"),
	        new InstructionVariant("OUT (C),E", "ED 59", "12", "2"),
	        new InstructionVariant("OUT (C),H", "ED 61", "12", "2"),
	        new InstructionVariant("OUT (C),L", "ED 69", "12", "2"),
	        new InstructionVariant("OUT (n),A", "D3 n", "11", "2")
	});

	@Override
	public String instructionName() {
		return "out";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Writing the value of the second operand to the port given in the first operand. The flags are preserved.";
	}

}
