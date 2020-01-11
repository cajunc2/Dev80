package org.cajunc2.dev80.ui.help.instructions;

import java.util.Arrays;
import java.util.List;

public class LDInstructionDetail implements InstructionDetail {

	private static final List<InstructionVariant> variants = Arrays.asList(new InstructionVariant[] {
	        new InstructionVariant("LD (BC),A", "02", "7", "1"),
	        new InstructionVariant("LD (DE),A", "12", "7", "1"),
	        new InstructionVariant("LD (HL),n", "36 n", "10", "2"),
	        new InstructionVariant("LD (HL),r", "70+r", "7", "1"),
	        new InstructionVariant("LD (IX+o),n", "DD 36 o n", "19", "4"),
	        new InstructionVariant("LD (IX+o),r", "DD 70+r o", "19", "3"),
	        new InstructionVariant("LD (IY+o),n", "FD 36 o n", "19", "4"),
	        new InstructionVariant("LD (IY+o),r", "FD 70+r o", "19", "3"),
	        new InstructionVariant("LD (nn),A", "32 nn nn", "13", "3"),
	        new InstructionVariant("LD (nn),BC", "ED 43 nn nn", "20", "4"),
	        new InstructionVariant("LD (nn),DE", "ED 53 nn nn", "20", "4"),
	        new InstructionVariant("LD (nn),HL", "22 nn nn", "16", "3"),
	        new InstructionVariant("LD (nn),IX", "DD 22 nn nn", "20", "4"),
	        new InstructionVariant("LD (nn),IY", "FD 22 nn nn", "20", "4"),
	        new InstructionVariant("LD (nn),SP", "ED 73 nn nn", "20", "4"),
	        new InstructionVariant("LD A,(BC)", "0A", "7", "1"),
	        new InstructionVariant("LD A,(DE)", "1A", "7", "1"),
	        new InstructionVariant("LD A,(HL)", "7E", "7", "1"),
	        new InstructionVariant("LD A,(IX+o)", "DD 7E o", "19", "3"),
	        new InstructionVariant("LD A,(IY+o)", "FD 7E o", "19", "3"),
	        new InstructionVariant("LD A,(nn)", "3A nn nn", "13", "3"),
	        new InstructionVariant("LD A,n", "3E n", "7", "2"),
	        new InstructionVariant("LD A,r", "78+r", "4", "1"),
	        new InstructionVariant("LD A,IXp", "DD 78+p", "8", "2"),
	        new InstructionVariant("LD A,IYq", "FD 78+q", "8", "2"),
	        new InstructionVariant("LD A,I", "ED 57", "9", "2"),
	        new InstructionVariant("LD A,R", "ED 5F", "9", "2"),
	        new InstructionVariant("LD B,(HL)", "46", "7", "1"),
	        new InstructionVariant("LD B,(IX+o)", "DD 46 o", "19", "3"),
	        new InstructionVariant("LD B,(IY+o)", "FD 46 o", "19", "3"),
	        new InstructionVariant("LD B,n", "06 n", "7", "2"),
	        new InstructionVariant("LD B,r", "40+r", "4", "1"),
	        new InstructionVariant("LD B,IXp", "DD 40+p", "8", "2"),
	        new InstructionVariant("LD B,IYq", "FD 40+q", "8", "2"),
	        new InstructionVariant("LD BC,(nn)", "ED 4B nn nn", "20", "4"),
	        new InstructionVariant("LD BC,nn", "01 nn nn", "10", "3"),
	        new InstructionVariant("LD C,(HL)", "4E", "7", "1"),
	        new InstructionVariant("LD C,(IX+o)", "DD 4E o", "19", "3"),
	        new InstructionVariant("LD C,(IY+o)", "FD 4E o", "19", "3"),
	        new InstructionVariant("LD C,n", "0E n", "7", "2"),
	        new InstructionVariant("LD C,r", "48+r", "4", "1"),
	        new InstructionVariant("LD C,IXp", "DD 48+p", "8", "2"),
	        new InstructionVariant("LD C,IYq", "FD 48+q", "8", "2"),
	        new InstructionVariant("LD D,(HL)", "56", "7", "1"),
	        new InstructionVariant("LD D,(IX+o)", "DD 56 o", "19", "3"),
	        new InstructionVariant("LD D,(IY+o)", "FD 56 o", "19", "3"),
	        new InstructionVariant("LD D,n", "16 n", "7", "2"),
	        new InstructionVariant("LD D,r", "50+r", "4", "1"),
	        new InstructionVariant("LD D,IXp", "DD 50+p", "8", "2"),
	        new InstructionVariant("LD D,IYq", "FD 50+q", "8", "2"),
	        new InstructionVariant("LD DE,(nn)", "ED 5B nn nn", "20", "4"),
	        new InstructionVariant("LD DE,nn", "11 nn nn", "10", "3"),
	        new InstructionVariant("LD E,(HL)", "5E", "7", "1"),
	        new InstructionVariant("LD E,(IX+o)", "DD 5E o", "19", "3"),
	        new InstructionVariant("LD E,(IY+o)", "FD 5E o", "19", "3"),
	        new InstructionVariant("LD E,n", "1E n", "7", "2"),
	        new InstructionVariant("LD E,r", "58+r", "4", "1"),
	        new InstructionVariant("LD E,IXp", "DD 58+p", "8", "2"),
	        new InstructionVariant("LD E,IYq", "FD 58+q", "8", "2"),
	        new InstructionVariant("LD H,(HL)", "66", "7", "1"),
	        new InstructionVariant("LD H,(IX+o)", "DD 66 o", "19", "3"),
	        new InstructionVariant("LD H,(IY+o)", "FD 66 o", "19", "3"),
	        new InstructionVariant("LD H,n", "26 n", "7", "2"),
	        new InstructionVariant("LD H,r", "60+r", "4", "1"),
	        new InstructionVariant("LD HL,(nn)", "2A nn nn", "16", "5"),
	        new InstructionVariant("LD HL,nn", "21 nn nn", "10", "3"),
	        new InstructionVariant("LD I,A", "ED 47", "9", "2"),
	        new InstructionVariant("LD IX,(nn)", "DD 2A nn nn", "20", "4"),
	        new InstructionVariant("LD IX,nn", "DD 21 nn nn", "14", "4"),
	        new InstructionVariant("LD IXh,n", "DD 26 n", "11", "2"),
	        new InstructionVariant("LD IXh,p", "DD 60+p", "8", "2"),
	        new InstructionVariant("LD IXl,n", "DD 2E n", "11", "2"),
	        new InstructionVariant("LD IXl,p", "DD 68+p", "8", "2"),
	        new InstructionVariant("LD IY,(nn)", "FD 2A nn nn", "20", "4"),
	        new InstructionVariant("LD IY,nn", "FD 21 nn nn", "14", "4"),
	        new InstructionVariant("LD IYh,n", "FD 26 n", "11", "2"),
	        new InstructionVariant("LD IYh,q", "FD 60+q", "8", "2"),
	        new InstructionVariant("LD IYl,n", "FD 2E n", "11", "2"),
	        new InstructionVariant("LD IYl,q", "FD 68+q", "8", "2"),
	        new InstructionVariant("LD L,(HL)", "6E", "7", "1"),
	        new InstructionVariant("LD L,(IX+o)", "DD 6E o", "19", "3"),
	        new InstructionVariant("LD L,(IY+o)", "FD 6E o", "19", "3"),
	        new InstructionVariant("LD L,n", "2E n", "7", "2"),
	        new InstructionVariant("LD L,r", "68+r", "4", "1"),
	        new InstructionVariant("LD R,A", "ED 4F", "9", "2"),
	        new InstructionVariant("LD SP,(nn)", "ED 7B nn nn", "20", "4"),
	        new InstructionVariant("LD SP,HL", "F9", "6", "1"),
	        new InstructionVariant("LD SP,IX", "DD F9", "10", "2"),
	        new InstructionVariant("LD SP,IY", "FD F9", "10", "2"),
	        new InstructionVariant("LD SP,nn", "31 nn nn", "10", "3")
	});

	@Override
	public String instructionName() {
		return "ld";
	}

	@Override
	public List<InstructionVariant> variants() {
		return variants;
	}

	@Override
	public String description() {
		return "The ld instruction copies the value of the second operand into the first operand. It does not alter any of the flags, except for the special cases of reading the I or R registers. The two operands must fit in size; they can be either 8 or 16 bits long.";
	}

}
