package org.cajunc2.dev80.ui.help.instructions;

import java.util.List;

public interface InstructionDetail {
	String instructionName();

	List<InstructionVariant> variants();

	String description();
}
