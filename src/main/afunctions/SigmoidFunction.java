package main.afunctions;

public class SigmoidFunction extends LogisticFunction {
	public static final String NAME = "Sigmoid";

	public SigmoidFunction() {
		super(0, 1, 1, 0);
	}

	@Override
	public String toString() {
		return NAME;
	}

	public static ActivationFunction fromString(String string) {
		return new SigmoidFunction();
	}
}
