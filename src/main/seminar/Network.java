package main.seminar;

import main.Util;

/**
 * The class {@link Network} allows creating objects from this type. It represents a neural network and works with {@link Neuron}s.
 * @author aschal2s, azarkh2s, llegge2s, szhang2s
 */
public class Network {
	Neuron[] inputLayer, outputLayer;
	Neuron[][] hiddenLayers;

	/**
	 * Constructor of {@link Network}.
	 * @param numInUnit Number of units of the input layer.
	 * @param numOutUnit Number of units of the output layer
	 * @param weights Weights for the neural network.
	 * @param numHidUnit Numbers of units of the hidden layers. The number of arguments starting with the forth argument represents the number of hidden layers. Every integer gives the number of units in the specific layer.
	 * @throws IllegalArgumentException Whether the weights' matrix' size and the number of layers do not match.
	 */
	public Network(int numInUnit, int numOutUnit, double[][][] weights, int... numHidUnit) {
		if(numHidUnit.length != weights.length - 2) {throw new IllegalArgumentException("Illegal weight matrices");}

		inputLayer = new Neuron[numInUnit];
		outputLayer = new Neuron[numOutUnit];
		hiddenLayers = new Neuron[numHidUnit.length][];

		// Try and catch for throwing IllegalArgumentException instead of ArrayIndexOutOfBoundsException

		// Initialize input layer
		for(int i = 0; i < numInUnit; i++)
			inputLayer[i] = new Neuron(hiddenLayers.length == 0 ? outputLayer: hiddenLayers[0], weights[0][i]);

		// Initialize hidden layers
		for(int i = 0; i < hiddenLayers.length; i++) {
			hiddenLayers[i] = new Neuron[numHidUnit[i]];

			for(int j = 0; j < numHidUnit[i]; j++)
					hiddenLayers[i][j] = new Neuron(i + 1 < hiddenLayers.length ? hiddenLayers[i + 1] : outputLayer, weights[i + 1][j]);

		}

		// Initialize output layer
		for(int i = 0; i < numOutUnit; i++) {
			outputLayer[i] = new Neuron(null, weights[weights.length - 1][i]);
		}
	}

	/**
	 * Constructor of {@link Network}. In this constructor the weights are random decimal numbers between -0.5 and 0.5.
	 * @param numInUnit Number of units of the input layer.
	 * @param numOutUnit Number of units of the output layer
	 * @param numHidUnit Numbers of units of the hidden layers. The number of arguments starting with the forth argument represents the number of hidden layers. Every integer gives the number of units in the specific layer.
	 */
	public Network(int numInUnit, int numOutUnit, int... numHidUnit) {
		inputLayer = new Neuron[numInUnit];
		outputLayer = new Neuron[numOutUnit];
		hiddenLayers = new Neuron[numHidUnit.length][];

		for(int i = 0; i < numInUnit; i++)
			inputLayer[i] = new Neuron(hiddenLayers.length == 0 ? outputLayer: hiddenLayers[0], new double[]{1});


		for(int i = 0; i < hiddenLayers.length; i++) {
			hiddenLayers[i] = new Neuron[numHidUnit[i]];

			for(int j = 0; j < numHidUnit[i]; j++) {
				hiddenLayers[i][j] = new Neuron(i + 1 < hiddenLayers.length ? hiddenLayers[i + 1] : outputLayer, Util.random(i == 0 ? numInUnit : numHidUnit[i - 1]));
			}
		}

		for(int i = 0; i < numOutUnit; i++) {
			outputLayer[i] = new Neuron(null, Util.random(numHidUnit.length == 0 ? numInUnit : numHidUnit[numHidUnit.length - 1]));
		}
	}

	/**
	 * Computing a result with an input vector.
	 * @param input Input vector for computing.
	 * @return Result (output vector) of the neural network.
	 * @throws IllegalArgumentException Whether the input vector's size does not match the input layer size.
	 */
	public double[] compute(double[] input) {
		if(input.length != inputLayer.length) throw new IllegalArgumentException("Argument's size and input layer's size do not match");

		double[] inputResult = new double[inputLayer.length];
		double[] outputResult = new double[outputLayer.length];
		double[][] hiddenResults = new double[hiddenLayers.length][];

		// Fire all units of input layer
		for(int i = 0; i < inputLayer.length; i++)
			inputResult[i] = inputLayer[i].fire(input);

		// Fire all units of hidden layers
		for(int i = 0; i < hiddenLayers.length; i++)
			for(int j = 0; j < hiddenLayers[i].length; j++)
				hiddenResults[i][j] = hiddenLayers[i][j].fire(j == 0 ? inputResult : hiddenResults[i - 1]);

		// Fire all units of output layer
		for(int i = 0; i < outputLayer.length; i++)
			inputResult[i] = inputLayer[i].fire(hiddenLayers.length == 0 ? inputResult : hiddenResults[hiddenResults.length - 1]);

		return outputResult;
	}
}
