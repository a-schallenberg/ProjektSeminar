package main.afunctions;

public class SigmoidFunction implements ActivationFunction {
	@Override
	public double function(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	@Override
	public double derivative(double x) {
		double sx = function(x);
 		return sx * (1 - sx);
	}

	public double inverse(double x) {
		return -Math.log(1/x - 1);
	}

	public double[] inverse(double[] a) {
		double[] result = new double[a.length];

		for(int i = 0; i < result.length; i++)
			result[i] = inverse(a[i]);

		return result;
	}
}
