package main;

import main.afunctions.ActivationFunction;
import main.afunctions.SignFunction;
import main.algebraoriented.PyNetwork;
import main.mnist.Mnist;
import main.objectoriented.JNetwork;
import main.util.Util;

import java.io.IOException;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws IOException {
//		JNetwork network = new JNetwork(3, 7, 6, 2);
//
//		StorageManager.save("JNetwork", network);
//
//		INetwork network2 = StorageManager.load("JNetwork", JNetwork::new);
//
//		System.out.println(Arrays.toString(network2.compute(new double[]{1, 2, 3})));
//
//		StorageManager.save("INetwork", network2);


		//trainPyXOR();
		trainJXOR();

		//testArgmax();
		//new Network(1,1, 5, 8, 4, 2);
	}

	private static void trainPyXOR() {
		PyNetwork network = new PyNetwork(2, 1, 3);
		double[][] input = new double[][] {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		double[][] labels = new double[][] {{0}, {1}, {1}, {0}};
		network.train(input, labels, 50000, 0.1);
	}

	private static void trainJXOR() {
		JNetwork network = new JNetwork(2, 3, 1);
		double[][] input = new double[][] {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		double[][] labels = new double[][] {{0}, {1}, {1}, {0}};
		network.train(input, labels, 100000, 0.1);
	}

	private static void trainPyMnist() {
		PyNetwork network = new PyNetwork(Mnist.TRAIN_IMAGES[0].length, 10, 16, 16);
		network.train(Mnist.TRAIN_IMAGES, Mnist.TRAIN_LABELS, 10, 0.01);
	}

	private static void trainJMnist() {
		JNetwork network = new JNetwork(Mnist.TRAIN_IMAGES[0].length, 16, 16, 10);
		network.train(Mnist.TRAIN_IMAGES, Mnist.TRAIN_LABELS, 10, 0.01);
	}

	private static void trainJOR() {
		JNetwork network = new JNetwork(2, 1);
		double[][] input = new double[][] {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		double[][] labels = new double[][] {{1}, {1}, {1}, {0}};
		network.train(input, labels, 100000, 0.01);
	}

	private static void trainJAND() {
		JNetwork network = new JNetwork(2, 1);
		double[][] input = {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		double[][] labels = {{1}, {0}, {0}, {0}};
		network.train(input, labels, 10000, 0.01);
	}

	private static void testJOR() {
		JNetwork network = new JNetwork(new SignFunction(0.5, 0, 1), new double[][][]{new double[][]{new double[]{1,1}}}, new double[][]{new double[]{0}});
		double[][] input = new double[][] {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		for(double[] in: input)
			System.out.println(Arrays.toString(in) + ": " + Arrays.toString(network.compute(in)));
	}

	private static void testJAND() {
		JNetwork network = new JNetwork(new SignFunction(1.5, 0, 1), new double[][][]{new double[][]{new double[]{1,1}}}, new double[][]{new double[]{0}});
		double[][] input = new double[][] {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		for(double[] in: input)
			System.out.println(Arrays.toString(in) + ": " + Arrays.toString(network.compute(in)));
	}

	private static void testJXOR() {
		double[][][] weights = new double[][][]{new double[][]{new double[]{1,1}, new double[]{-1,-1}}, new double[][]{new double[]{1, 1}}};
		double[][] biases = new double[][]{new double[]{0, 0}, new double[]{0}};
		ActivationFunction[][] funcs = {{new SignFunction(0.5, 0, 1), new SignFunction(-1.5, 0, 1)}, {new SignFunction(1.5, 0, 1)}};

		JNetwork network = new JNetwork(funcs, weights, biases);
		double[][] input = new double[][] {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		for(double[] in: input)
			System.out.println(Arrays.toString(in) + ": " + Arrays.toString(network.compute(in)));
	}

	private static void testArgmax() {
		for(int i = 0; i < 10; i++){
			double[] array = Util.random(10, 0d, 10d);
			System.out.println(Arrays.toString(array));
			System.out.println(Util.argmax(array) + "\n");
		}
	}

	private static void fixJAND() {
		JNetwork network = new JNetwork(new SignFunction(1.5, 0, 1), new double[][][]{new double[][]{new double[]{0.5,1.5}}}, new double[][]{new double[]{0.5}});
		double[][] input = new double[][] {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		double[][] labels = new double[][] {{1}, {0}, {0}, {0}};

		System.out.println(network);
		network.train(input, labels, 5, 0.1);
	}
}
