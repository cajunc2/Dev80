package nl.grauw.glass.directives;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.SectionContextLiteral;

public class Ds extends Directive {

	@Override
	public void register(Scope scope, Line line) {
		nl.grauw.glass.instructions.Ds ds = new nl.grauw.glass.instructions.Ds();
		line.setInstruction(ds);
		SectionContextLiteral sectionContextLiteral = new SectionContextLiteral(line.getScope(), ds);
		for (String label : line.getLabels())
			scope.addSymbol(label, sectionContextLiteral);
	}

}
