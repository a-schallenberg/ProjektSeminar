public class Network {
	Neuron[] inputLayer, outputLayer;
	Neuron[][] hiddenLayers;

	public Network(int numInUnit, int numOutUnit, int... numHidUnit) {
		inputLayer = new Neuron[numInUnit];
		outputLayer = new Neuron[numOutUnit];
		hiddenLayers = new Neuron[numHidUnit.length][];

		for(int i = 0; i < numInUnit; i++) {

			if(hiddenLayers.length == 0) {inputLayer[i] = new Neuron(outputLayer);}
			else {inputLayer[i] = new Neuron(hiddenLayers[0]);}
		}

		for(int i = 0; i < hiddenLayers.length; i++) {
			hiddenLayers[i] = new Neuron[numHidUnit[i]];

			for(int j = 0; j < numHidUnit[i]; j++) {
				if(i + 1  < hiddenLayers.length)
					hiddenLayers[i][j] = new Neuron(hiddenLayers[i + 1]);
				else
					hiddenLayers[i][j] = new Neuron(outputLayer);
			}
		}

		for(int i = 0; i < numOutUnit; i++) {
			outputLayer[i] = new Neuron(null);
		}
	}

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
}
