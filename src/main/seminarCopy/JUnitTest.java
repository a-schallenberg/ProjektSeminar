package main.seminarCopy;

import static org.junit.jupiter.api.Assertions.*;

import main.mnist.Mnist;
import main.objectoriented.JNetwork;
import org.junit.jupiter.api.Test;

import main.afunctions.ActivationFunction;
import main.afunctions.SignFunction;

import java.io.IOException;
import java.util.Arrays;

public class JUnitTest {

	@Test
	void testSaveLoad() throws IOException {
		Network network = new Network(3, 2, new SignFunction(1.6, 0, 1), 5, 7);

		NetworkHelper.save(network, "network");
		Network network1 = NetworkHelper.load("network");

		NetworkHelper.save(network1, "network1");
	}

	@Test
	void testAND() {
		Network network = new Network(2, 1, new double[][][]{{{1, 1}}}, new double[][]{{0}}, new SignFunction(1.5, 0, 1));

		printBinary(network); // 0, 0, 0, 1
	}

	@Test
	void testOR() {
		Network network = new Network(2, 1, new double[][][]{{{1, 1}}}, new double[][]{{0}}, new SignFunction(0.5, 0, 1));

		printBinary(network); // 0, 1, 1, 1
	}

	@Test
	void testXOR() {
		double[][][] weights = new double[][][]{new double[][]{new double[]{1,1}, new double[]{-1,-1}}, new double[][]{new double[]{1, 1}}};
		double[][] biases = new double[][]{new double[]{0, 0}, new double[]{0}};
		ActivationFunction[][] functions = new ActivationFunction[][] {{new SignFunction(0.5, 0, 1), new SignFunction(-1.5, 0, 1)}, {new SignFunction(1.5, 0, 1)}}; // OR-Gate, NAND-Gate, AND-Gate

		Network network = new Network(2, 1, weights, biases, functions, 2);

		printBinary(network); // 0, 1, 1, 0
	}

	@Test
	void trainAND() {
		Network network = new Network(2, 1, new SignFunction(0, 0, 1));

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {0}, {0}, {1}};

		network.train(inputs, 10, y, 10);
	}

	@Test
	void trainOR() {
		Network network = new Network(2, 1, new SignFunction(0, 0, 1));

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {1}, {1}, {1}};

		network.train(inputs, 100, y, 100);
	}

	@Test
	void train3OR() {
		Network network = new Network(3, 1, new SignFunction(0, 0, 1));

		double[][] inputs = {{0, 0, 0}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1}, {1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
		double[][] y = {{0}, {1}, {1}, {1}, {1}, {1}, {1}, {1}};

		network.train(inputs, 10, y, 10);
	}

	@Test
	void trainXOR() {
		Network network = new Network(2, 1, new SignFunction(0, 0, 1), 2);

		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] y = {{0}, {1}, {1}, {0}};

		network.train(inputs, 1, y, 10);
	}

	@Test
	void trainMnist() {
		Network network = new Network(Mnist.TRAIN_IMAGES[0].length, 10, 16, 16);
		network.train(Mnist.TRAIN_IMAGES, 0.01, Mnist.TRAIN_LABELS, 1000);
	}

	private static void printBinary(Network network) {
		System.out.println(Arrays.toString(network.compute(new double[]{0, 0})));
		System.out.println(Arrays.toString(network.compute(new double[]{0, 1})));
		System.out.println(Arrays.toString(network.compute(new double[]{1, 0})));
		System.out.println(Arrays.toString(network.compute(new double[]{1, 1})));
	}

	public static String neuronsToAddress(Neuron[][] hidden, Neuron[] output) {
		String s = "[";
		for(int i = 0; i < hidden.length; i++) {
			if(i != 0) s += ", ";
			s += "[";
			for(int j = 0; j < hidden[i].length; j++) {
				if(j != 0) s += ", ";
				s += hidden[i][j];
			}
			s += "]";
		}

		s += "[";
		for(int i = 0; i < output.length; i++) {
			if(i != 0) s += ", ";
			s += output[i];
		}
		s += "]]";

		return s;
	}
}
