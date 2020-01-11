package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class RETInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("RET", "C9", "10", "1"),
	        new InstructionVariant("RET C", "D8", "11/5", "1"),
	        new InstructionVariant("RET M", "F8", "11/5", "1"),
	        new InstructionVariant("RET NC", "D0", "11/5", "1"),
	        new InstructionVariant("RET NZ", "C0", "11/5", "1"),
	        new InstructionVariant("RET P", "F0", "11/5", "1"),
	        new InstructionVariant("RET PE", "E8", "11/5", "1"),
	        new InstructionVariant("RET PO", "E0", "11/5", "1"),
	        new InstructionVariant("RET Z", "C8", "11/5", "1")
	});

	@Override
	public String instructionName() {
		return "ret";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The word on the top of the stack is retrieved and it is used as the address of the next instruction from which the execution is to be continued. This is basically a pop pc. The conditions work the same way as above, all of them can be used. The flags are preserved.";
	}

}
