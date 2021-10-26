package main.objectoriented;

import main.Util;
import main.afunctions.ActivationFunction;

import java.util.Arrays;

public class JNeuron {
	public double[] weights;
	public double bias, z, a;
	public JNeuron[] prevLayer;
	public final ActivationFunction function;

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
}
