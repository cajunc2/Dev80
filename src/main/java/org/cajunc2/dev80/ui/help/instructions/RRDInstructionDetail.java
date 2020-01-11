package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RRDInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RRD", "ED 67", "18", "2")
	});

	@Override
	public String instructionName() {
		return "rrd";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "This instruction has no operands. It is a 4-bit rightward rotation of the 12-bit number whose 4 most significant bits are the 4 least significant bits of A, and its 8 least significant bits are in (HL). I. e. if A contains %aaaaxxxx and (HL) is %yyyyzzzz initially, their final values will be A=%aaaazzzz and (HL)=%xxxxyyyy. The H and N flags are reset, P/V is parity, S and Z are modified by definition. The carry flag is preserved.";
	}

}
