package org.cajunc2.dev80.ui.help.directives;

import java.util.List;

import org.cajunc2.dev80.ui.help.instructions.InstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.InstructionVariant;

public class IncludeDirectiveDetail implements InstructionDetail {

	@Override
	public String instructionName() {
		return "include";
	}

	@Override
	public List<InstructionVariant> variants() {
		return null;
	}

	@Override
	public String description() {
		return "Includes another source file.";
	}

}
