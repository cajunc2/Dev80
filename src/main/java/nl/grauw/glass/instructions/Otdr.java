package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Otdr extends InstructionFactory {

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (Otdr_.ARGUMENTS.check(arguments))
			return new Otdr_(context);
		throw new ArgumentException();
	}

	public static class Otdr_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Otdr_(Scope context) {
			super(context);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return new byte[] { (byte)0xED, (byte)0xBB };
		}

	}

}
