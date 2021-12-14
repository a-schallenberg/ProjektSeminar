package main;

import main.functions.ActivationFunction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.function.BiFunction;

/**
 * The class {@link Network} allows creating objects from this type. It represents a neural network and works with {@link Neuron}s.
 * @author aschal2s, azarkh2s, llegge2s, szhang2s
 */
public class Network {
	private int inLayerLength;
	private Neuron[] outputLayer;
	private Neuron[][] hiddenLayers;
	private boolean trainable = true;

	public Network(int numInUnit, int numOutUnit, double[][][] weights, double[][] biases, int... numHidUnit) {
		init(numInUnit, numOutUnit, (i, j) -> new Neuron(weights[i][j], biases[i][j], ActivationFunction.DEFAULT_FUNCTION), numHidUnit);
	}

	public Network(int numInUnit, int numOutUnit, int... numHidUnit) {
		init(numInUnit, numOutUnit, (i, j) -> new Neuron(((i == 0) ? numInUnit : numHidUnit[i-1]), ActivationFunction.DEFAULT_FUNCTION), numHidUnit);
	}

	public Network(int numInUnit, int numOutUnit, double[][][] weights, double[][] biases, ActivationFunction function, int... numHidUnit) { // int... == int[] mit dem Unterschied: new Network(1, 1, w, 3, 5) statt new Network(1, 1, w, new int[]{3, 5})
		init(numInUnit, numOutUnit, (i, j) -> new Neuron(weights[i][j], biases[i][j], function), numHidUnit);
	}

	public Network(int numInUnit, int numOutUnit, ActivationFunction function,  int... numHidUnit) {  // int... == int[] mit dem Unterschied: new Network(1, 1, 3, 5) statt new Network(1, 1, new int[]{3, 5})
		init(numInUnit, numOutUnit, (i, j) -> new Neuron((i == 0) ? numInUnit : numHidUnit[i-1], function), numHidUnit);
	}

	/**
	 * Constructor of {@link Network}.
	 * @param numInUnit Number of units of the input layer.
	 * @param numOutUnit Number of units of the output layer
	 * @param weights Weights for the neural network.
	 * @param biases Biases for the neural network.
	 * @param numHidUnit Numbers of units of the hidden layers. The number of arguments starting with the fourth argument represents the number of hidden layers. Every integer gives the number of units in the specific layer.
	 * @throws IllegalArgumentException Whether the weights' matrix' size and the number of layers do not match.
	 */
	public Network(int numInUnit, int numOutUnit, double[][][] weights, double[][] biases, ActivationFunction[][] functions, int... numHidUnit) { // int... == int[] mit dem Unterschied: new Network(1, 1, w, 3, 5) statt new Network(1, 1, w, new int[]{3, 5})
		init(numInUnit, numOutUnit, (i, j) -> new Neuron(weights[i][j], biases[i][j], functions[i][j]), numHidUnit);
	}

	/**
	 * Constructor of {@link Network}. In this constructor the weights are random decimal numbers between -0.5 and 0.5.
	 * @param numInUnit Number of units of the input layer.
	 * @param numOutUnit Number of units of the output layer
	 * @param numHidUnit Numbers of units of the hidden layers. The number of arguments starting with the fourth argument represents the number of hidden layers. Every integer gives the number of units in the specific layer.
	 */
	public Network(int numInUnit, int numOutUnit, ActivationFunction[][] functions,  int... numHidUnit) {  // int... == int[] mit dem Unterschied: new Network(1, 1, 3, 5) statt new Network(1, 1, new int[]{3, 5})
		init(numInUnit, numOutUnit, (i, j) -> new Neuron(((i == 0) ? numInUnit : numHidUnit[i-1]), functions[i][j]), numHidUnit);
	}

	private void init(int numInUnit, int numOutUnit, BiFunction<Integer, Integer, Neuron> func, int... numHidUnit) {
		inLayerLength = numInUnit;
		outputLayer = new Neuron[numOutUnit];
		hiddenLayers = new Neuron[numHidUnit.length][];

		// Initialize hidden layers
		for(int i = 0; i < hiddenLayers.length; i++) {
			hiddenLayers[i] = new Neuron[numHidUnit[i]];

			for(int j = 0; j < numHidUnit[i]; j++) {
				hiddenLayers[i][j] = func.apply(i, j);
				if(Double.isNaN(hiddenLayers[i][j].getFunction().derivative(0))) trainable = false;
			}
		}

		// Initialize output layer
		for(int i = 0; i < numOutUnit; i++) {
			outputLayer[i] = func.apply(hiddenLayers.length, i);
			if(Double.isNaN(outputLayer[i].getFunction().derivative(0))) trainable = false;
		}
	}

	/**
	 * Computing a result with an input vector.
	 * @param input Input vector for computing.
	 * @return Result (output vector) of the neural network.
	 * @throws IllegalArgumentException Whether the input vector's size does not match the input layer size.
	 */
	public double[] compute(double[] input) {
		double[][] results = forwardPropagation(input);
		return results[results.length - 1];
	}

	public void train(double[][] input, double[][] y, double learnRate, int iterations) {
		if(!trainable) throw new UnsupportedOperationException("Network is not trainable");

		double[][] results;
		for(int i = 0; i < iterations; i++) {
			double cost = 0;
			for(int j = 0; j < input.length; j++) {
				results = forwardPropagation(input[j]);
				cost += cost(results[results.length - 1], y[j]);

//				System.out.println(Arrays.toString(results[results.length - 1]));
//				System.out.println(Arrays.toString(y[j]));
//				System.out.println();

				backpropagation(learnRate, y[j], results);
			}
			System.out.println("Cost: " + cost);
		}
	}

	private double[][] forwardPropagation(double[] input) {
		if(input.length != inLayerLength) throw new IllegalArgumentException("Argument's size and input layer's size do not match");

		double[][] results = new double[hiddenLayers.length + 2][];			//+2 = +inputpLayer+outputLayer
		results[0] = input;

		// Fire all units of hidden layers
		for(int i = 0; i < hiddenLayers.length; i++) {
			int length = hiddenLayers[i].length;
			results[i + 1] = new double[length];

			for(int j = 0; j < length; j++)
				results[i + 1][j] = hiddenLayers[i][j].fire(i == 0 ? input : results[i]);
		}

		results[results.length - 1] = new double[outputLayer.length];

		// Fire all units of output layer
		for(int i = 0; i < outputLayer.length; i++)
			results[results.length - 1][i] = outputLayer[i].fire(hiddenLayers.length == 0 ? input : results[results.length - 2]);

		return results;
	}

	private void backpropagation(double learnRate, double[] y, double[][] results) {
		int length = hiddenLayers.length; // TODO +1 ?
		LinkedList<double[]> list = new LinkedList<>();
		list.add(new double[outputLayer.length]);
		for(int i = 0; i < list.get(0).length; i++)
			list.get(0)[i] = 2 * (results[results.length - 1][i] - y[i]);

		for(int i = length; i >= 0; i--) {
			list.add(new double[i == 0 ? inLayerLength : hiddenLayers[i-1].length]);
			for(int j = 0; j < (i == length ? outputLayer.length : hiddenLayers[i].length); j++)
				Util.addToVec1(list.get(1), (i == length ? outputLayer[j] : hiddenLayers[i][j]).backpropagation(learnRate, list.get(0)[j], results[i]));
			
			//Util.mulToVec(1.0/(i == length ? outputLayer.length : hiddenLayers[i].length), list.get(1)); // Wird diese Zeile gebraucht?
			list.remove(0);
		}
	}

	private double cost(double[] output, double[] y) {
		double sum = 0;
		for(int i = 0; i < output.length; i++) {
			double diff = output[i] - y[i];
			sum += diff *  diff;
		}

		return sum;
	}

	@Override
	public String toString() {
		String string = "";

		for(int i = 0; i < inLayerLength; i++)
			string += "O   ";

		string += "B";
		for(int i = 0; i < hiddenLayers.length; i++) {
			string += "\n   |   ";
			for(int j = 0; j < hiddenLayers[i].length; j++) {
				if(j != 0) string += ", \n   |   ";
				string += hiddenLayers[i][j].toString();
			}

			string += "\n";

			for(int j = 0; j < hiddenLayers[i].length; j++)
				string += "O   ";

			string += "B";
		}

		string += "\n   |   ";

		for(int i = 0; i < outputLayer.length; i++) {
			if(i != 0) string += ", \n   |   ";
			string += outputLayer[i].toString();
		}

		string += "\n";

		for(int i = 0; i < outputLayer.length; i++)
			string += "O   ";

		return "Network{\n" + string + "\n}";
	}

	void save(BufferedWriter writer) throws IOException {
		{ // Number of units in each layer
			writer.append("layers;" + inLayerLength);

			for(Neuron[] layer : hiddenLayers)
				writer.append(";" + layer.length);

			writer.append(";" + outputLayer.length + "\n");
		}

		double[][][] temp = new double[hiddenLayers.length + 1][][]; // 1. layers, 2. Länge weights + 1, 3. Anzahl Neuronen

		for(int i = 0; i < temp.length; i++) {
			Neuron[] layer = (i < temp.length - 1) ? hiddenLayers[i] : outputLayer;
			temp[i] = new double[layer[0].getWeights().length][];
			for(int j = 0; j < temp[i].length; j++) {
				temp[i][j] = new double[layer.length];
				for(int k = 0; k < temp[i][j].length; k++) {
					temp[i][j][k] = layer[k].getWeights()[j];
				}
			}
		}

		for(int i = 0; i < temp.length; i++) {
			Neuron[] layer = (i < temp.length - 1) ? hiddenLayers[i] : outputLayer;

			if(i != 0) writer.append("\n\n");
			for(int j = 0; j < temp[i].length; j++) {
				if(j != 0) writer.append("\n");
				for(int k = 0; k < temp[i][j].length; k++) {
					if(k != 0) writer.append("; ");
					writer.append("" + temp[i][j][k]);
				}
			}

			writer.append("\n");
			for(int j = 0; j < layer.length; j++) {
				if(j != 0) writer.append("; ");
				writer.append("" + layer[j].getBias());
			}
		}
	}

	void saveFunctions(BufferedWriter writer) throws IOException {
		Neuron[][] layers = getLayers();

		for(int i = 0; i < layers.length; i++){
			if(i != 0) writer.append("\n#");

			for(int j = 0; j < layers[i].length; j++) {
				if(i != 0 || j != 0) writer.append("\n");

				ActivationFunction function = layers[i][j].getFunction();
				writer.append(function.getClass().getName() + " ");
				function.toBuffer(writer);
			}
		}
	}

	private Neuron[][] getLayers() {
		Neuron[][] layers = new Neuron[hiddenLayers.length + 1][];

		System.arraycopy(hiddenLayers, 0, layers, 0, hiddenLayers.length);
		layers[layers.length - 1] = outputLayer;

		return layers;
	}
}
