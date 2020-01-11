package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class BITInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(
	        new InstructionVariant("BIT b,(HL)", "CB 46+8*b", "12", "2"),
	        new InstructionVariant("BIT b,(IX+o)", "DD CB o 46+8*b", "20", "4"),
	        new InstructionVariant("BIT b,(IY+o)", "FD CB o 46+8*b", "20", "4"),
	        new InstructionVariant("BIT b,r", "CB 40+8*b+r", "8", "2"));

	@Override
	public String instructionName() {
		return "bit";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The opposite of the nth bit of the second operand is written into the Z flag. The carry is left intact, N is reset, H is set, while S and P/V are undefined.";
	}

}
