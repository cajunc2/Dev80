package org.cajunc2.dev80.ui.help.directives;

import java.util.List;

import org.cajunc2.dev80.ui.help.instructions.InstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.InstructionVariant;

public class DSDirectiveDetail implements InstructionDetail {

	@Override
	public String instructionName() {
		return "ds";
	}

	@Override
	public List<InstructionVariant> variants() {
		return null;
	}

	@Override
	public String description() {
		return "<html><body>\"Define Space\". It takes one or two arguments, <i>num</i> and <i>val</i>. It reserves <i>num</i> bytes of space and initializes them to <i>val</i>. If <i>val</i> is omitted, it defaults to 0.";
	}

}
