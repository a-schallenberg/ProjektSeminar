package main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Class for creating objects of type {@link Neuron}. {@link Neuron}s are used by neural {@link Network}s for saving and processing data.
 * @author aschal2s, azarkh2s, llegge2s, szhang2s
 */
public class Neuron {
	private double[] weights;
	private double bias, z;
	private Function<Double, Double> function, derivative;

	private Neuron(){}

	public Neuron(int weightDim, Function<Double, Double> function, Function<Double, Double> derivative) {
		this(Util.random(weightDim), 0, function, derivative);
	}

	/**
	 * Constructor for {@link Neuron}.
	 * @param weights Weighting of units in the previous layer for computing a value for this unit.
	 */
	public Neuron(double[] weights, double bias, Function<Double, Double> function, Function<Double, Double> derivative) {
		this.weights = weights;
		this.bias = bias;
		this.function = function;
		this.derivative = derivative;
	}

	/**
	 * Fires a value for this unit depending on the given input vector, weights and bias via sigma rule.
	 * @param input Input vector which is needed for computing a value.
	 * @return The value which is computed by this method.
	 */
	public double fire(double[] input) {
		double sum = 0;
		for(int i = 0; i < (Math.min(input.length, weights.length)); i++)
			sum += weights[i] * input[i];

		sum += bias;
		z = sum;
		return function.apply(sum);
	}

	public double[] backpropagation(double learnRate, double delta, double[] prevResults) {
		for(int i = 0; i < weights.length; i++)
			weights[i] += -learnRate * prevResults[i] * derivative.apply(z) * delta;

		bias += -learnRate * delta * derivative.apply(z);
		double[] deltas = new double[weights.length];
		for(int i = 0; i < deltas.length; i++)
			deltas[i] = weights[i] * derivative.apply(z) * delta;

		return deltas;
	}

	@Override
	public String toString() {
		return "Neuron{" +
				"weights=" + Arrays.toString(weights) +
				", bias=" + bias +
				'}';
	}

	double[] getWeights() {
		return weights;
	}

	double getBias() {
		return bias;
	}
}
