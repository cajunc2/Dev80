package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Ldd extends InstructionFactory {

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (Ldd_.ARGUMENTS.check(arguments))
			return new Ldd_(context);
		throw new ArgumentException();
	}

	public static class Ldd_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Ldd_(Scope context) {
			super(context);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return new byte[] { (byte)0xED, (byte)0xA8 };
		}

	}

}
