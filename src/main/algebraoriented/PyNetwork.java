package main.algebraoriented;

import main.INetwork;
import main.util.Util;
import main.afunctions.ActivationFunction;
import main.afunctions.SigmoidFunction;
import main.objectoriented.JNeuron;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Neuronal main.objectoriented.Network that does not compute the output with {@link JNeuron} but with matrices and vectors.
 */
public class PyNetwork implements INetwork {
	private final ActivationFunction function;
	private double[][][] weights;
	private double[][] biases;

	public PyNetwork(int numInUnit, int numOutUnit, double[][][] weights, int... numHidUnit) {
		this(new SigmoidFunction(), weights, numInUnit, numOutUnit, numHidUnit);
	}

	public PyNetwork(ActivationFunction function, double[][][] weights, int numInUnit, int numOutUnit, int... numHidUnit) {
		this(function, numInUnit, numOutUnit, numHidUnit);
		if(numHidUnit.length != weights.length - 1) {throw new IllegalArgumentException("Illegal weight matrices");}
		this.weights = weights;
	}

	public PyNetwork(int numInUnit, int numOutUnit, int... numHidUnit) {
		this(new SigmoidFunction(), numInUnit, numOutUnit, numHidUnit);
	}

	/**
	 * Constructor that declare and initialize weights with random numbers between -0.5 and 0.5 and biases with 0.
	 * @param numInUnit The number of neurons of the input layer.
	 * @param numOutUnit The number of neurons of the output layer.
	 * @param numHidUnit The numbers of neurons in the hidden layers. The number of values after the first two parameters
	 *                   represents the number of hidden layers.
	 */
	public PyNetwork(ActivationFunction function, int numInUnit, int numOutUnit, int... numHidUnit) {
		this.function = function;

		boolean twoLayers = numHidUnit.length == 0;

		weights = new double[numHidUnit.length + 1][][];
		weights[0] = Util.random(twoLayers ? numOutUnit : numHidUnit[0], numInUnit);
		if(!twoLayers)
			weights[weights.length - 1] = Util.random(numOutUnit, numHidUnit[numHidUnit.length - 1]);

		for(int i = 1; i < weights.length - 1; i++)
			weights[i] = Util.random(numHidUnit[i], numHidUnit[i - 1]);

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
	public void train(double[][] input, double[][] labels, int repetitions, double learnRate) {
		System.out.println("Start training");
		long start = System.currentTimeMillis();

		for(int i = 0; i < repetitions; i++) {
			double cost = 0d;
			int correct = 0;
			System.out.println(i + 1 + ". repetition:");
			for(int j = 0; j < input.length; j++) {
				double[][] layers = forwardPropagation(input[j]);
				cost += cost(layers[layers.length - 1], labels[j]);
				correct += correct(layers[layers.length - 1], labels[j]) ? 1 : 0;
				backpropagation(layers, labels[j], learnRate);
			}
			System.out.printf("Cost: %f\nCorrect: %f%% \n\n", cost, correct/ (double) input.length * 100);
		}
		System.out.println("Finished training in " + (System.currentTimeMillis() - start) + "ms");
	}

	@Override
	public void fromBuffer(BufferedReader reader) throws IOException {

	}

	@Override
	public void toBuffer(BufferedWriter writer) throws IOException {

	}

	private double[][] forwardPropagation(double[] input) {
		double[][] layers = new double[biases.length + 1][];
		layers[0] = input;

		for(int i = 0; i < layers.length - 1; i++)
			layers[i + 1] = function.function(Util.add(biases[i], Util.mul(weights[i], layers[i]))); // sigma propagation rule

		return layers;
	}

	private void backpropagation(double[][] layers, double[] label, double learnRate) {
		double[] delta = Util.mul(2, Util.sub(layers[layers.length - 1], label));
		for(int i = layers.length - 2; i >= 0; i--) {
			double[] deltaLearn = Util.mul(-learnRate, delta);
			weights[i] = Util.add(weights[i], Util.mul(deltaLearn, Util.transpose(layers[i])));
			biases[i] = Util.add(biases[i], deltaLearn);
			delta = Util.mul(Util.mul(Util.transpose(weights[i]), delta), function.derivative(layers[i]));
		}
	}

//	private double[] dSigCase(double[] vec) {
//		return function instanceof SigmoidFunction ? ((SigmoidFunction) function).inverse(vec): vec;
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
}
