package main;

import main.algebraoriented.PyNetwork;
import main.mnist.Mnist;
import main.objectoriented.JNetwork;

public class Main {

	public static void main(String[] args) {
		testJMnist();
	}

	private static void testPyXOR() {
		PyNetwork network = new PyNetwork(2, 1, 8, 8);
		double[][] input = new double[][] {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		double[][] labels = new double[][] {{0}, {1}, {1}, {0}};
		network.train(input, labels, 50000, 0.01);
	}

	private static void testJXOR() {
		JNetwork network = new JNetwork(2, 3, 1);
		double[][] input = new double[][] {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		double[][] labels = new double[][] {{0}, {1}, {1}, {0}};
		network.train(input, labels, 10000000, 0.01);
	}

	private static void testPyMnist() {
		PyNetwork network = new PyNetwork(Mnist.TEST_IMAGES[0].length, 10, 16, 16);
		network.train(Mnist.TRAIN_IMAGES, Mnist.TRAIN_LABELS, 1000, 0.01);
	}

	private static void testJMnist() {
		JNetwork network = new JNetwork(Mnist.TEST_IMAGES[0].length, 16, 16, 10);
		network.train(Mnist.TRAIN_IMAGES, Mnist.TRAIN_LABELS, 1000, 0.01);
	}

	private static void testJOR() {
		JNetwork network = new JNetwork(2, 1);
		double[][] input = new double[][] {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		double[][] labels = new double[][] {{1}, {1}, {1}, {0}};
		network.train(input, labels, 100000, 0.1);
	}

	private static void testJAND() {
		JNetwork network = new JNetwork(2, 1);
		double[][] input = new double[][] {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		double[][] labels = new double[][] {{1}, {0}, {0}, {0}};
		network.train(input, labels, 10000, 0.01);
	}
}
