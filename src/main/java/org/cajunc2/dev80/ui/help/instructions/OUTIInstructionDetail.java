package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class OUTIInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("OUTI", "ED A3", "16", "2")
	});

	@Override
	public String instructionName() {
		return "outi";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "There are no operands. Reads the byte at (HL) and outputs it to the (C) port, then increments HL and decrements B. The carry is preserved, the N flag is reset, while S, H and P/V are undefined. Z is set if B becomes zero after decrementing, otherwise it is reset.";
	}

}
