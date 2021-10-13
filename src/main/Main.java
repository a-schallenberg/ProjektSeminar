package main;

import main.algebraoriented.PyNetwork;
import main.mnist.Mnist;

public class Main {

	public static void main(String[] args) {
//		double[][] matrix = new double[][]{{1, 2}};
//		double[] vec = new double[] {1, 1};
//
//		System.out.println(Arrays.toString(main.Util.mul(matrix, vec)));

//		PyNetwork network = new PyNetwork(Mnist.TEST_IMAGES[0].length, 10, 512, 512);
//		network.train(Mnist.TEST_IMAGES, Mnist.TEST_LABELS, 50, 0.1);

		PyNetwork network = new PyNetwork(2, 1, 4);
		double[][] input = new double[][] {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
		double[][] labels = new double[][] {{0}, {1}, {1}, {0}};
		network.train(input, labels, 50, 0.1);
	}
}
