package main.mnist;

import java.util.List;

public class Mnist {
	public static final double[][] TEST_IMAGES = transformImages("resources/data/t10k-images.idx3-ubyte");
	public static final double[][] TEST_LABELS = transformLabels("resources/data/t10k-labels.idx1-ubyte");
	public static final double[][] TRAIN_IMAGES = transformImages("resources/data/train-images.idx3-ubyte");
	public static final double[][] TRAIN_LABELS = transformLabels("resources/data/train-labels.idx1-ubyte");

	private static double[][] transformImages(String infile) {
		List<int[][]> list = MnistReader.getImages(infile);
		double[][] inputs = new double[list.size()][list.get(0).length * list.get(0)[0].length];

		for(int i = 0; i < inputs.length; i++)
			for(int j = 0; j < list.get(i).length; j++) {
				int size = list.get(i)[j].length;
				for(int k = 0; k < size; k++)
					inputs[i][j * size + k] = list.get(i)[j][k]/255d;
			}
		return inputs;
	}

	private static double[][] transformLabels(String infile) {
		int[] array = MnistReader.getLabels(infile);
		double[][] labels = new double[array.length][10];

		for(int i = 0; i < array.length; i++)
			labels[i][array[i]] = 1;

		return labels;
	}
}
