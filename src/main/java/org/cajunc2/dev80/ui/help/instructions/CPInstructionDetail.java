package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class CPInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(
	        new InstructionVariant("CP (HL)", "BE", "7", "1"),
	        new InstructionVariant("CP (IX+o)", "DD BE o", "19", "3"),
	        new InstructionVariant("CP (IY+o)", "FD BE o", "19", "3"),
	        new InstructionVariant("CP n", "FE n", "7", "2"),
	        new InstructionVariant("CP r", "B8+r", "4", "1"),
	        new InstructionVariant("CP IXp", "DD B8+p", "8", "2"),
	        new InstructionVariant("CP IYq", "FD B8+q", "8", "2"));

	@Override
	public String instructionName() {
		return "cp";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "This is a virtual subtraction from A, without writing back the result. You can regard it as a sub op8 that affects only the flags. The most important examples: if A = op8 then the C flag is reset, and Z is set. If A < op8, C is set and Z is reset. If A > op8 then both C and Z are reset.";
	}

}
