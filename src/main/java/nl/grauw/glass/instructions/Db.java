package nl.grauw.glass.instructions;

import java.util.List;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;

public class Db extends InstructionFactory {

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (arguments != null)
			return new Db_N(context, arguments.getFlatList());
		throw new ArgumentException();
	}

	public static class Db_N extends InstructionObject {

		private List<Expression> arguments;

		public Db_N(Scope context, List<Expression> arguments) {
			super(context);
			this.arguments = arguments;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.of(arguments.size());
		}

		@Override
		public byte[] getBytes() {
			byte[] bytes = new byte[arguments.size()];
			for (int i = 0, length = arguments.size(); i < length; i++)
				bytes[i] = (byte)arguments.get(i).getInteger();
			return bytes;
		}

	}

}
