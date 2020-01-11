package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class CPDInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(
	        new InstructionVariant("CPD", "ED A9", "16", "2"));

	@Override
	public String instructionName() {
		return "cpd";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "There are no operands. This is a cp (hl); dec hl; dec bc combined in one instruction. The carry is preserved, N is set and all the other flags are affected as defined. P/V denotes the overflowing of BC, while the Z flag is set if A=(HL) at the time of the comparison.";
	}

}
