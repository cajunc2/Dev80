package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class ADDInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(
	        new InstructionVariant("ADD A,(HL)", "86", "7", "1"),
	        new InstructionVariant("ADD A,(IX+o)", "DD 86 o", "19", "3"),
	        new InstructionVariant("ADD A,(IY+o)", "FD 86 o", "19", "3"),
	        new InstructionVariant("ADD A,n", "C6 n", "7", "2"),
	        new InstructionVariant("ADD A,r", "80+r", "4", "1"),
	        new InstructionVariant("ADD A,IXp", "DD 80+p", "8", "2"),
	        new InstructionVariant("ADD A,IYq", "FD 80+q", "8", "2"),
	        new InstructionVariant("ADD HL,BC", "09", "11", "1"),
	        new InstructionVariant("ADD HL,DE", "19", "11", "1"),
	        new InstructionVariant("ADD HL,HL", "29", "11", "1"),
	        new InstructionVariant("ADD HL,SP", "39", "11", "1"),
	        new InstructionVariant("ADD IX,BC", "DD 09", "15", "2"),
	        new InstructionVariant("ADD IX,DE", "DD 19", "15", "2"),
	        new InstructionVariant("ADD IX,IX", "DD 29", "15", "2"),
	        new InstructionVariant("ADD IX,SP", "DD 39", "15", "2"),
	        new InstructionVariant("ADD IY,BC", "FD 09", "15", "2"),
	        new InstructionVariant("ADD IY,DE", "FD 19", "15", "2"),
	        new InstructionVariant("ADD IY,IY", "FD 29", "15", "2"),
	        new InstructionVariant("ADD IY,SP", "FD 39", "15", "2"));

	@Override
	public String instructionName() {
		return "add";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The value of the two operands is added together, and the result is written back to the first one. In the case of 8-bit additions the N flag is reset, P/V is interpreted as overflow. The rest of the flags is modified by definition. On the other hand, 16-bit additions preserve the S, Z and P/V flags, and H is undefined. For example, if the result is out of the range (8 or 16 bits depending on the first operand), the carry flag is set.";
	}

}
