package main.objectoriented;

import main.INetwork;
import main.Util;
import main.afunctions.AFunction;
import main.afunctions.SigmoidFunction;

import java.util.Arrays;

public class JNetwork implements INetwork {
	private final AFunction function;
	private final JNeuron[][] layers;
	private final int inputSize;

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
	public JNetwork(double[][][] weights) {
		this(new SigmoidFunction(), weights);
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
	public JNetwork(AFunction function, double[][][] weights) {
		this.function = function;

		inputSize = weights[0][0].length;
		layers = new JNeuron[weights.length][];

		for(int i = 0; i < layers.length; i++) {
			layers[i] = new JNeuron[weights[i].length];
			int prevSize = i > 0 ? weights[i-1].length: inputSize;

			for(int j = 0; j < weights[i].length; j++) {
				if(weights[i][j].length != prevSize)
					throw new IllegalArgumentException("weights are invalid");

				layers[i][j] = new JNeuron(weights[i][j]);
			}
		}
	}

	/**
	 * Creates a neural network which works with {@link JNeuron}s.
	 * @param numLayerUnits numLayerUnits.length <=> Number of layers.
	 *                      numLayerUnits[i] <=> Number of units in layer i.
	 *                      First layer <=> input layer
	 *                      Last layer <=> output layer
	 * @throws IllegalArgumentException Whether the argument's length is less than two, because the network needs at least an input layer and an output layer.
	 */
	public JNetwork(int... numLayerUnits) {
		this(new SigmoidFunction(), numLayerUnits);
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
	private JNetwork(AFunction function, int... numLayerUnits) {
		if(numLayerUnits.length < 2)
			throw  new IllegalArgumentException("Network need at least an input layer and an output layer (at least two integer arguments required)");

		this.function = function;
		inputSize = numLayerUnits[0];
		layers = new JNeuron[numLayerUnits.length - 1][];

		for(int i = 0; i < layers.length; i++) {
			layers[i] = new JNeuron[numLayerUnits[i+1]];

			for(int j = 0; j < numLayerUnits[i+1]; j++)
				layers[i][j] = new JNeuron(Util.random(numLayerUnits[i]));
		}
	}

	@Override
	public double[] compute(double[] input) {
		double[][] results = new double[layers.length][];

		forwardPropagation(input, results);

		return results[results.length - 1];
	}

	@Override
	public void train(double[][] input, double[][] labels, int repetitions, double learnRate){
		double[][] results = new double[layers.length][];

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
				backpropagation(results, labels[j], learnRate);
			}
			System.out.printf("Cost: %f\nCorrect: %f%% \n\n", cost, correct/ (double) input.length * 100);
		}

		System.out.println("Finished training in " + (System.currentTimeMillis() - start) + "ms");
	}

	private void forwardPropagation(double[] input, double[][] results) {
		for(int i = 0; i < layers.length; i++)          // Initializes second layer of hidden matrix.
			results[i] = new double[layers[i].length];

		for(int i = 0; i < layers.length; i++)
			for(int j = 0; j < layers[i].length; j++)
				results[i][j] = layers[i][j].fire(i == 0 ? input : results[i - 1], function);
	}

	private void backpropagation(double[][] results, double[] label, double learnRate) {
		double[] delta = new double[Util.maxLength(results)];
		for(int i = 0; i < label.length; i++)
			delta[i] = 2 * (results[results.length - 1][i] - label[i]);

		backpropagation(delta, results.length - 1, layers, results, learnRate);
	}

	private void backpropagation(double[] delta, int index, JNeuron[][] layers, double[][] results, double learnRate) {
		if(index < 0) return;

		for(int i = 0; i < layers[index].length; i++) {
			double df = function.derivative(layers[index][i].z);
			double w = Arrays.stream(layers[index][i].weights).sum();
			double deltaI = delta[i];

			for(int j = 0; j < layers[index][i].weights.length; j++)
				layers[index][i].weights[j] += -learnRate * deltaI * df * results[index][i] ;

			layers[index][i].bias += -learnRate * deltaI * df;
			delta[i] = deltaI * df * w;                                 // FIXME Lernprozess funktioniert nur ohne hidden layers. Delta?
		}

		backpropagation(delta, index - 1, layers, results, learnRate);
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

	private double[][][] weights(int[] numLayerUnits) {

		double[][][] weights = new double[numLayerUnits.length - 1][][];
		for(int i = 0; i < weights.length; i++) {
			weights[i] = new double[numLayerUnits[i + 1]][];

			for(int j = 0; j < weights[i].length; j++)
				weights[i][j] = Util.random(numLayerUnits[i]);
		}

		return weights;
	}
}
