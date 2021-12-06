package main.functions;

/**
 * Differentiable Sign function
 */
public class SignDiff extends Sign{
	public SignDiff() {
		super(0);
	}

	public SignDiff(double threshold) {
		super(threshold, -1, 1);
	}

	public SignDiff(double threshold, double lowerLimit, double upperLimit) {
		super(threshold, lowerLimit, upperLimit);
	}

	@Override
	public double derivative(double x) {
		return 1d; // mathematically incorrect
	}
}
