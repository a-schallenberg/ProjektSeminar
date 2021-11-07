package main.afunctions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Function;

/**
 * An {@link ActivationFunction} is used by a neural network for calculating the activation of each neuron.
 * There are multiple functions and function templates:
 * {@link IdentityFunction}, {@link LogisticFunction}, {@link SemiLinearFunction}, {@link SigmoidFunction}, {@link SignFunction} and {@link TangensHyperbolicus}.
 * To implement a custom {@link ActivationFunction} implement this interface. Every {@link ActivationFunction} needs a Constructor without parameters for loading it from a file.
 * Otherwise, there will be printed a Stacktrace while loading the function and the DEFAULT_FUNCTION will be loaded.
 */
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

	void toBuffer(BufferedWriter writer) throws IOException;

	void fromBuffer(Scanner scanner) throws IOException;

	static double[] array(double[] a, Function<Double, Double> func) {
		double[] result = new double[a.length];

		for(int i = 0; i < result.length; i++)
			result[i] = func.apply(a[i]);

		return result;
	}
}
