package main.afunctions;

public class SemiLinearFunction implements ActivationFunction {
	public static final String NAME = "Semi-Linear";

	private final double lowerLimit, upperLimit;

	public SemiLinearFunction() {
		this(-1, 1);
	}

	public SemiLinearFunction(double lowerLimit, double upperLimit) {
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	@Override
	public double function(double x) {
		if(x <= lowerLimit) return lowerLimit;
		if(x >= upperLimit) return upperLimit;
		return x;
	}

	@Override
	public double derivative(double x) {
		return (x < upperLimit && x > lowerLimit) ? 1d : 0d;
	}

	@Override
	public String toString() {
		return NAME + "(" + lowerLimit + " | " + upperLimit + ")";
	}

	public static ActivationFunction fromString(String string) {
		string = string.replace(")", "").split("\\(")[1];
		String[] args = string.split(" \\| ");
		return new SemiLinearFunction(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
	}
}
