package main;

import java.util.Arrays;
import java.util.function.Function;

public class Test {

	public static void main(String[] args) {
		trainXOR();
	}

	private static void testAND() {
		Network network = new Network(2, 1, new double[][][]{{{1, 1}}}, new double[][]{{0}}, x -> x < 1.5 ? 0d : 1d, null);

		System.out.println(Arrays.toString(network.compute(new double[]{0, 0}))); // 0
		System.out.println(Arrays.toString(network.compute(new double[]{0, 1}))); // 0
		System.out.println(Arrays.toString(network.compute(new double[]{1, 0}))); // 0
		System.out.println(Arrays.toString(network.compute(new double[]{1, 1}))); // 1
	}

	private static void testOR() {
		Network network = new Network(2, 1, new double[][][]{{{1, 1}}}, new double[][]{{0}}, x -> x < 0.5 ? 0d : 1d, null);

		System.out.println(Arrays.toString(network.compute(new double[]{0, 0}))); // 0
		System.out.println(Arrays.toString(network.compute(new double[]{0, 1}))); // 0
		System.out.println(Arrays.toString(network.compute(new double[]{1, 0}))); // 0
		System.out.println(Arrays.toString(network.compute(new double[]{1, 1}))); // 1
	}

	private static void testXOR() {
		double[][][] weights = new double[][][]{new double[][]{new double[]{1,1}, new double[]{-1,-1}}, new double[][]{new double[]{1, 1}}};
		double[][] biases = new double[][]{new double[]{0, 0}, new double[]{0}};
		Function<Double, Double>[][] functions = new Function[][] {{x -> ((Double) x) < 0.5 ? 0d : 1d, x -> ((Double) x) < -1.5 ? 0d : 1d}, {x -> ((Double) x) < 1.5 ? 0d : 1d}}; // OR-Gate, NAND-Gate, AND-Gate

		Network network = new Network(2, 1, weights, biases, functions, null, 2);

		System.out.println(Arrays.toString(network.compute(new double[]{0, 0}))); // 0
		System.out.println(Arrays.toString(network.compute(new double[]{0, 1}))); // 1
		System.out.println(Arrays.toString(network.compute(new double[]{1, 0}))); // 1
		System.out.println(Arrays.toString(network.compute(new double[]{1, 1}))); // 0
	}

	private static void trainAND() {
		Network network = new Network(2, 1, x -> x < 0 ? 0d : 1d, x -> 1d);

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {0}, {0}, {1}};

		network.train(inputs, y, 10, 10);
	}

	private static void trainOR() {
		Network network = new Network(2, 1, x -> x < 0 ? 0d : 1d, x -> 1d);

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {1}, {1}, {1}};

		network.train(inputs, y, 100, 100);
	}

	private static void train3OR() {
		Network network = new Network(3, 1, x -> x < 0 ? 0d : 1d, x -> 1d);

		double[][] inputs = {{0, 0, 0}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1}, {1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
		double[][] y = {{0}, {1}, {1}, {1}, {1}, {1}, {1}, {1}};

		network.train(inputs, y, 10, 10);
	}

	private static void trainXOR() {
		Network network = new Network(2, 1, x -> x < 0 ? 0d : 1d, x -> 1d, 2);

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {1}, {1}, {0}};

		network.train(inputs, y, 1, 10);
	}

}
