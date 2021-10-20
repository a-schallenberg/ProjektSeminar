package main.seminar;

import main.Util;

/**
 * Class for creating objects of type {@link Neuron}. {@link Neuron}s are used by neural {@link Network}s for saving and processing data.
 * @author aschal2s, azarkh2s, llegge2s, szhang2s
 */
public class Neuron {
	private double[] weights;
	private double bias = 1;
	private Neuron[] sendToArray;

	/**
	 * Constructor for {@link Neuron}.
	 * @param sendToArray All units of the next layer to which the data will be sent while processing data.
	 * @param weights Weighting of units in the previous layer for computing a value for this unit.
	 */
	public Neuron(Neuron[] sendToArray, double[] weights) {
		this.sendToArray = sendToArray;
		this.weights = weights;
	}

	/**
	 * Fires a value for this unit depending on the given input vector, weights and bias.
	 * @param input Input vector which is needed for computing a value.
	 * @return The value which is computed by this method.
	 */
	public double fire(double[] input) {
//		if(weights == null) { // initialize weights !!!
//			weights = new double[input.length];
//			Arrays.setAll(weights, (w) -> w = 1);
//		}

		 return fireSigmoid(input);
	}

	/**
	 * Fires a value for this unit depending on the given input vector, weights and bias. This computation is based on a sign function.
	 * @param input Input vector which is needed for computing a value.
	 * @return The value which is computed by this method.
	 */
	private int fireSGN(double[] input) {
		 double sum = 0;
		 for(int i = 0; i < (Math.min(input.length, weights.length)); i++)
			 sum += weights[i] * input[i];

		 return bias <= sum ? 1 : 0;
	}

	/**
	 * Fires a value for this unit depending on the given input vector, weights and bias. This computation is based on a sigmoid (or logistic) function.
	 * @param input Input vector which is needed for computing a value.
	 * @return The value which is computed by this method.
	 */
	private double fireSigmoid(double[] input) {
		double sum = 0;
		for(int i = 0; i < (Math.min(input.length, weights.length)); i++)
			sum += weights[i] * input[i];

		sum += bias;
		return Util.sigmoid(sum);
	}

	/**
	 * Fires a value for this unit depending on the given input vector, weights and bias. This computation is based on a semi-linear function.
	 * @param input Input vector which is needed for computing a value.
	 * @return The value which is computed by this method.
	 */
	private double fireSemiLinear(double[] input) {
		double sum = 0;
		for(int i = 0; i < (Math.min(input.length, weights.length)); i++)
			sum += weights[i] * input[i];

		sum += bias;
		return Util.semiLinear(sum);
	}
}
