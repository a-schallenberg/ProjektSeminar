package main.objectoriented;

import main.afunctions.AFunction;

public class JNeuron {
	public double[] weights;
	public double bias;
	public double z;

	public JNeuron(double[] weights) {
		this.weights = weights;
	}

	public double fire(double[] input, AFunction function) {
		z = sum(input) + bias;
		return function.function(z);
	}

	public double sum(double[] input) {
		double sum = 0;
		for(int i = 0; i < (Math.min(input.length, weights.length)); i++)
			sum += weights[i] * input[i];

		return sum;
	}
}
