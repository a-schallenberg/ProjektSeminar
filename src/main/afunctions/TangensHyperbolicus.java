package main.afunctions;

public class TangensHyperbolicus implements AFunction{
	@Override
	public double function(double x) {
		return Math.tanh(x);
	}

	@Override
	public double derivative(double x) {
		double tanh = Math.tanh(x);
		return 1 - tanh * tanh;
	}

	@Override
	public String toString() {
		return "Tanh";
	}
}
