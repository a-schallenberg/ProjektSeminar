package main.afunctions;

public class SignFunction implements ActivationFunction {
	public static final String NAME = "Sign";
	private double threshold, lowerLimit, upperLimit;

	public SignFunction(double threshold) {
		this(threshold, -1, 1);
	}

	public SignFunction(double threshold, double lowerLimit, double upperLimit) {
		this.threshold = threshold;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	@Override
	public double function(double x) {
		return x < threshold ? lowerLimit : upperLimit;
	}

	@Override
	public double derivative(double x) {
		return x == threshold ? Double.NaN: 0d;
	}

	@Override
	public String toString() {
		return NAME + "(" + threshold + " | " + lowerLimit + " | " + upperLimit + ")";
	}

	public static ActivationFunction fromString(String string) {
		string = string.replace(")", "").split("\\(")[1];
		String[] args = string.split(" \\| ");
		return new SignFunction(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
	}
}
