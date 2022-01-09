package main.convnet;

import main.Network;

import java.util.ArrayList;

public class ConvNet extends Network{
	private final ArrayList<TrainData> trainData = new ArrayList<>();
	private final ImageAdapter imageAdapter;

	public ConvNet(String path, int imgWidth, int imgHeight, int numOutUnit, int... numHidUnit) {
		super(imgWidth * imgHeight, numOutUnit, numHidUnit);
		imageAdapter = new ImageAdapter(path, imgWidth, imgHeight);
	}

	public ConvNet(String path, int imgWidth, int imgHeight, double[][][] weights, double[][] biases, int numOutUnit, int... numHidUnit) {
		super(imgWidth * imgHeight, numOutUnit, weights, biases, numHidUnit);
		imageAdapter = new ImageAdapter(path, imgWidth, imgHeight);
	}

	public void train(double learnRate, int iterations) {
		super.train(getImages(), getTargets(), learnRate, iterations);
	}

	public void addTrainData(String filename, int[] target) {
		addTrainData(filename, target, false);
	}

	public void addTrainData(String filename, int[] target, boolean convert) {
		trainData.add(new TrainData(imageAdapter.getImageRGBs(filename, convert), target));
	}

	private double[][] getImages() {
		double[][] matrix = new double[trainData.size()][];
		for(int i = 0; i < matrix.length; i++)
			matrix[i] = trainData.get(i).getImageAsDoubleArray();

		return matrix;
	}

	private double[][] getTargets() {
		double[][] matrix = new double[trainData.size()][];
		for(int i = 0; i < matrix.length; i++)
			matrix[i] = trainData.get(i).getTargetAsDoubleArray();

		return matrix;
	}
}
