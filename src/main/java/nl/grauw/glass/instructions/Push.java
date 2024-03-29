package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Push extends InstructionFactory {

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (Push_RR.ARGUMENTS.check(arguments))
			return new Push_RR(context, arguments.getElement(0));
		throw new ArgumentException();
	}

	public static class Push_RR extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_RR_AF_INDEX);

		Expression argument;

		public Push_RR(Scope context, Expression argument) {
			super(context);
			this.argument = argument;
		}

		@Override
		public Expression getSize() {
			return indexifyDirect(argument.getRegister(), IntegerLiteral.ONE);
		}

		@Override
		public byte[] getBytes() {
			Register register = argument.getRegister();
			return indexifyDirect(register, (byte)(0xC5 | register.get16BitCode() << 4));
		}

	}

}
