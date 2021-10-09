
/**
 * Neuronal Network that does not compute the output with {@link Neuron} but with matrices and vectors.
 */
public class NetworkMatrices implements INetwork{
	double[][][] weights;
	double[][] biases;

	public NetworkMatrices(int numInUnit, int numOutUnit, double[][][] weights, int... numHidUnit) {
		this(numInUnit, numOutUnit, numHidUnit);
		if(numHidUnit.length != weights.length - 1) {throw new IllegalArgumentException("Illegal weight matrices");}
		this.weights = weights;
	}

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
		weights[0] = Util.random(numInUnit, twoLayers ? numOutUnit : numHidUnit[0]);
		if(!twoLayers)
			weights[weights.length - 1] = Util.random(numHidUnit[numHidUnit.length - 1], numOutUnit);

		for(int i = 1; i < weights.length - 1; i++)
			weights[i] = Util.random(numHidUnit[i-1], numHidUnit[i]);

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
		double[][] layers = forwardPropagation(input);
		return layers[layers.length - 1];
	}

	@Override
	public void train(double[][] inputVectors, double[][] labels, int repetitions, double learnRate) {
		for(int i = 0; i < repetitions; i++) {
			for(int j = 0; j < inputVectors.length; j++) {
				double[][] layers = forwardPropagation(inputVectors[j]);
			}
		}
	}

	private double[][] forwardPropagation(double[] input) {
		double[][] layers = new double[biases.length + 1][];
		layers[0] = input;

		for(int i = 0; i < layers.length - 1; i++)
			layers[i + 1] = Util.sigmoid(Util.add(biases[i], Util.mul(weights[i], layers[i]))); // sigma propagation rule

		return layers;
	}

	private void backpropagation(double[][] layers, double[] label, int learnRate) {
		double[] delta = Util.sub(layers[0], label);
		for(int i = layers.length - 1; i >= 0; i--) {
			double[] deltaLearn = Util.mul(-learnRate, delta);
			weights[i] = Util.add(weights[i], Util.mul(deltaLearn, Util.transpose(layers[i])));
			biases[i] = Util.add(biases[i], deltaLearn);
			//TODO delta, but does not work, so look for another method for back propagation
		}
	}
}
