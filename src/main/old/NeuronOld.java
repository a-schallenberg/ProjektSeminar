package main.old;

import main.Util;

import java.util.Arrays;

public class NeuronOld {
	private double[] weights;
	private double bias = 1;
	private NeuronOld[] sendToArray;


	public NeuronOld(NeuronOld[] sendToArray, double[] weights) {
		this.sendToArray = sendToArray;
		this.weights = weights;
	}

 	public NeuronOld(NeuronOld[] sendToArray) {
		this.sendToArray = sendToArray;
	}

	public double fire(double[] input) {
		if(weights == null) { // initialize weights !!!
			weights = new double[input.length];
			Arrays.setAll(weights, (w) -> w = 1);
		}

		 return fireSigmoid(input);
	}

	private int fireSGN(double[] input) {
		 double sum = 0;
		 for(int i = 0; i < (Math.min(input.length, weights.length)); i++)
			 sum += weights[i] * input[i];

		 return bias <= sum ? 1 : 0;
	}

	private double fireSigmoid(double[] input) {
		double sum = 0;
		for(int i = 0; i < (Math.min(input.length, weights.length)); i++)
			sum += weights[i] * input[i];

		sum += bias;
		return Util.sigmoid(sum);
	}

	private double fireSemiLinear(double[] input) {
		double sum = 0;
		for(int i = 0; i < (Math.min(input.length, weights.length)); i++)
			sum += weights[i] * input[i];

		sum += bias;
		return Util.semiLinear(sum);
	}
}
