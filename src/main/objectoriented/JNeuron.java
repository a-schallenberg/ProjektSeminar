package main.objectoriented;

import main.util.Util;
import main.afunctions.ActivationFunction;
import main.afunctions.OutputFunction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;

public class JNeuron {
	public double[] weights;
	public double bias, z, a;
	public JNeuron[] prevLayer;
	private OutputFunction function;

	private JNeuron() {}

	public JNeuron(int weightDim, OutputFunction function) {
		this(Util.random(weightDim), 0, function);
	}

	public JNeuron(double[] weights, double bias, OutputFunction function) {
		this.weights = weights;
		this.bias = bias;
		this.function = function;
	}

	public double fire(double[] input) {
		z = Util.sum(Util.add(Util.mul(weights, input), bias)); // TODO sum(w * a + b) or sum(w * a) + b ?
		a = function.function(z);
		return a;
	}

	public void backpropagationTest(double delta, double learnRate, double[] input) {
		ActivationFunction fct = (ActivationFunction) function;

		double df = fct.derivative(z);

		for(int i = 0; i < weights.length; i++)
			weights[i] += -learnRate * delta * (prevLayer == null ? input[i] : prevLayer[i].a);

		bias += -learnRate * delta;
		for(int i = 0; prevLayer != null && i < prevLayer.length; i++)
			prevLayer[i].backpropagationTest(delta * df * weights[i], learnRate, input);
	}

	public double[] backpropagation(double learnRate, double delta, double[] prevResults) {
		ActivationFunction fct = (ActivationFunction) function;

		for(int i = 0; i < weights.length; i++)
			weights[i] += -learnRate * prevResults[i] * delta;

		bias += -learnRate * delta;
		double[] deltas = new double[weights.length];
		for(int i = 0; i < deltas.length; i++)
			deltas[i] = weights[i] * fct.derivative(z) * delta;

		return deltas;
	}

	@Override
	public String toString() {
		return "Neuron{" +
				"weights=" + Arrays.toString(weights) +
				", bias=" + bias +
				'}';
	}

	public OutputFunction getFunction() {
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
		neuron.function = OutputFunction.fromString(strings[2]);

		return neuron;
	}

	public void toBuffer(BufferedWriter writer) throws IOException {
		writer.append(Arrays.toString(weights)).append("; ").append(String.valueOf(bias)).append("; ").append(String.valueOf(function));
	}
}
