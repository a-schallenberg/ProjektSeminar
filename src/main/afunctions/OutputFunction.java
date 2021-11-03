package main.afunctions;

import java.io.IOException;
import java.util.function.Function;

public interface OutputFunction {
	OutputFunction DEFAULT_FUNCTION = new SigmoidFunction();

	double function(double x);

	default double[] function(double[] d) {
		return array(d, this::function);
	}

	static double[] array(double[] a, Function<Double, Double> func) {
		double[] result = new double[a.length];

		for(int i = 0; i < result.length; i++)
			result[i] = func.apply(a[i]);

		return result;
	}

	static OutputFunction fromString(String string) throws IOException {
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
				throw new IOException("Cannot read output function: " + string);
		}
	}
}
