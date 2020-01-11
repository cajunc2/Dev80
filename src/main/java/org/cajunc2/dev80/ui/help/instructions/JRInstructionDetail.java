package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class JRInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("JR o", "18 o", "12", "2"),
	        new InstructionVariant("JR C,o", "38 o", "12/7", "2"),
	        new InstructionVariant("JR NC,o", "30 o", "12/7", "2"),
	        new InstructionVariant("JR NZ,o", "20 o", "12/7", "2"),
	        new InstructionVariant("JR Z,o", "28 o", "12/7", "2")
	});

	@Override
	public String instructionName() {
		return "jr";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Relative jumps to the address. This means that it can only jump between 128 bytes ahead or behind. Can be conditional or unconditional. JR takes up one less byte than JP, but is also slower. Weigh the needs of the code at the time before choosing one over the other (speed vs. size).";
	}

}
