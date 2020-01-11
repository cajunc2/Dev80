package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class CALLInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(
	        new InstructionVariant("CALL nn", "CD nn nn", "17", "3"),
	        new InstructionVariant("CALL C,nn", "DC nn nn", "17/10", "3"),
	        new InstructionVariant("CALL M,nn", "FC nn nn", "17/10", "3"),
	        new InstructionVariant("CALL NC,nn", "D4 nn nn", "17/10", "3"),
	        new InstructionVariant("CALL NZ,nn", "C4 nn nn", "17/10", "3"),
	        new InstructionVariant("CALL P,nn", "F4 nn nn", "17/10", "3"),
	        new InstructionVariant("CALL PE,nn", "EC nn nn", "17/10", "3"),
	        new InstructionVariant("CALL PO,nn", "E4 nn nn", "17/10", "3"),
	        new InstructionVariant("CALL Z,nn", "CC nn nn", "17/10", "3"));

	@Override
	public String instructionName() {
		return "call";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The address of the instruction immediately following the call (i. e. PC+3) is saved to the stack, and execution is continued from the address given by the label. The conditions are the same as those of the absolute jump, i. e. all the eight conditions can be used. The flags are preserved.";
	}

}
