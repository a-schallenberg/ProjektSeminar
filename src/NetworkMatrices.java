
/**
 * Neuronal Network that does not compute the output with {@link Neuron} but with matrices and vectors.
 */
public class NetworkMatrices implements INetwork{
	double[][][] weights;
	double[][] biases;

	/**
	 * Constructor that declare and initialize weights with random numbers between -0.5 and 0.5 and biases with 0.
	 * @param numInUnit The number of neurons of the input layer.
	 * @param numOutUnit The number of neurons of the output layer.
	 * @param numHidUnit The numbers of neurons in the hidden layers. The number of values after the first two parameters
	 *                   represents the number of hidden layers.
	 */
	public NetworkMatrices(int numInUnit, int numOutUnit, int... numHidUnit) {
		boolean twoLayers = numHidUnit.length == 0;

		weights = new double[numHidUnit.length + 1][][];
		weights[0] = Util.randomMatrix(numInUnit, twoLayers ? numOutUnit : numHidUnit[0]);
		if(!twoLayers)
			weights[weights.length - 1] = Util.randomMatrix(numHidUnit[numHidUnit.length - 1], numOutUnit);

		for(int i = 1; i < weights.length - 1; i++)
			weights[i] = Util.randomMatrix(numHidUnit[i-1], numHidUnit[i]);

		biases = new double[numHidUnit.length + 1][];
		biases[biases.length - 1] = new double[numOutUnit];
		for(int i = 0; i < biases.length - 1; i++)
			biases[i] = new double[numHidUnit[i]];
	}

	/**
	 * Computes the output vector via the neuronal network.
	 * @param input The input vector for computing the output vector.
	 * @return The output vector.
	 */
	@Override
	public double[] compute(double[] input) {
		double[][] layers = new double[biases.length + 1][];
		layers[0] = input;

		for(int i = 0; i < biases.length; i++)
			layers[1] = Util.sigmoid(Util.addVectors(biases[i], Util.mulMatrixVector(weights[i], biases[i])));

		return layers[layers.length - 1];
	}
}
