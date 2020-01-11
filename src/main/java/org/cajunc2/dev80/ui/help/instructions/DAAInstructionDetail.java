package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class DAAInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("DAA", "27", "4", "1")
	});

	@Override
	public String instructionName() {
		return "daa";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Decimal Adjust Accumulator; no operands are needed. When this instruction is executed, the A register is BCD corrected using the contents of the flags. The exact process is the following: if the least significant four bits of A contain a non-BCD digit (i. e. it is greater than 9) or the H flag is set, then $06 is added to the register. Then the four most significant bits are checked. If this more significant digit also happens to be greater than 9 or the C flag is set, then $60 is added. If this second addition was needed, the C flag is set after execution, otherwise it is reset. The N flag is preserved, P/V is parity and the others are altered by definition.";
	}

}
