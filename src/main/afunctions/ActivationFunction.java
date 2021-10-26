package main.afunctions;

import java.util.function.Function;

public interface ActivationFunction {
	ActivationFunction DEFAULT_FUNCTION = new SigmoidFunction();

	double function(double x);

	default double[] function(double[] d) {
		return array(d, this::function);
	}

	double derivative(double x);

	default double[] derivative(double[] d) {
		return array(d, this::derivative);
	}

	private static double[] array(double[] a, Function<Double, Double> func) {
		double[] result = new double[a.length];

		for(int i = 0; i < result.length; i++)
			result[i] = func.apply(a[i]);

		return result;
	}
}
