package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class DECInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("DEC (HL)", "35", "11", "1"),
	        new InstructionVariant("DEC (IX+o)", "DD 35 o", "23", "3"),
	        new InstructionVariant("DEC (IY+o)", "FD 35 o", "23", "3"),
	        new InstructionVariant("DEC A", "3D", "4", "1"),
	        new InstructionVariant("DEC B", "05", "4", "1"),
	        new InstructionVariant("DEC BC", "0B", "6", "1"),
	        new InstructionVariant("DEC C", "0D", "4", "1"),
	        new InstructionVariant("DEC D", "15", "4", "1"),
	        new InstructionVariant("DEC DE", "1B", "6", "1"),
	        new InstructionVariant("DEC E", "1D", "4", "1"),
	        new InstructionVariant("DEC H", "25", "4", "1"),
	        new InstructionVariant("DEC HL", "2B", "6", "1"),
	        new InstructionVariant("DEC IX", "DD 2B", "10", "2"),
	        new InstructionVariant("DEC IY", "FD 2B", "10", "2"),
	        new InstructionVariant("DEC IXp", "DD 05+8*p", "8", "2"),
	        new InstructionVariant("DEC IYq", "FD 05+8*q", "8", "2"),
	        new InstructionVariant("DEC L", "2D", "4", "2"),
	        new InstructionVariant("DEC SP", "3B", "6", "1")
	});

	@Override
	public String instructionName() {
		return "dec";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Decrements the value of the operand by one. 8-bit decrements preserve the C flag, set N, treat P/V as overflow and modify the others by definition. 16-bit decrements do not alter any of the flags.";
	}

}
