package nl.grauw.glass.expressions;

public class Annotation extends Expression {

	private final Identifier annotation;
	private final Expression annotee;

	public Annotation(Identifier annotation, Expression annotee) {
		this.annotation = annotation;
		this.annotee = annotee;
	}

	@Override
	public Annotation copy(Context context) {
		return new Annotation(annotation.copy(context), annotee.copy(context));
	}

	public Identifier getAnnotation() {
		return annotation;
	}

	public Expression getAnnotee() {
		return annotee;
	}

	@Override
	public boolean is(Expression type) {
		return type.is(Type.ANNOTATION);
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.ANNOTATION))
			return this;
		return super.get(type);
	}

	public String toString() {
		return "" + annotation + " " + annotee;
	}

	public String toDebugString() {
		return "{" + annotation.toDebugString() + " " + annotee.toDebugString() + "}";
	}

}
