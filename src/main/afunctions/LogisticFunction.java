package main.afunctions;

public class LogisticFunction implements  ActivationFunction{
	public static final String NAME = "Logistic";
	private double offset, height, factor, ordinate;

	public LogisticFunction(double offset, double height, double factor, double ordinate) {
		this.offset = offset;
		this.height = height;
		this.factor = factor;
		this.ordinate = ordinate;
	}

	@Override
	public double function(double x) {
		return height/(1 + Math.exp(-factor * (x - offset)));
	}

	@Override
	public double derivative(double x) {
		double x0 = x - offset;
		return x0 * (1 - x0);
	}

	@Override
	public String toString() {
		return NAME + "(" + offset + ", " +  height + ", " + factor + ", " + ordinate + ")";
	}

	public static ActivationFunction fromString(String string) {
		string = string.replace(")", "").split("\\(")[1];
		return new LogisticFunction(Double.parseDouble(string), Double.parseDouble(string), Double.parseDouble(string), Double.parseDouble(string));
	}
}
