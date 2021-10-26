package main.afunctions;

public class TangensHyperbolicus implements ActivationFunction {
	@Override
	public double function(double x) {
		return Math.tanh(x);
	}

	@Override
	public double derivative(double x) {
		double tanh = Math.tanh(x);
		return 1 - tanh * tanh;
	}
}
