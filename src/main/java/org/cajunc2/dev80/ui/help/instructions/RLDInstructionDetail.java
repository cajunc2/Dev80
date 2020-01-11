package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RLDInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RLD", "ED 6F", "18", "2")
	});

	@Override
	public String instructionName() {
		return "rld";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "This instruction has no operands. It is a 4-bit leftward rotation of the 12-bit number whose 4 most significant bits are the 4 least significant bits of A, and its 8 least significant bits are in (HL). I. e. if A contains %aaaaxxxx and (HL) is %yyyyzzzz initially, their final values will be A=%aaaayyyy and (HL)=%zzzzxxxx. The H and N flags are reset, P/V is parity, S and Z are modified by definition. The carry flag is preserved.";
	}

}
