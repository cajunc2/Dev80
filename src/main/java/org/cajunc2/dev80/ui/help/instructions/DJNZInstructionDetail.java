package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class DJNZInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("DJNZ o", "10 o", "13/8", "2")
	});

	@Override
	public String instructionName() {
		return "djnz";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "B is decreased, and a jr label happens if the result was not zero. The flags are preserved. Since this is a relative jump, it can only point to its 128-byte vicinity.";
	}

}
