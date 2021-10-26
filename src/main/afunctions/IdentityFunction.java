package main.afunctions;

public class IdentityFunction implements ActivationFunction {

	@Override
	public double function(double x) {
		return x;
	}

	@Override
	public double derivative(double x) {
		return 1;
	}
}
