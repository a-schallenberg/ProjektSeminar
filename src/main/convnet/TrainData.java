package main.convnet;

public record TrainData(int[] image, int[] target) {

	public double[] getImageAsDoubleArray() {
		return getIntAsDoubleArray(image);
	}

	public double[] getTargetAsDoubleArray() {
		return getIntAsDoubleArray(target);
	}

	private static double[] getIntAsDoubleArray(int[] array) {
		double[] result = new double[array.length];
		for(int i = 0; i < array.length; i++)
			result[i] = array[i];

		return result;
	}
}
