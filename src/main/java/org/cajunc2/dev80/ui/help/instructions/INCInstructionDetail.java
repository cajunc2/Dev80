package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class INCInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("INC A", "3C", "4", "1"),
	        new InstructionVariant("INC B", "04", "4", "1"),
	        new InstructionVariant("INC C", "0C", "4", "1"),
	        new InstructionVariant("INC D", "14", "4", "1"),
	        new InstructionVariant("INC E", "1C", "4", "1"),
	        new InstructionVariant("INC H", "24", "4", "1"),
	        new InstructionVariant("INC L", "2C", "4", "1"),
	        new InstructionVariant("INC IXp", "DD 04+8*p", "8", "2"),
	        new InstructionVariant("INC IYq", "FD 04+8*q", "8", "2"),
	        new InstructionVariant("INC BC", "03", "6", "1"),
	        new InstructionVariant("INC DE", "13", "6", "1"),
	        new InstructionVariant("INC HL", "23", "6", "1"),
	        new InstructionVariant("INC IX", "DD 23", "10", "2"),
	        new InstructionVariant("INC IY", "FD 23", "10", "2"),
	        new InstructionVariant("INC SP", "33", "6", "1"),
	        new InstructionVariant("INC (HL)", "34", "11", "1"),
	        new InstructionVariant("INC (IX+o)", "DD 34 o", "23", "3"),
	        new InstructionVariant("INC (IY+o)", "FD 34 o", "23", "3")
	});

	@Override
	public String instructionName() {
		return "inc";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Increments the value of the operand by one. 8-bit increments preserve the C flag, reset N, treat P/V as overflow and modify the others by definition. 16-bit increments do not alter any of the flags.";
	}

}
