package main.objectoriented;

import main.INetwork;
import main.Util;
import main.afunctions.ActivationFunction;
import main.afunctions.OutputFunction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.function.BiFunction;

public class JNetwork implements INetwork {
	private JNeuron[][] layers;
	private int inputSize;

	public JNetwork(BufferedReader reader) {
		try {
			fromBuffer(reader);
		} catch(IOException e) {
			e.printStackTrace();
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
		this(OutputFunction.DEFAULT_FUNCTION, numLayerUnits);
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
		this(OutputFunction.DEFAULT_FUNCTION, weights, biases);
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
	private JNetwork(OutputFunction function, int... numLayerUnits) {
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
	public JNetwork(OutputFunction function, double[][][] weights, double[][] biases) {
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
	private JNetwork(OutputFunction[][] functions, int... numLayerUnits) {
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
	public JNetwork(OutputFunction[][] functions, double[][][] weights, double[][] biases) {
		init(weightsToNumbers(weights), (i, j) -> new JNeuron(weights[i][j], biases[i][j], functions[i][j]));
	}

	private void init(int[] numLayerUnits, BiFunction<Integer, Integer, JNeuron> func) {
		if(numLayerUnits.length < 2)
			throw  new IllegalArgumentException("Network need at least an input layer and an output layer (at least two integer arguments required)");

		inputSize = numLayerUnits[0];
		layers = new JNeuron[numLayerUnits.length - 1][];

		for(int i = 0; i < layers.length; i++) {
			layers[i] = new JNeuron[numLayerUnits[i+1]];

			for(int j = 0; j < numLayerUnits[i+1]; j++) {
				layers[i][j] = func.apply(i, j);
				if(i > 0) layers[i][j].prevLayer = layers[i-1]; //initialize prevLayers
			}
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

		double[][] results = forwardPropagation(input, new double[layers.length + 1][]);
		return results[results.length - 1];
	}

	@Override
	public void train(double[][] input, double[][] labels, int repetitions, double learnRate){
		double[][] results = new double[layers.length + 1][];

		{ // Exception check
			for(double[] in : input)
				if(in.length != inputSize)
					throw new IllegalArgumentException("invalid input vector dimension");

			int outputLength = layers[layers.length - 1].length;
			for(double[] lab : labels)
				if(lab.length != outputLength)
					throw new IllegalArgumentException("invalid label vector dimension (output: " + outputLength + ", label: " + lab.length);
		}

		System.out.println("Start training");
		long start = System.currentTimeMillis();

		double costOld = 0d;
		for(int i = 0; i < repetitions; i++) {
			double cost = 0d;
			int correct = 0;
			System.out.println("Repetition " + (i + 1));
			for(int j = 0; j < input.length; j++) {
				forwardPropagation(input[j], results);
				cost += cost(results[results.length - 1], labels[j]);
				correct += correct(results[results.length - 1], labels[j]) ? 1 : 0;
				//backpropagationTest(input[j], labels[j], learnRate);
				backpropagation(results, labels[j], learnRate);
				//System.out.println(Arrays.toString(labels[j]) + ", " + Arrays.toString(results[results.length - 1]));
				//System.out.printf("Expected: %d, Actual: %d \n", Util.argmax(labels[j]), Util.argmax(results[results.length - 1]));
			}
			//System.out.println(this);
			System.out.printf("Cost: %f\nCost difference: %f\nCorrect: %f%% \n\n", cost, cost - costOld, correct/ (double) input.length * 100);
			costOld = cost;
		}

		System.out.println("Finished training in " + (System.currentTimeMillis() - start) + "ms");
	}

	private double[][] forwardPropagation(double[] input, double[][] results) {
		for(int i = 1; i < results.length; i++)          // Initializes second array of results.
			results[i] = new double[layers[i-1].length];

		results[0] = input;

		for(int i = 0; i < layers.length; i++)
			for(int j = 0; j < layers[i].length; j++)
				results[i + 1][j] = layers[i][j].fire(results[i]);

		return results;
	}

	private void backpropagationTest(double[] input, double[] target, double learnRate) {
		checkTrainable();
		JNeuron[] outLayer = layers[layers.length - 1];

		for(int i = 0; i < outLayer.length; i++)
			 outLayer[i].backpropagationTest((outLayer[i].a - target[i]), learnRate, input);
	}

	public void backpropagation(double[][] results, double[] target, double learnRate) {
		checkTrainable();
		int length = layers.length;
		LinkedList<double[]> list = new LinkedList<>();
		list.add(new double[layers[layers.length - 1].length]);

		for(int i = 0; i < list.get(0).length; i++)
			list.get(0)[i] = 2 * (results[results.length - 1][i] - target[i]);

		for(int i = length - 1; i >= 0; i--) {
			list.add(new double[i == 0 ? inputSize : layers[i-1].length]);
			for(int j = 0; j < layers[i].length; j++)
				Util.addToVec1(list.get(1), layers[i][j].backpropagation(learnRate, list.get(0)[j], results[i]));

			//Util.mulToVec(1.0/layers[i].length, list.get(1));
			list.remove(0);
		}
	}

//	private void backpropagationTest(double[][] results, double[] label, double learnRate) {
//		double[] outResults = results[results.length - 1];
//		double[][] deltas = new double[layers.length][];
//		deltas[deltas.length - 1] = new double[outResults.length];
//
//		for(int i = 0; i < outResults.length; i++)
//			deltas[deltas.length - 1][i] = 2 * (outResults[i] - label[i]);
//
//		for(int i = layers.length - 1; i >= 0; i--) { // Iterates over all layers without the output layer
//			if(i != 0) deltas[i-1] = new double[layers[i - 1].length];
//
//			for(int j = 0; j < results[i].length; j++) {
//				double[] del = layers[i][j].backpropagation(results[i][j], deltas[i][j], learnRate);
//
//				if(i != 0) deltas[i-1] = Util.add(deltas[i-1], del);
//			}
//
//			if(i != 0) deltas[i-1] = Util.mul(1d/deltas[i-1].length, deltas[i-1]);
//		}
//	}

//	private void backpropagation(double[][] results, double[] label, double learnRate) {
//		double[] delta = new double[Util.maxLength(results)];
//			for(int i = 0; i < label.length; i++)
//				delta[i] = 2 *  (results[results.length - 1][i] - label[i]);
//
//		backpropagation(delta, results.length - 1, results, learnRate);
//	}
//
//	private void backpropagation(double[] delta, int index, double[][] results, double learnRate) {
//		if(index < 0) return;
//
//		for(int i = 0; i < layers[index].length; i++) {
//			double df = layers[index][i].function.derivative(layers[index][i].z);
//			double w = Arrays.stream(layers[index][i].weights).sum(); // Maybe wrong idea
//			double deltaI = delta[i];
//
//			for(int j = 0; j < layers[index][i].weights.length; j++)
//				layers[index][i].weights[j] += -learnRate * deltaI * df * results[index][i];
//
//			layers[index][i].bias += -learnRate * deltaI * df;
//			delta[i] = deltaI * df * w;
//		}
//
//		backpropagation(delta, index - 1, results, learnRate);
//	}

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

	private void checkTrainable() {
		for(JNeuron[] layer: layers)
			for(JNeuron unit: layer)
				if(!(unit.getFunction() instanceof ActivationFunction))
					throw new IllegalStateException("Network is not trainable");
	}

	@Override
	public String toString() {
		String string = "";

		for(int i = 0; i < inputSize; i++)
			string += "O   ";

		string += "B";
		for(int i = 0; i < layers.length; i++) {
			string += "\n   |   ";
			for(int j = 0; j < layers[i].length; j++) {
				if(j != 0) string += ", \n   |   ";
				string += layers[i][j].toString();
			}

			string += "\n   |   ";

			for(int j = 0; j < layers[i].length; j++) {
				string += (j != 0 ? ", " : "") + layers[i][j].getFunction();
			}

			string += "\n";

			for(int j = 0; j < layers[i].length; j++)
				string += "O   ";

			string += "B";
		}

		return "Network{\n" + string + "\n}";
	}

	@Override
	public void fromBuffer(BufferedReader reader) throws IOException {
		String[] strings = reader.readLine().split(", ");
		if(strings.length < 2) throw new IOException("Invalid file");

		inputSize = Integer.parseInt(strings[0]);

		layers = new JNeuron[strings.length - 1][];
		for(int i = 1; i < strings.length; i++)
			layers[i-1] = new JNeuron[Integer.parseInt(strings[i])];

		int layer = 0, unit = 0;
		for(String str; (str = reader.readLine()) != null;) {
			if(str.charAt(0) == '%') {
				layer++;
				unit = 0;
			} else {
				layers[layer][unit] = JNeuron.fromBuffer(str);
				unit++;
			}
		}

	}

	@Override
	public void toBuffer(BufferedWriter writer) throws IOException {
		writer.append(String.valueOf(inputSize));

		for(int i = 0; i < layers.length; i++)
			writer.append(", ").append(String.valueOf(layers[i].length));

		for(int i = 0; i < layers.length; i++) {
			if(i != 0) writer.append("\n%");
			for(int j = 0; j < layers[i].length; j++) {
				writer.append("\n");
				layers[i][j].toBuffer(writer);
			}
		}
	}
}
