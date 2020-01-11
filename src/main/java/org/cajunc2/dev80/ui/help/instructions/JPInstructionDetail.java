package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class JPInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("JP nn", "C3 nn nn", "10", "3"),
	        new InstructionVariant("JP (HL)", "E9", "4", "1"),
	        new InstructionVariant("JP (IX)", "DD E9", "8", "2"),
	        new InstructionVariant("JP (IY)", "FD E9", "8", "2"),
	        new InstructionVariant("JP C,nn", "DA nn nn", "10", "3"),
	        new InstructionVariant("JP M,nn", "FA nn nn", "10", "3"),
	        new InstructionVariant("JP NC,nn", "D2 nn nn", "10", "3"),
	        new InstructionVariant("JP NZ,nn", "C2 nn nn", "10", "3"),
	        new InstructionVariant("JP P,nn", "F2 nn nn", "10", "3"),
	        new InstructionVariant("JP PE,nn", "EA nn nn", "10", "3"),
	        new InstructionVariant("JP PO,nn", "E2 nn nn", "10", "3"),
	        new InstructionVariant("JP Z,nn", "CA nn nn", "10", "3")
	});

	@Override
	public String instructionName() {
		return "jp";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "Absolute jumps to the address. Can be conditional or unconditional. JP takes one more byte than JR, but is also slightly faster, so decide whether speed or size is more important before choosing JP or JR. JP (HL), JP (IX), and JP (IY) are unconditional and are the fastest jumps, and do not take more bytes than other jumps.";
	}

}
