package main.old;

import main.INetwork;

public class NetworkOld implements INetwork {
	NeuronOld[] inputLayer, outputLayer;
	NeuronOld[][] hiddenLayers;

	public NetworkOld(int numInUnit, int numOutUnit, double[][][] weights, int... numHidUnit) {
		if(numHidUnit.length != weights.length - 1) {throw new IllegalArgumentException("Illegal weight matrices");}

		inputLayer = new NeuronOld[numInUnit];
		outputLayer = new NeuronOld[numOutUnit];
		hiddenLayers = new NeuronOld[numHidUnit.length][];

		// Input layer
		for(int i = 0; i < numInUnit; i++)
			inputLayer[i] = new NeuronOld(hiddenLayers.length == 0 ? outputLayer: hiddenLayers[0]);

		// Hidden layers
		for(int i = 0; i < hiddenLayers.length; i++) {
			hiddenLayers[i] = new NeuronOld[numHidUnit[i]];

			for(int j = 0; j < numHidUnit[i]; j++) {
				if(i + 1  < hiddenLayers.length)
					hiddenLayers[i][j] = new NeuronOld(hiddenLayers[i + 1]);
				else
					hiddenLayers[i][j] = new NeuronOld(outputLayer);
			}
		}

		// Output layer
		for(int i = 0; i < numOutUnit; i++) {
			outputLayer[i] = new NeuronOld(null);
		}
	}

	public NetworkOld(int numInUnit, int numOutUnit, int... numHidUnit) {
		inputLayer = new NeuronOld[numInUnit];
		outputLayer = new NeuronOld[numOutUnit];
		hiddenLayers = new NeuronOld[numHidUnit.length][];

		for(int i = 0; i < numInUnit; i++) {

			if(hiddenLayers.length == 0) {inputLayer[i] = new NeuronOld(outputLayer);}
			else {inputLayer[i] = new NeuronOld(hiddenLayers[0]);}
		}

		for(int i = 0; i < hiddenLayers.length; i++) {
			hiddenLayers[i] = new NeuronOld[numHidUnit[i]];

			for(int j = 0; j < numHidUnit[i]; j++) {
				if(i + 1  < hiddenLayers.length)
					hiddenLayers[i][j] = new NeuronOld(hiddenLayers[i + 1]);
				else
					hiddenLayers[i][j] = new NeuronOld(outputLayer);
			}
		}

		for(int i = 0; i < numOutUnit; i++) {
			outputLayer[i] = new NeuronOld(null);
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
	public void train(double[][] inputVectors, double[][] labels, int repetitions, double learnRate) {

	}
}
