package main.afunctions;

public class SignFunction implements AFunction{
	private double threshold;

	public SignFunction(double threshold) {
		this.threshold = threshold;
	}

	@Override
	public double function(double x) {
		return x < threshold ? -1d : 1d;
	}

	@Override
	public double derivative(double x) {
		return 0d;
	}

	@Override
	public String toString() {
		return "Sign";
	}
}
