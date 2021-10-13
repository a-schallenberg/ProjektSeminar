package main;

import java.util.function.Function;

public enum AFunctions {
	IDENTITY(x -> x, x -> 1d),
	SEMI_LINEAR(x -> (x <= -1 ? -1d : (x >= 1 ? 1d : x)), x -> (x < 1 && x > -1 ? 1d : 0d)),
	SIGN(x -> (x < 0 ? -1d : 1d), x -> 0d),                                                     //SIGN function (offset has to be subtracted from sum)
	SIGMOID(x -> (1 / (1 + Math.exp(-x))), x -> x * (1 - x)),                                   // derivative inputs have to be between 0 and 1
	TANH(Math::tanh, x -> 1 - Math.pow(Math.tanh(x), 2));

	private Function<Double, Double> function, derivative;

	AFunctions(Function<Double, Double> function, Function<Double, Double> derivative) {
		this.function = function;
		this.derivative = derivative;
	}

	public double function(double d) {
		return function.apply(d);
	}

	public double[] function(double[] a) {
		return array(a, function);
	}

	public double derivative(double d) {
		return derivative.apply(d);
	}

	public double[] derivative(double[] a) {
		return array(a, derivative);
	}

	private static double[] array(double[] a, Function<Double, Double> func) {
		double[] result = new double[a.length];

		for(int i = 0; i < result.length; i++)
			result[i] = func.apply(a[i]);

		return result;
	}
}
