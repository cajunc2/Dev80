package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class DefaultInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(
	        new InstructionVariant("ADC", "Add with Carry", "", ""),
	        new InstructionVariant("ADD", "Add", "", ""),
	        new InstructionVariant("AND", "Boolean AND", "", ""),
	        new InstructionVariant("BIT", "Test if bit is set", "", ""),
	        new InstructionVariant("CALL", "Call subroutine", "", ""),
	        new InstructionVariant("CCF", "", "", ""),
	        new InstructionVariant("CP", "", "", ""),
	        new InstructionVariant("CPD", "", "", ""),
	        new InstructionVariant("CPDR", "", "", ""),
	        new InstructionVariant("CPI", "", "", ""),
	        new InstructionVariant("CPIR", "", "", ""),
	        new InstructionVariant("CPL", "", "", ""),
	        new InstructionVariant("DAA", "", "", ""),
	        new InstructionVariant("DEC", "", "", ""),
	        new InstructionVariant("DI", "", "", ""),
	        new InstructionVariant("DJNZ", "Decrement B, Jump if not zero", "", ""),
	        new InstructionVariant("EI", "", "", ""),
	        new InstructionVariant("EX", "", "", ""),
	        new InstructionVariant("EXX", "", "", ""),
	        new InstructionVariant("HALT", "", "", ""),
	        new InstructionVariant("IM", "", "", ""),
	        new InstructionVariant("IN", "", "", ""),
	        new InstructionVariant("INC", "", "", ""),
	        new InstructionVariant("IND", "", "", ""),
	        new InstructionVariant("INDR", "", "", ""),
	        new InstructionVariant("INI", "", "", ""),
	        new InstructionVariant("INIR", "", "", ""),
	        new InstructionVariant("JP", "", "", ""),
	        new InstructionVariant("JR", "", "", ""),
	        new InstructionVariant("LD", "", "", ""),
	        new InstructionVariant("LDD", "", "", ""),
	        new InstructionVariant("LDDR", "", "", ""),
	        new InstructionVariant("LDI", "", "", ""),
	        new InstructionVariant("LDIR", "", "", ""),
	        new InstructionVariant("NEG", "", "", ""),
	        new InstructionVariant("NOP", "", "", ""),
	        new InstructionVariant("OR", "", "", ""),
	        new InstructionVariant("OTDR", "", "", ""),
	        new InstructionVariant("OTIR", "", "", ""),
	        new InstructionVariant("OUT", "", "", ""),
	        new InstructionVariant("OUTD", "", "", ""),
	        new InstructionVariant("OUTI", "", "", ""),
	        new InstructionVariant("POP", "", "", ""),
	        new InstructionVariant("PUSH", "", "", ""),
	        new InstructionVariant("RES", "", "", ""),
	        new InstructionVariant("RET", "", "", ""),
	        new InstructionVariant("RETI", "", "", ""),
	        new InstructionVariant("RETN", "", "", ""),
	        new InstructionVariant("RL", "", "", ""),
	        new InstructionVariant("RLA", "", "", ""),
	        new InstructionVariant("RLC", "", "", ""),
	        new InstructionVariant("RLCA", "", "", ""),
	        new InstructionVariant("RLD", "", "", ""),
	        new InstructionVariant("RR", "", "", ""),
	        new InstructionVariant("RRA", "", "", ""),
	        new InstructionVariant("RRC", "", "", ""),
	        new InstructionVariant("RRCA", "", "", ""),
	        new InstructionVariant("RRD", "", "", ""),
	        new InstructionVariant("RST", "", "", ""),
	        new InstructionVariant("SBC", "", "", ""),
	        new InstructionVariant("SCF", "", "", ""),
	        new InstructionVariant("SET", "", "", ""),
	        new InstructionVariant("SLA", "", "", ""),
	        new InstructionVariant("SLL/SL1", "", "", ""),
	        new InstructionVariant("SRA", "", "", ""),
	        new InstructionVariant("SRL", "", "", ""),
	        new InstructionVariant("SUB", "", "", ""),
	        new InstructionVariant("XOR", "", "", ""));

	@Override
	public String instructionName() {
		return "All Z80 Instructions";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return " ";
	}

}
