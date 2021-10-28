package main.seminar;

import main.Util;

import java.util.Arrays;

/**
 * Class for creating objects of type {@link Neuron}. {@link Neuron}s are used by neural {@link Network}s for saving and processing data.
 * @author aschal2s, azarkh2s, llegge2s, szhang2s
 */
public class Neuron {
	private double[] weights;
	private double bias, z;

	/**
	 * Constructor for {@link Neuron}.
	 * @param weights Weighting of units in the previous layer for computing a value for this unit.
	 */
	public Neuron(double[] weights, double bias) {
		this.weights = weights;
		this.bias = bias;
	}

	/**
	 * Fires a value for this unit depending on the given input vector, weights and bias via sigma rule.
	 * @param input Input vector which is needed for computing a value.
	 * @return The value which is computed by this method.
	 */
	public double fire(double[] input) {
		 return fireSGN(input);
	}

	/**
	 * Fires a value for this unit depending on the given input vector, weights and bias via sigma rule. This computation is based on a sign function.
	 * @param input Input vector which is needed for computing a value.
	 * @return The value which is computed by this method.
	 */
	private int fireSGN(double[] input) {
		 double sum = 0;
		 for(int i = 0; i < (Math.min(input.length, weights.length)); i++)
			 sum += weights[i] * input[i];

		 z = sum;
		 return Util.sgn(sum, bias);
	}

	/**
	 * Fires a value for this unit depending on the given input vector, weights and bias via sigma rule. This computation is based on a sigmoid (or logistic) function.
	 * @param input Input vector which is needed for computing a value.
	 * @return The value which is computed by this method.
	 */
	private double fireSigmoid(double[] input) {
		double sum = 0;
		for(int i = 0; i < (Math.min(input.length, weights.length)); i++)
			sum += weights[i] * input[i];

		sum += bias;
		z = sum;
		return Util.sigmoid(sum);
	}

	/**
	 * Fires a value for this unit depending on the given input vector, weights and bias via sigma rule. This computation is based on a semi-linear function.
	 * @param input Input vector which is needed for computing a value.
	 * @return The value which is computed by this method.
	 */
	private double fireSemiLinear(double[] input) {
		double sum = 0;
		for(int i = 0; i < (Math.min(input.length, weights.length)); i++)
			sum += weights[i] * input[i];

		sum += bias;
		z = sum;
		return Util.semiLinear(sum);
	}

	public double[] backpropagation(double learnRate, double delta, double result, double[] prevResults) { //TODO result brauchen wir nicht
		for(int i = 0; i < weights.length; i++) {
			weights[i] += -learnRate * prevResults[i] * delta;
		}
		bias += -learnRate * delta;
		double[] deltas = new double[weights.length];
		for(int i = 0; i < deltas.length; i++) {
			deltas[i] = weights[i] * Util.dSigmoid(z) * delta;
		}
		return deltas;
	}


	@Override
	public String toString() {
		return "Neuron{" +
				"weights=" + Arrays.toString(weights) +
				", bias=" + bias +
				'}';
	}
}
