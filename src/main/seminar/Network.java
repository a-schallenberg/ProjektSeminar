package main.seminar;

import main.Util;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * The class {@link Network} allows creating objects from this type. It represents a neural network and works with {@link Neuron}s.
 * @author aschal2s, azarkh2s, llegge2s, szhang2s
 */
public class Network {
	private final int inLayerLength;
	private final Neuron[] outputLayer;
	private final Neuron[][] hiddenLayers;

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
		if(input.length != inLayerLength) throw new IllegalArgumentException("Argument's size and input layer's size do not match");

		double[] outputResult = new double[outputLayer.length];
		double[][] hiddenResults = new double[hiddenLayers.length][];

		// Fire all units of hidden layers
		for(int i = 0; i < hiddenLayers.length; i++)
			for(int j = 0; j < hiddenLayers[i].length; j++)
				hiddenResults[i][j] = hiddenLayers[i][j].fire(j == 0 ? input : hiddenResults[i - 1]);

		// Fire all units of output layer
		for(int i = 0; i < outputLayer.length; i++)
			outputResult[i] = outputLayer[i].fire(hiddenLayers.length == 0 ? input : hiddenResults[hiddenResults.length - 1]);

		return outputResult;
	}

	public void backpropagation(double learnRate, double[] y, double[][] results) {
		int length = hiddenLayers.length; // TODO +1 ?
		LinkedList<double[]> list = new LinkedList<>();
		list.add(new double[outputLayer.length]);
		for(int i = 0; i < list.get(0).length; i++) {
			list.get(0)[i] = 2 * (results[results.length - 1][i] - y[i]);
		}
		for(int i = length; i >= 0; i--) {
			list.add(new double[i == 0 ? inLayerLength : hiddenLayers[i-1].length]);
			for(int j = 0; j < (i == length ? outputLayer.length : hiddenLayers[i].length); j++) {
				Util.addToVec1(list.get(0), (i == length ? outputLayer[j] : hiddenLayers[i][j]).backpropagation(learnRate, list.get(0)[j], results[i+1][j], results[i])); // TODO list.get(0) -> list.get(1)
			}
			Util.mulToVec(1.0/(i == length ? outputLayer.length : hiddenLayers[i].length), list.get(0)); // Wird diese Zeile gebraucht ? TODO list.get(0) -> list.get(1)
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

	public static void main(String[] args) {
		double[][][] w = new double[][][]{new double[][]{new double[]{0.5, 0.5}}};
		double[][] b = new double[][]{new double[]{0.7}};

		Network network = new Network(2, 1, w, b);
		System.out.println(network);
		System.out.println("-------------------------------------------------");
		System.out.println(Arrays.toString(network.compute(new double[]{0, 0})));
		System.out.println(Arrays.toString(network.compute(new double[]{0, 1})));
		System.out.println(Arrays.toString(network.compute(new double[]{1, 0})));
		System.out.println(Arrays.toString(network.compute(new double[]{1, 1})));
	}
}
