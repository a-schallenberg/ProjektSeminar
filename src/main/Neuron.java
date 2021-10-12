package main;

public class Neuron {
	public double[] weights;
	public double bias;

	public Neuron(double[] weights) {
		this.weights = weights;
	}

	public double fire(double[] input) {
		 return fireSigmoid(input); // can switch between sgn, semi-linear and sigmoid.
	}

	private int fireSGN(double[] input) {
		 return bias <= sum(input) ? 1 : 0;
	}

	private double fireSigmoid(double[] input) {
		return Util.sigmoid(sum(input) + bias);
	}

	private double fireSemiLinear(double[] input) {
		return Util.semiLinear(sum(input) + bias);
	}

	private double sum(double[] input) {
		double sum = 0;
		for(int i = 0; i < (Math.min(input.length, weights.length)); i++)
			sum += weights[i] * input[i];

		return sum;
	}
}
