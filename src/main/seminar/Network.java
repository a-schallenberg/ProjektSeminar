package main.seminar;

import main.Util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * The class {@link Network} allows creating objects from this type. It represents a neural network and works with {@link Neuron}s.
 * @author aschal2s, azarkh2s, llegge2s, szhang2s
 */
public class Network {
	private int inLayerLength;
	private Neuron[] outputLayer;
	private Neuron[][] hiddenLayers;

	private Network(){}

	/**
	 * Constructor of {@link Network}.
	 * @param numInUnit Number of units of the input layer.
	 * @param numOutUnit Number of units of the output layer
	 * @param weights Weights for the neural network.
	 * @param biases Biases for the neural network.
	 * @param numHidUnit Numbers of units of the hidden layers. The number of arguments starting with the fourth argument represents the number of hidden layers. Every integer gives the number of units in the specific layer.
	 * @throws IllegalArgumentException Whether the weights' matrix' size and the number of layers do not match.
	 */
	public Network(int numInUnit, int numOutUnit, double[][][] weights, double[][] biases, int... numHidUnit) { // int... == int[] mit dem Unterschied: new Network(1, 1, w, 3, 5) statt new Network(1, 1, w, new int[]{3, 5})
		if(numHidUnit.length != weights.length - 1) {throw new IllegalArgumentException("Illegal weight matrices");}

		inLayerLength = numInUnit;
		outputLayer = new Neuron[numOutUnit];
		hiddenLayers = new Neuron[numHidUnit.length][];

		// Try and catch for throwing IllegalArgumentException instead of ArrayIndexOutOfBoundsException

		// Initialize hidden layers
		for(int i = 0; i < hiddenLayers.length; i++) {
			hiddenLayers[i] = new Neuron[numHidUnit[i]];

			for(int j = 0; j < numHidUnit[i]; j++)
				hiddenLayers[i][j] = new Neuron(weights[i][j], biases[i][j]);
		}

		// Initialize output layer
		for(int i = 0; i < numOutUnit; i++) {
			outputLayer[i] = new Neuron(weights[weights.length - 1][i], biases[biases.length - 1][i]);
		}
	}

	/**
	 * Constructor of {@link Network}. In this constructor the weights are random decimal numbers between -0.5 and 0.5.
	 * @param numInUnit Number of units of the input layer.
	 * @param numOutUnit Number of units of the output layer
	 * @param numHidUnit Numbers of units of the hidden layers. The number of arguments starting with the fourth argument represents the number of hidden layers. Every integer gives the number of units in the specific layer.
	 */
	public Network(int numInUnit, int numOutUnit, int... numHidUnit) {  // int... == int[] mit dem Unterschied: new Network(1, 1, 3, 5) statt new Network(1, 1, new int[]{3, 5})
		inLayerLength = numInUnit;
		outputLayer = new Neuron[numOutUnit];
		hiddenLayers = new Neuron[numHidUnit.length][];

		// Initialize hidden layers
		for(int i = 0; i < hiddenLayers.length; i++) {
			hiddenLayers[i] = new Neuron[numHidUnit[i]];

			for(int j = 0; j < numHidUnit[i]; j++)
				hiddenLayers[i][j] = new Neuron(Util.random(i == 0 ? numInUnit : numHidUnit[i - 1]), 1);
		}

		// Initialize output layer
		for(int i = 0; i < numOutUnit; i++)
			outputLayer[i] = new Neuron(Util.random(numHidUnit.length == 0 ? numInUnit : numHidUnit[numHidUnit.length - 1]), 1);
	}

	/**
	 * Computing a result with an input vector.
	 * @param input Input vector for computing.
	 * @return Result (output vector) of the neural network.
	 * @throws IllegalArgumentException Whether the input vector's size does not match the input layer size.
	 */
	public double[] compute(double[] input) {
		double[][] results = forwardPropagation(input);
		return results[results.length - 1];
	}

	public void train(double[][] input, double learnRate, double[][] y, int iterations) {
		double[][] results;

		for(int i = 0; i < iterations; i++)
			for(int j = 0; j < input.length; j++) {
				results = forwardPropagation(input[j]);
				backpropagation(learnRate, y[j], results);
			}
	}

	private double[][] forwardPropagation(double[] input) {
		if(input.length != inLayerLength) throw new IllegalArgumentException("Argument's size and input layer's size do not match");

		double[][] results = new double[hiddenLayers.length + 1][];

		// Fire all units of hidden layers
		for(int i = 0; i < hiddenLayers.length; i++) {
			int length = hiddenLayers[i].length;
			results[i] = new double[length];

			for(int j = 0; j < length; j++)
				results[i][j] = hiddenLayers[i][j].fire(j == 0 ? input : results[i - 1]);

		}

		results[results.length - 1] = new double[outputLayer.length];

		// Fire all units of output layer
		for(int i = 0; i < outputLayer.length; i++)
			results[results.length - 1][i] = outputLayer[i].fire(hiddenLayers.length == 0 ? input : results[results.length - 2]);

		return results;
	}

	private void backpropagation(double learnRate, double[] y, double[][] results) {
		int length = hiddenLayers.length; // TODO +1 ?
		LinkedList<double[]> list = new LinkedList<>();
		list.add(new double[outputLayer.length]);
		for(int i = 0; i < list.get(0).length; i++) {
			list.get(0)[i] = 2 * (results[results.length - 1][i] - y[i]);
		}
		for(int i = length; i >= 0; i--) {
			list.add(new double[i == 0 ? inLayerLength : hiddenLayers[i-1].length]);
			for(int j = 0; j < (i == length ? outputLayer.length : hiddenLayers[i].length); j++) {
				Util.addToVec1(list.get(1), (i == length ? outputLayer[j] : hiddenLayers[i][j]).backpropagation(learnRate, list.get(0)[j], results[i]));
			}
			Util.mulToVec(1.0/(i == length ? outputLayer.length : hiddenLayers[i].length), list.get(1)); // Wird diese Zeile gebraucht?
			list.remove(0);
		}
	}

	@Override
	public String toString() {
		String string = "";

		for(int i = 0; i < inLayerLength; i++)
			string += "O   ";

		string += "B";
		for(int i = 0; i < hiddenLayers.length; i++) {
			string += "\n   |   ";
			for(int j = 0; j < hiddenLayers[i].length; j++) {
				if(j != 0) string += ", \n   |   ";
				string += hiddenLayers[i][j].toString();
			}

			string += "\n   |   Sigmoid\n";

			for(int j = 0; j < hiddenLayers[i].length; j++)
				string += "O   ";

			string += "B";
		}

		string += "\n   |   ";

		for(int i = 0; i < outputLayer.length; i++) {
			if(i != 0) string += ", \n   |   ";
			string += outputLayer[i].toString();
		}

		string += "\n   |   Sigmoid\n";

		for(int i = 0; i < outputLayer.length; i++)
			string += "O   ";

		return "Network{\n" + string + "\n}";
	}

	void save(BufferedWriter writer) throws IOException {
		writer.append(inLayerLength + " " + outputLayer.length + " " + hiddenLayers.length);

		for(Neuron[] layer: hiddenLayers)
			writer.append(" " + layer.length);

		for(Neuron[] layer: hiddenLayers) {
			writer.append("\n");
			for(Neuron unit : layer)
				unit.save(writer);
		}

		writer.append("\n");
		for(Neuron unit: outputLayer)
			unit.save(writer);
	}

	static Network load(Scanner scanner) {
		Network network = new Network();

		// Initialize inLayerLength and neuron arrays
		network.inLayerLength = scanner.nextInt();
		network.outputLayer = new Neuron[scanner.nextInt()];

		network.hiddenLayers = new Neuron[scanner.nextInt()][];
		for(int i = 0; i < network.hiddenLayers.length; i++)
			network.hiddenLayers[i] = new Neuron[scanner.nextInt()];

		// Fill arrays
		for(int i = 0; i < network.hiddenLayers.length; i++)
			for(int j = 0; j < network.hiddenLayers[i].length; j++)
				network.hiddenLayers[i][j] = Neuron.load(scanner);

		for(int i = 0; i < network.outputLayer.length; i++)
			network.outputLayer[i] = Neuron.load(scanner);

		return network;
	}

	public static void main(String[] args) throws IOException {
//		double[][][] w = new double[][][]{new double[][]{new double[]{0.5, 0.5}}};
//		double[][] b = new double[][]{new double[]{0.7}};
//
//		Network network = new Network(2, 1, w, b);

		Network network = new Network(3, 2, 5, 7);

		NetworkHelper.save(network, "Network");
		Network network1 = NetworkHelper.load("Network");

		NetworkHelper.save(network1, "Network1");

//		System.out.println(network);
//		System.out.println("-------------------------------------------------");
//		System.out.println(Arrays.toString(network.compute(new double[]{0, 0})));
//		System.out.println(Arrays.toString(network.compute(new double[]{0, 1})));
//		System.out.println(Arrays.toString(network.compute(new double[]{1, 0})));
//		System.out.println(Arrays.toString(network.compute(new double[]{1, 1})));
	}
}
