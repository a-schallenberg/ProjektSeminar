package main.objectoriented;

import main.Util;
import main.afunctions.ActivationFunction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;

public class JNeuron {
	public double[] weights;
	public double bias, z, a;
	public JNeuron[] prevLayer;
	private ActivationFunction function;

	private JNeuron() {}

	public JNeuron(int weightDim, ActivationFunction function) {
		this(Util.random(weightDim), 1, function);
	}

	public JNeuron(double[] weights, double bias, ActivationFunction function) {
		this.weights = weights;
		this.bias = bias;
		this.function = function;
	}

	public double fire(double[] input) {
		z = Util.sum(Util.mul(weights, input)) + bias;
		a = function.function(z);
		return a;
	}

	public void backpropagation(double delta, double learnRate) {
		double df = function.derivative(z);

		for(int i = 0; i < weights.length; i++)
			weights[i] += -learnRate * delta * a;

		bias += -learnRate * delta;
		for(int i = 0; prevLayer != null && i < prevLayer.length; i++)
			prevLayer[i].backpropagation(delta * df * weights[i], learnRate);
	}

	@Override
	public String toString() {
		return "Neuron{" +
				"weights=" + Arrays.toString(weights) +
				", bias=" + bias +
				'}';
	}

	public ActivationFunction getFunction() {
		return function;
	}

	public static JNeuron fromBuffer(String string) throws IOException {
		JNeuron neuron = new JNeuron();

		string = string.replace("[", "").replace("]", "");
		String[] strings = string.split("; ");
		String[] strWeights = strings[0].split(", ");
		neuron.weights = new double[strWeights.length];
		for(int i = 0; i < strWeights.length; i++)
			neuron.weights[i] = Double.parseDouble(strWeights[i]);

		neuron.bias = Double.parseDouble(strings[1]);
		neuron.function = ActivationFunction.fromString(strings[2]);

		return neuron;
	}

	public void toBuffer(BufferedWriter writer) throws IOException {
		writer.append(Arrays.toString(weights)).append("; ").append(String.valueOf(bias)).append("; ").append(String.valueOf(function));
	}
}
