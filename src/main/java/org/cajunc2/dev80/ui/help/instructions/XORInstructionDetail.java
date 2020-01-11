package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class XORInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("XOR (HL)", "AE", "7", "1"),
	        new InstructionVariant("XOR (IX+o)", "DD AE o", "19", "3"),
	        new InstructionVariant("XOR (IY+o)", "FD AE o", "19", "3"),
	        new InstructionVariant("XOR n", "EE n", "7", "2"),
	        new InstructionVariant("XOR r", "A8+r", "4", "1"),
	        new InstructionVariant("XOR IXp", "DD A8+p", "8", "2"),
	        new InstructionVariant("XOR IYq", "FD A8+q", "8", "2")
	});

	@Override
	public String instructionName() {
		return "xor";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Performs bit-wise XOR (exclusive or) between A and the operand, and writes the result back to A. The C and N flags are cleared, P/V is parity, and the others are altered by definition.";
	}

}
