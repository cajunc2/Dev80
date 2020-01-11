package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class SUBInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("SUB (HL)", "96", "7", "1"),
	        new InstructionVariant("SUB (IX+o)", "DD 96 o", "19", "3"),
	        new InstructionVariant("SUB (IY+o)", "FD 96 o", "19", "3"),
	        new InstructionVariant("SUB n", "D6 n", "7", "2"),
	        new InstructionVariant("SUB r", "90+r", "4", "1"),
	        new InstructionVariant("SUB IXp", "DD 90+p", "8", "2"),
	        new InstructionVariant("SUB IYq", "FD 90+q", "8", "2")
	});

	@Override
	public String instructionName() {
		return "sub";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The value of the operand is subtracted from A, and the result is also written back to A. The N flag is set, P/V is interpreted as overflow. The rest of the flags is modified by definition. There is no 16-bit version.";
	}

}
