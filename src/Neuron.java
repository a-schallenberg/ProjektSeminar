import java.util.Arrays;

public class Neuron {
	private double[] weights;
	private double bias;
	private Neuron[] sendToArray;


	public Neuron(Neuron[] sendToArray, double[] weights) {
		this.sendToArray = sendToArray;
		this.weights = weights;
	}

 	public Neuron(Neuron[] sendToArray) {
		this.sendToArray = sendToArray;
	}

	public double fire(double[] input) {
		if(weights == null) { //TODO initialize weights !!!
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
