package main.seminar;

import main.INetwork;
import main.Util;

public class Network implements INetwork {
	Neuron[] inputLayer, outputLayer;
	Neuron[][] hiddenLayers;

	public Network(int numInUnit, int numOutUnit, double[][][] weights, int... numHidUnit) {
		if(numHidUnit.length != weights.length - 2) {throw new IllegalArgumentException("Illegal weight matrices");}

		inputLayer = new Neuron[numInUnit];
		outputLayer = new Neuron[numOutUnit];
		hiddenLayers = new Neuron[numHidUnit.length][];

		// Input layer
		for(int i = 0; i < numInUnit; i++)
			inputLayer[i] = new Neuron(hiddenLayers.length == 0 ? outputLayer: hiddenLayers[0], weights[0][i]);

		// Hidden layers
		for(int i = 0; i < hiddenLayers.length; i++) {
			hiddenLayers[i] = new Neuron[numHidUnit[i]];

			for(int j = 0; j < numHidUnit[i]; j++)
					hiddenLayers[i][j] = new Neuron(i + 1 < hiddenLayers.length ? hiddenLayers[i + 1] : outputLayer, weights[i + 1][j]);

		}

		// Output layer
		for(int i = 0; i < numOutUnit; i++) {
			outputLayer[i] = new Neuron(null, weights[weights.length - 1][i]);
		}
	}

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

	@Override
	public double[] compute(double[] input) {
		double[] inputResult = new double[inputLayer.length];
		double[] outputResult = new double[outputLayer.length];
		double[][] hiddenResults = new double[hiddenLayers.length][];

		for(int i = 0; i < inputLayer.length; i++)
			inputResult[i] = inputLayer[i].fire(input);

		for(int i = 0; i < hiddenLayers.length; i++)
			for(int j = 0; j < hiddenLayers[i].length; j++)
				hiddenResults[i][j] = hiddenLayers[i][j].fire(j == 0 ? inputResult : hiddenResults[i - 1]);

		for(int i = 0; i < outputLayer.length; i++)
			inputResult[i] = inputLayer[i].fire(hiddenLayers.length == 0 ? inputResult : hiddenResults[hiddenResults.length - 1]);

		return outputResult;
	}

	@Override
	public void train(double[][] inputVectors, double[][] labels, int repetitions, double learnRate) {}
}
