package main;

import main.functions.ActivationFunction;
import main.functions.Sign;
import main.functions.SignDiff;

import java.io.IOException;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) throws IOException {
		trainXOR();
	}

	// Funktioniert
	private static void testSaveLoad() throws IOException {
		Network network = new Network(3, 1, 4, 6);
		NetworkHelper.save(network, "test");

		Network network1 = NetworkHelper.load("test");
		NetworkHelper.save(network1, "test1");
	}

	// Funktioniert
	private static void testAND() {
		Network network = new Network(2, 1, new double[][][]{{{1, 1}}}, new double[][]{{0}}, new Sign(1.5, 0d, 1d));
		printCoupleGate(network); // 0, 0, 0, 1
	}

	// Funktioniert
	private static void testOR() {
		Network network = new Network(2, 1, new double[][][]{{{1, 1}}}, new double[][]{{0}}, new Sign(0.5, 0d, 1d));
		printCoupleGate(network); // 0, 1, 1, 1
	}

	// Funktioniert
	private static void testXOR() {
		double[][][] weights = new double[][][]{new double[][]{new double[]{1,1}, new double[]{-1,-1}}, new double[][]{new double[]{1, 1}}};
		double[][] biases = new double[][]{new double[]{0, 0}, new double[]{0}};
		ActivationFunction[][] functions = new ActivationFunction[][] {{new Sign(0.5, 0d, 1d), new Sign(-1.5, 0d, 1d)}, {new Sign(1.5, 0d, 1d)}}; // OR-Gate, NAND-Gate, AND-Gate

		Network network = new Network(2, 1, weights, biases, functions, 2);

		printCoupleGate(network); // 0, 1, 1, 0
	}

	// Funktioniert
	private static void trainAND() {
		Network network = new Network(2, 1, new SignDiff(0d, 0d, 1d));

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {0}, {0}, {1}};

		network.train(inputs, y, 10, 10);
	}

	// Funktioniert
	private static void trainOR() {
		Network network = new Network(2, 1, new SignDiff(0d, 0d, 1d));

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {1}, {1}, {1}};

		network.train(inputs, y, 100, 100);
	}

	// Funktioniert
	private static void train3OR() {
		Network network = new Network(3, 1, new SignDiff(0d, 0d, 1d));

		double[][] inputs = {{0, 0, 0}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1}, {1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
		double[][] y = {{0}, {1}, {1}, {1}, {1}, {1}, {1}, {1}};

		network.train(inputs, y, 10, 10);
	}

	// Training funktioniert nicht korrekt
	private static void trainXOR() {
		Network network = new Network(2, 1, new SignDiff(0d, 0d, 1d));

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {1}, {1}, {0}};

		network.train(inputs, y, 1, 10);
	}

	/**
	 * Helper method to print a {@link Network} with a two-dimensional input vector.
	 * @param network {@link Network} which results of computing are printed.
	 */
	private static void printCoupleGate(Network network) {
		System.out.println(Arrays.toString(network.compute(new double[]{0, 0})));
		System.out.println(Arrays.toString(network.compute(new double[]{0, 1})));
		System.out.println(Arrays.toString(network.compute(new double[]{1, 0})));
		System.out.println(Arrays.toString(network.compute(new double[]{1, 1})));
	}
}
