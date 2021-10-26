package main.objectoriented;

import main.INetwork;
import main.Util;
import main.afunctions.ActivationFunction;

import java.util.Arrays;
import java.util.function.BiFunction;

public class JNetwork implements INetwork {
	private JNeuron[][] layers;
	private int inputSize;

	/**
	 * Creates a neural network which works with {@link JNeuron}s.
	 * @param numLayerUnits numLayerUnits.length <=> Number of layers.
	 *                      numLayerUnits[i] <=> Number of units in layer i.
	 *                      First layer <=> input layer
	 *                      Last layer <=> output layer
	 * @throws IllegalArgumentException Whether the argument's length is less than two, because the network needs at least an input layer and an output layer.
	 */
	public JNetwork(int... numLayerUnits) {
		this(ActivationFunction.DEFAULT_FUNCTION, numLayerUnits);
	}

	/**
	 * Creates a neural network which works with {@link JNeuron}s.
	 * @param weights weights.length <=> Number of layers (without input layer).
	 *                weights[i].length <=> Number of Units in Layer i.
	 *                weights[i][j] <=> Incoming weights of Unit j of Layer i.
	 *                weights[0][0] <=> size of each input vector.
	 *                Condition 1: weights[i][j].length == weights[i-1].length.
	 *                Condition 2: weights[0][0] == weights[0][j], for every j in range.
	 * @throws  IllegalArgumentException Whether one of the condition of argument weights is false.
	 */
	public JNetwork(double[][][] weights, double[][] biases) {
		this(ActivationFunction.DEFAULT_FUNCTION, weights, biases);
	}

	/**
	 * Creates a neural network which works with {@link JNeuron}s.
	 * @param function activation or output function which will be used in the network.
	 * @param numLayerUnits numLayerUnits.length <=> Number of layers.
	 *                      numLayerUnits[i] <=> Number of units in layer i.
	 *                      First layer <=> input layer
	 *                      Last layer <=> output layer
	 * @throws IllegalArgumentException Whether the argument's length is less than two, because the network needs at least an input layer and an output layer.
	 */
	private JNetwork(ActivationFunction function, int... numLayerUnits) {
		init(numLayerUnits, (i, j) -> new JNeuron(numLayerUnits[i], function));
	}

	/**
	 * Creates a neural network which works with {@link JNeuron}s.
	 * @param function activation or output function which will be used in the network.
	 * @param weights weights.length <=> Number of layers (without input layer).
	 *                weights[i].length <=> Number of Units in Layer i.
	 *                weights[i][j] <=> Incoming weights of Unit j of Layer i.
	 *                weights[0][0] <=> size of each input vector.
	 *                Condition 1: weights[i][j].length == weights[i-1].length.
	 *                Condition 2: weights[0][0] == weights[0][j], for every j in range.
	 * @throws  IllegalArgumentException Whether one of the condition of argument weights is false.
	 */
	public JNetwork(ActivationFunction function, double[][][] weights, double[][] biases) {
		init(weightsToNumbers(weights), (i, j) -> new JNeuron(weights[i][j], biases[i][j], function));
	}

	/**
	 * Creates a neural network which works with {@link JNeuron}s.
	 * @param functions activation or output function which will be used in the network.
	 * @param numLayerUnits numLayerUnits.length <=> Number of layers.
	 *                      numLayerUnits[i] <=> Number of units in layer i.
	 *                      First layer <=> input layer
	 *                      Last layer <=> output layer
	 * @throws IllegalArgumentException Whether the argument's length is less than two, because the network needs at least an input layer and an output layer.
	 */
	private JNetwork(ActivationFunction[][] functions, int... numLayerUnits) {
		init(numLayerUnits, (i, j) -> new JNeuron(numLayerUnits[i], functions[i][j]));
	}

	/**
	 * Creates a neural network which works with {@link JNeuron}s.
	 * @param functions activation or output function which will be used in the network.
	 * @param weights weights.length <=> Number of layers (without input layer).
	 *                weights[i].length <=> Number of Units in Layer i.
	 *                weights[i][j] <=> Incoming weights of Unit j of Layer i.
	 *                weights[0][0] <=> size of each input vector.
	 *                Condition 1: weights[i][j].length == weights[i-1].length.
	 *                Condition 2: weights[0][0] == weights[0][j], for every j in range.
	 * @throws  IllegalArgumentException Whether one of the condition of argument weights is false.
	 */
	public JNetwork(ActivationFunction[][] functions, double[][][] weights, double[][] biases) {
		init(weightsToNumbers(weights), (i, j) -> new JNeuron(weights[i][j], biases[i][j], functions[i][j]));
	}

	private void init(int[] numLayerUnits, BiFunction<Integer, Integer, JNeuron> func) {
		if(numLayerUnits.length < 2)
			throw  new IllegalArgumentException("Network need at least an input layer and an output layer (at least two integer arguments required)");

		inputSize = numLayerUnits[0];
		layers = new JNeuron[numLayerUnits.length - 1][];

		for(int i = 0; i < layers.length; i++) {
			layers[i] = new JNeuron[numLayerUnits[i]];

			for(int j = 0; j < numLayerUnits[i]; j++)
				layers[i][j] = func.apply(i, j);
		}
	}

	private int[] weightsToNumbers(double[][][] weights) {
		int[] result = new int[weights.length + 1];
		result[0] = weights[0][0].length;

		for(int i = 1; i < result.length; i++)
			result[i] = weights[i-1].length;

		return result;
	}

	@Override
	public double[] compute(double[] input) {
		if(input.length != inputSize) // Exception check
			throw new IllegalArgumentException("invalid input vector dimension");

		double[][] results = forwardPropagation(input, new double[layers.length][]);
		return results[results.length - 1];
	}

	@Override
	public void train(double[][] input, double[][] labels, int repetitions, double learnRate){
		double[][] results = new double[layers.length][];

		{ // Exception check
			for(double[] in : input)
				if(in.length != inputSize)
					throw new IllegalArgumentException("invalid input vector dimension");

			int outputLength = layers[layers.length - 1].length;
			for(double[] lab : labels)
				if(lab.length != outputLength)
					throw new IllegalArgumentException("invalid label vector dimension");
		}

		System.out.println("Start training");
		long start = System.currentTimeMillis();

		for(int i = 0; i < repetitions; i++) {
			double cost = 0d;
			int correct = 0;
			System.out.println("Repetition " + (i + 1));
			for(int j = 0; j < input.length; j++) {
				forwardPropagation(input[j], results);
				cost += cost(results[results.length - 1], labels[j]);
				correct += correct(results[results.length - 1], labels[j]) ? 1 : 0;
				backpropagationTest(results, labels[j], learnRate);
			}
			System.out.printf("Cost: %f\nCorrect: %f%% \n\n", cost, correct/ (double) input.length * 100);
		}

		System.out.println("Finished training in " + (System.currentTimeMillis() - start) + "ms");
	}

	private double[][] forwardPropagation(double[] input, double[][] results) {
		for(int i = 0; i < layers.length; i++)          // Initializes second layer of hidden matrix.
			results[i] = new double[layers[i].length];

		for(int i = 0; i < layers.length; i++)
			for(int j = 0; j < layers[i].length; j++)
				results[i][j] = layers[i][j].fire(i == 0 ? input : results[i - 1]);

		return results;
	}

	private void backpropagationTest(double[][] results, double[] label, double learnRate) {
		double[] outResults = results[results.length - 1];
		double[][] deltas = new double[layers.length][];
		deltas[deltas.length - 1] = new double[outResults.length];

		for(int i = 0; i < outResults.length; i++)
			deltas[deltas.length - 1][i] = 2 * (outResults[i] - label[i]);

		for(int i = layers.length - 1; i >= 0; i--) { // Iterates over all layers without the output layer
			if(i != 0) deltas[i - 1] = new double[layers[i - 1].length];

			for(int j = 0; j < results[i].length; j++) {
				double[] del = layers[i][j].backpropagation(results[i][j], deltas[i][j], learnRate);

				if(i != 0) Util.add(deltas[i - 1], del);
			}

			if(i != 0) deltas[i-1] = Util.mul(1d/deltas[i-1].length, deltas[i-1]);
		}
	}

	private void backpropagation(double[][] results, double[] label, double learnRate) {
		double[] delta = new double[Util.maxLength(results)];
			for(int i = 0; i < label.length; i++)
				delta[i] = 2 *  (results[results.length - 1][i] - label[i]);

		backpropagation(delta, results.length - 1, results, learnRate);
	}

	private void backpropagation(double[] delta, int index, double[][] results, double learnRate) {
		if(index < 0) return;

		for(int i = 0; i < layers[index].length; i++) {
			double df = layers[index][i].function.derivative(layers[index][i].z);
			double w = Arrays.stream(layers[index][i].weights).sum(); // Maybe wrong idea
			double deltaI = delta[i];

			for(int j = 0; j < layers[index][i].weights.length; j++)
				layers[index][i].weights[j] += -learnRate * deltaI * df * results[index][i];

			layers[index][i].bias += -learnRate * deltaI * df;
			delta[i] = deltaI * df * w;                                 // FIXME Lernprozess funktioniert nur ohne hidden layers. Delta?
		}

		backpropagation(delta, index - 1, results, learnRate);
	}

	private double cost(double[] output, double[] label) {
		double sum = 0d;
		for(int i = 0; i < output.length; i++) {
			double diff = output[i] - label[i];
			sum += diff * diff;
		}

		return sum;
	}

	private boolean correct(double[] output, double[] label) {
		return Util.argmax(output) == Util.argmax(label);
	}
}
