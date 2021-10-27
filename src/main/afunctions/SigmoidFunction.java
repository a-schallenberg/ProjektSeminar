package main.afunctions;

public class SigmoidFunction implements ActivationFunction {
	public static final String NAME = "Sigmoid";
	private final double xOffset;

	public SigmoidFunction() {
		this(0);
	}

	public SigmoidFunction(double xOffset) {
		this.xOffset = xOffset;
	}

	@Override
	public double function(double x) {
		return 1 / (1 + Math.exp(-(x - xOffset)));
	}

	@Override
	public double derivative(double x) {
		double sx = function(x - xOffset);
 		return sx * (1 - sx);
	}

	public double inverse(double x) {
		return -Math.log(1/(x - xOffset) - 1);
	}

	public double[] inverse(double[] a) {
		double[] result = new double[a.length];

		for(int i = 0; i < result.length; i++)
			result[i] = inverse(a[i]);

		return result;
	}

	@Override
	public String toString() {
		return NAME + "(" + xOffset + ")";
	}

	public static ActivationFunction fromString(String string) {
		string = string.replace(")", "").split("\\(")[1];
		return new SigmoidFunction(Double.parseDouble(string));
	}
}
