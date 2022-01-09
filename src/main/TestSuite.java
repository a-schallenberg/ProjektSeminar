package main;

import main.functions.ActivationFunction;
import main.functions.Identity;
import main.functions.Sign;
import main.functions.SignDiff;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class TestSuite {

	public static void main(String[] args) {}

	@Test
	public void testSaveLoad() throws IOException {
		Network network = new Network(3, 1, 4, 6);
		NetworkHelper.save(network, "test");

		Network network1 = NetworkHelper.load("test");
		NetworkHelper.save(network1, "test1");
	}

	@Test
	public void testAND() {
		Network network = new Network(2, 1, new double[][][]{{{1, 1}}}, new double[][]{{0}}, new Sign(1.5, 0d, 1d));
		printCoupleGate(network); // 0, 0, 0, 1
	}

	@Test
	public void testOR() {
		Network network = new Network(2, 1, new double[][][]{{{1, 1}}}, new double[][]{{0}}, new Sign(0.5, 0d, 1d));
		printCoupleGate(network); // 0, 1, 1, 1
	}

	@Test
	public void testXOR() {
		double[][][] weights = new double[][][]{new double[][]{new double[]{1,1}, new double[]{-1,-1}}, new double[][]{new double[]{1, 1}}};
		double[][] biases = new double[][]{new double[]{0, 0}, new double[]{0}};
		ActivationFunction[][] functions = new ActivationFunction[][] {{new Sign(0.5, 0d, 1d), new Sign(-1.5, 0d, 1d)}, {new Sign(1.5, 0d, 1d)}}; // OR-Gate, NAND-Gate, AND-Gate

		Network network = new Network(2, 1, weights, biases, functions, 2);

		printCoupleGate(network); // 0, 1, 1, 0
	}

	@Test
	public void trainAND() {
		Network network = new Network(2, 1, new SignDiff(0d, 0d, 1d));

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {0}, {0}, {1}};

		network.train(inputs, y, 10, 10);
	}

	@Test
	public void trainOR() {
		Network network = new Network(2, 1, new SignDiff(0d, 0d, 1d));

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {1}, {1}, {1}};

		network.train(inputs, y, 1, 1000);
	}

	@Test
	public void train3OR() {
		Network network = new Network(3, 1, new SignDiff(0d, 0d, 1d));

		double[][] inputs = {{0, 0, 0}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1}, {1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
		double[][] y = {{0}, {1}, {1}, {1}, {1}, {1}, {1}, {1}};

		network.train(inputs, y, 10, 10);
	}

	@Test
	public void trainXOR() {
		Network network = new Network(2, 1, new Identity(), 2);

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {1}, {1}, {0}};

		network.train(inputs, y, 1E-5, (int) 1E10);
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
