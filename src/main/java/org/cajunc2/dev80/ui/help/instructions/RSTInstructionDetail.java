package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RSTInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RST 0", "C7", "11", "1"),
	        new InstructionVariant("RST 8H", "CF", "11", "1"),
	        new InstructionVariant("RST 10H", "D7", "11", "1"),
	        new InstructionVariant("RST 18H", "DF", "11", "1"),
	        new InstructionVariant("RST 20H", "E7", "11", "1"),
	        new InstructionVariant("RST 28H", "EF", "11", "1"),
	        new InstructionVariant("RST 30H", "F7", "11", "1"),
	        new InstructionVariant("RST 38H", "FF", "11", "1")
	});

	@Override
	public String instructionName() {
		return "rst";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Restart - basically a call to the given address. Does not affect flags.";
	}

}
