package main.objectoriented;

import main.Util;
import main.afunctions.AFunction;

public class JNeuron {
	public double[] weights;
	public double bias;
	public double z;

	public JNeuron(double[] weights) {
		this.weights = weights;
	}

	public double fire(double[] input, AFunction function) {
		z = Util.sum(Util.mul(weights, input)) + bias;
		return function.function(z);
	}

	public double[] backpropagation(double result, double delta, double learnRate, AFunction function) {
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
