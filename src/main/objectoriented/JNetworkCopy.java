package main.objectoriented;

import main.INetwork;
import main.Util;
import main.afunctions.AFunction;
import main.afunctions.SigmoidFunction;

import java.util.Arrays;

public class JNetworkCopy implements INetwork {
	private final AFunction function;
	JNeuron[] outputLayer;
	JNeuron[][] hiddenLayers;
	JNeuron[][] layers;

	public JNetworkCopy(double[][][] weights, int numInUnit, int numOutUnit, int... numHidUnit) {
		this(new SigmoidFunction(), weights, numInUnit, numOutUnit, numHidUnit);
	}

	public JNetworkCopy(AFunction function, double[][][] weights, int numInUnit, int numOutUnit, int... numHidUnit) {
		if(numHidUnit.length != weights.length - 1) throw new IllegalArgumentException("Illegal weight matrices");

		this.function = function;

		outputLayer = new JNeuron[numOutUnit];
		hiddenLayers = new JNeuron[numHidUnit.length][];

		// Hidden layers
		for(int i = 0; i < hiddenLayers.length; i++) {
			hiddenLayers[i] = new JNeuron[numHidUnit[i]];

			for(int j = 0; j < numHidUnit[i]; j++)
				hiddenLayers[i][j] = new JNeuron(weights[i][j]);
		}

		// Output layer
		for(int i = 0; i < numOutUnit; i++)
			outputLayer[i] = new JNeuron(weights[weights.length - 1][i]);

		layers = new JNeuron[hiddenLayers.length + 1][];

		System.arraycopy(hiddenLayers, 0, layers, 0, layers.length - 1);

		layers[layers.length - 1] = outputLayer;
	}

	public JNetworkCopy(int numInUnit, int numOutUnit, int... numHidUnit) {
		this(new SigmoidFunction(), numInUnit, numOutUnit, numHidUnit);
	}

	public JNetworkCopy(AFunction function, int numInUnit, int numOutUnit, int... numHidUnit) {
		this.function = function;

		outputLayer = new JNeuron[numOutUnit];
		hiddenLayers = new JNeuron[numHidUnit.length][];

		// Hidden layers
		for(int i = 0; i < hiddenLayers.length; i++) {
			hiddenLayers[i] = new JNeuron[numHidUnit[i]];

			for(int j = 0; j < numHidUnit[i]; j++)
				hiddenLayers[i][j] = new JNeuron(Util.random(i == 0 ? numInUnit : numHidUnit[i - 1]));
		}

		// Output layer
		for(int i = 0; i < numOutUnit; i++)
			outputLayer[i] = new JNeuron(Util.random(numHidUnit.length == 0 ? numInUnit : numHidUnit[numHidUnit.length - 1]));
	}

	@Override
	public double[] compute(double[] input) {
		double[] output = new double[outputLayer.length];
		double[][] hidden = new double[hiddenLayers.length][];

		forwardPropagation(input, hidden, output);

		return output;
	}

	@Override
	public void train(double[][] input, double[][] labels, int repetitions, double learnRate){
		double[] output = new double[outputLayer.length];
		double[][] hidden = new double[hiddenLayers.length][];

		for(int i = 0; i < repetitions; i++) {
			double cost = 0d;
			int correct = 0;
			System.out.println("Repetition " + i);
			for(int j = 0; j < input.length; j++) {
				forwardPropagation(input[j], hidden, output);
				cost += cost(output, labels[j]);
				correct += correct(output, labels[j]) ? 1 : 0;
				backpropagation(output, hidden, labels[j], learnRate);
			}
			System.out.printf("Cost: %f\nCorrect: %d of %d\n\n", cost, correct, input.length);
		}
	}

	private void forwardPropagation(double[] input, double[][] hidden, double[] output) {
		for(int i = 0; i < hiddenLayers.length; i++) // Initialize second layer of hidden matrix.
			hidden[i] = new double[hiddenLayers[i].length];

		// Hidden layers
		for(int i = 0; i < hiddenLayers.length; i++)
			for(int j = 0; j < hiddenLayers[i].length; j++)
				hidden[i][j] = hiddenLayers[i][j].fire(i == 0 ? input : hidden[i - 1], function);

		// Output layer
		for(int i = 0; i < outputLayer.length; i++)
			output[i] = outputLayer[i].fire(hiddenLayers.length == 0 ? input : hidden[hidden.length - 1], function);
	}

	private void backpropagation(double[][] results, double[] label, double learnRate) {
		double[] delta = new double[Util.maxLength(results)];
		for(int i = 0; i < delta.length; i++)
			delta[i] = 2 * (results[results.length - 1][i] - label[i]);

		backpropagation(delta, results.length - 1, layers, results, learnRate);
	}

	private void backpropagation(double[] delta, int index, JNeuron[][] layers, double[][] results, double learnRate) {
		if(index < 0) return;

		for(int i = 0; i < layers[index].length; i++) {
			double df = function.derivative(layers[index][i].z);
			double w = Arrays.stream(layers[index][i].weights).sum();
			double deltaI = delta[i];

			for(int j = 0; j < layers[index][i].weights.length; j++)
				layers[index][i].weights[j] += -learnRate * results[index][i] * df * deltaI;

			layers[index][i].bias += -learnRate * df * deltaI;
			delta[i] = w * df * deltaI;
		}

		backpropagation(delta, index - 1, layers, results, learnRate);
	}

	private void backpropagation(double[] output, double[][] hidden, double[] label, double learnRate) {
		double[][] delta = new double[hiddenLayers.length + 1][];
		delta[0] = new double[output.length];
		for(int i = 0; i < outputLayer.length; i++) {
			delta[0][i] = 2 * (output[i] - label[i]);
			double deltaLearn = -learnRate * delta[0][i];

			for(int j = 0; j < outputLayer[i].weights.length; j++)
				outputLayer[i].weights[j] += deltaLearn * output[i];

			outputLayer[i].bias += deltaLearn;
		}

		for(int i = hiddenLayers.length - 2; i >= 0; i--) {
			int size = hiddenLayers[i].length;
			delta[i + 1] = new double[size];
			for(int j = 0; j < size; j++) {
				delta[i + 1][j] = Util.abs(Util.mul(delta[i][j], hiddenLayers[i][j].weights)) * function.derivative(hidden[i][j]);
				double deltaLearn = -learnRate * delta[i + 1][j];
				for(int k = 0; k < hiddenLayers[i][j].weights.length; k++)
					hiddenLayers[i][j].weights[k] += deltaLearn * hidden[i][j];

				hiddenLayers[i][j].bias += deltaLearn;
			}
		}
	}

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
