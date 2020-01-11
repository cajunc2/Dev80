package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class LDIRInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("LDIR", "ED B0", "21/16", "2")
	});

	@Override
	public String instructionName() {
		return "ldir";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "This is an ldi repeated until BC reaches zero. Naturally, the P/V flag holds zero after leaving the instruction, since BC does not overflow. This single instruction copies BC bytes from (HL) to (DE), increases both HL and DE by BC, and sets BC to zero.";
	}

}
