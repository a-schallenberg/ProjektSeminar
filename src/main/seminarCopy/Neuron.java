package main.seminarCopy;

import main.afunctions.ActivationFunction;
import main.util.Util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Class for creating objects of type {@link Neuron}. {@link Neuron}s are used by neural {@link Network}s for saving and processing data.
 * @author aschal2s, azarkh2s, llegge2s, szhang2s
 */
public class Neuron {
	private double[] weights;
	private double bias, z;
	private ActivationFunction function;

	private Neuron(){}

	public Neuron(int weightDim, ActivationFunction function) {
		this(Util.random(weightDim), 0, function);
	}

	/**
	 * Constructor for {@link Neuron}.
	 * @param weights Weighting of units in the previous layer for computing a value for this unit.
	 */
	public Neuron(double[] weights, double bias, ActivationFunction function) {
		this.weights = weights;
		this.bias = bias;
		this.function = function;
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
		return function.function(sum);
	}

	public double[] backpropagation(double learnRate, double delta, double[] prevResults) {
		for(int i = 0; i < weights.length; i++)
			weights[i] += -learnRate * prevResults[i] * delta;

		bias += -learnRate * delta;
		double[] deltas = new double[weights.length];
		for(int i = 0; i < deltas.length; i++)
			deltas[i] = weights[i] * function.derivative(z) * delta;

		return deltas;
	}


	@Override
	public String toString() {
		return "Neuron{" +
				"weights=" + Arrays.toString(weights) +
				", bias=" + bias +
				", function=" + function.toString() +
				'}';
	}

	double[] getWeights() {
		return  weights;
	}

	double getBias() {
		return bias;
	}

	ActivationFunction getFunction() {
		return function;
	}

	void save(BufferedWriter writer) throws IOException {
		writer.append("\n" + bias);

		writer.append(" " + weights.length);
		for(double weight: weights)
			writer.append(" " + weight);

	}

	static Neuron load(Scanner scanner) {
		Neuron neuron = new Neuron();

		neuron.bias = scanner.nextDouble();

		neuron.weights = new double[scanner.nextInt()];
		for(int i = 0; i < neuron.weights.length; i++)
			neuron.weights[i] = scanner.nextDouble();

		return neuron;
	}
}
