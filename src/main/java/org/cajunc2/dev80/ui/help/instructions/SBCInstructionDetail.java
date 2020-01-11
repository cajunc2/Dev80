package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class SBCInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("SBC A,(HL)", "9E", "7", "1"),
	        new InstructionVariant("SBC A,(IX+o)", "DD 9E o", "19", "3"),
	        new InstructionVariant("SBC A,(IY+o)", "FD 9E o", "19", "3"),
	        new InstructionVariant("SBC A,n", "DE n", "7", "2"),
	        new InstructionVariant("SBC A,r", "98+r", "4", "1"),
	        new InstructionVariant("SBC A,IXp", "DD 98+p", "8", "2"),
	        new InstructionVariant("SBC A,IYq", "FD 98+q", "8", "2"),
	        new InstructionVariant("SBC HL,BC", "ED 42", "15", "2"),
	        new InstructionVariant("SBC HL,DE", "ED 52", "15", "2"),
	        new InstructionVariant("SBC HL,HL", "ED 62", "15", "2"),
	        new InstructionVariant("SBC HL,SP", "ED 72", "15", "2")
	});

	@Override
	public String instructionName() {
		return "sbc";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The sum of the second operand and the carry flag (0 or 1) is subtracted from the first operand, where the final result is written back as well. The N flag is set, P/V is interpreted as overflow. The rest of the flags is modified by definition. In the case of 16-bit subtraction the H flag is undefined.";
	}

}
