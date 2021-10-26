package main.objectorientedCopy;

import main.Util;
import main.afunctions.ActivationFunction;

public class JNeuron {
	public double[] weights;
	public double bias, z;
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
		return function.function(z);
	}

	public double[] backpropagation(double result, double delta, double learnRate) {
		double df = function.derivative(z);
		double[] deltas = new double[weights.length];

		bias += -learnRate * delta * df;
		for(int i = 0; i < weights.length; i++) {
			weights[i] += -learnRate * delta * df * result;
			deltas[i] = delta * df * weights[i];
		}

		return deltas;
	}
}
