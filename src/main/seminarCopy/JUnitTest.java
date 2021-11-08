package main.seminarCopy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.afunctions.ActivationFunction;
import main.afunctions.SignFunction;

import java.io.IOException;
import java.util.Arrays;

public class JUnitTest {

	@Test
	private void testSaveLoad() throws IOException {
		Network network = new Network(3, 2, new SignFunction(1.6, 0, 1), 5, 7);

		NetworkHelper.save(network, "network");
		Network network1 = NetworkHelper.load("network");

		NetworkHelper.save(network1, "network1");
	}

	@Test
	private void testAND() {
		Network network = new Network(2, 1, new double[][][]{{{1, 1}}}, new double[][]{{0}}, new SignFunction(1.5, 0, 1));

		printBinary(network); // 0, 0, 0, 1
	}

	@Test
	private void testOR() {
		Network network = new Network(2, 1, new double[][][]{{{1, 1}}}, new double[][]{{0}}, new SignFunction(0.5, 0, 1));

		printBinary(network); // 0, 1, 1, 1
	}

	@Test
	private void testXOR() {
		double[][][] weights = new double[][][]{new double[][]{new double[]{1,1}, new double[]{-1,-1}}, new double[][]{new double[]{1, 1}}};
		double[][] biases = new double[][]{new double[]{0, 0}, new double[]{0}};
		ActivationFunction[][] functions = new ActivationFunction[][] {{new SignFunction(0.5, 0, 1), new SignFunction(-1.5, 0, 1)}, {new SignFunction(1.5, 0, 1)}}; // OR-Gate, NAND-Gate, AND-Gate

		Network network = new Network(2, 1, weights, biases, functions, 2);

		printBinary(network); // 0, 1, 1, 0
	}

	@Test
	private void trainAND() {
		Network network = new Network(2, 1, new SignFunction(0, 0, 1));

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {0}, {0}, {1}};

		network.train(inputs, 10, y, 10);
	}

	@Test
	private void trainOR() {
		Network network = new Network(2, 1, new SignFunction(0, 0, 1));

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {1}, {1}, {1}};

		network.train(inputs, 100, y, 100);
	}

	@Test
	private void train3OR() {
		Network network = new Network(3, 1, new SignFunction(0, 0, 1));

		double[][] inputs = {{0, 0, 0}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1}, {1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
		double[][] y = {{0}, {1}, {1}, {1}, {1}, {1}, {1}, {1}};

		network.train(inputs, 10, y, 10);
	}

	@Test
	private void trainXOR() {
		Network network = new Network(2, 1, new SignFunction(0, 0, 1), 2);

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {1}, {1}, {0}};

		network.train(inputs, 1, y, 10);
	}

	private static void printBinary(Network network) {
		System.out.println(Arrays.toString(network.compute(new double[]{0, 0})));
		System.out.println(Arrays.toString(network.compute(new double[]{0, 1})));
		System.out.println(Arrays.toString(network.compute(new double[]{1, 0})));
		System.out.println(Arrays.toString(network.compute(new double[]{1, 1})));
	}
}
