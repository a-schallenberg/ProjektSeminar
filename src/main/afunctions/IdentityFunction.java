package main.afunctions;

public class IdentityFunction implements AFunction{

	@Override
	public double function(double x) {
		return x;
	}

	@Override
	public double derivative(double x) {
		return 1;
	}
}
