package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class ADCInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(
	        new InstructionVariant("ADC A,(HL)", "8E", "7", "1"),
	        new InstructionVariant("ADC A,(IX+o)", "DD 8E o", "19", "3"),
	        new InstructionVariant("ADC A,(IY+o)", "FD 8E o", "19", "3"),
	        new InstructionVariant("ADC A,n", "CE n", "7", "2"),
	        new InstructionVariant("ADC A,r", "88+r", "4", "1"),
	        new InstructionVariant("ADC A,IXp", "DD 88+p", "8", "2"),
	        new InstructionVariant("ADC A,IYq", "FD 88+q", "8", "2"),
	        new InstructionVariant("ADC HL,BC", "ED 4A", "15", "2"),
	        new InstructionVariant("ADC HL,DE", "ED 5A", "15", "2"),
	        new InstructionVariant("ADC HL,HL", "ED 6A", "15", "2"),
	        new InstructionVariant("ADC HL,SP", "ED 7A", "15", "2"));

	@Override
	public String instructionName() {
		return "adc";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The sum of the two operands plus the carry flag (0 or 1) is calculated, and the result is written back into the first operand. The N flag is reset, P/V is interpreted as overflow. The rest of the flags is modified by definition. In the case of 16-bit addition the H flag is undefined.";
	}

}
