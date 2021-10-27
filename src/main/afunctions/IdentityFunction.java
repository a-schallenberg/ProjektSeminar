package main.afunctions;

public class IdentityFunction implements ActivationFunction {
	public static final String NAME = "Identity";

	@Override
	public double function(double x) {
		return x;
	}

	@Override
	public double derivative(double x) {
		return 1;
	}

	@Override
	public String toString() {
		return NAME;
	}

	public static ActivationFunction fromString(String string) {
		return new IdentityFunction();
	}
}
