package main.afunctions;

import java.io.IOException;
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


	String toString();

	static ActivationFunction fromString(String string) throws IOException {
		switch(string.split("\\(")[0]) {
			case IdentityFunction.NAME:
				return IdentityFunction.fromString(string);
			case SemiLinearFunction.NAME:
				return SemiLinearFunction.fromString(string);
			case SigmoidFunction.NAME:
				return SigmoidFunction.fromString(string);
			case SignFunction.NAME:
				return SignFunction.fromString(string);
			case TangensHyperbolicus.NAME:
				return TangensHyperbolicus.fromString(string);
			default:
				throw new IOException("Cannot read activation function: " + string);
		}
	}
}
