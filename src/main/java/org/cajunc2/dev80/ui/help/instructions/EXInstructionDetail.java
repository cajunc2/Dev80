package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class EXInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("EX (SP),HL", "E3", "19", "1"),
	        new InstructionVariant("EX (SP),IX", "DD E3", "23", "2"),
	        new InstructionVariant("EX (SP),IY", "FD E3", "23", "2"),
	        new InstructionVariant("EX AF,AF'", "08", "4", "1"),
	        new InstructionVariant("EX DE,HL", "EB", "4", "1")
	});

	@Override
	public String instructionName() {
		return "ex";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The values of the two operands are exchanged. There is a total number of five combinations possible: ex (sp),hl, ex (sp),ix, ex (sp),iy, ex de,hl, ex af,af’. The last one naturally alters the flags (exchanges them with the shadow flags). You cannot exchange the order given, e. g. there is no ex hl,de";
	}

}
