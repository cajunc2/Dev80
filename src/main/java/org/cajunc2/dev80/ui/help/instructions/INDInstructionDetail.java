package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class INDInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("IND", "ED AA", "16", "2")
	});

	@Override
	public String instructionName() {
		return "ind";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "There are no operands. Reads the (C) port and writes the result to (HL), then decrements HL and decrements B (not BC!). The carry is preserved, the N flag is reset, while S, H and P/V are undefined. Z is set if B becomes zero after decrementing, otherwise it is reset.";
	}

}
