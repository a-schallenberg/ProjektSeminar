package main;

import java.io.*;
import java.util.function.Function;

public class StorageManager {
	private static final String PATH = "resources/networkfiles/";

	public static INetwork load(String filename, Function<BufferedReader, INetwork> func) {
		File file = new File(PATH + filename + ".network");

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			return func.apply(reader);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void save(String filename, INetwork network) throws IOException {
		File file = new File(PATH + filename + ".network");
		file.createNewFile();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

		network.toBuffer(writer);

		writer.close();
	}

	private static void writeWeights(BufferedWriter writer, double[][][] weights) throws IOException {
		for(double[][] w0: weights) {
			for(double[] w1 : w0) {
				for(double w : w1)
					writer.append(String.valueOf(w)).append(":");

				writer.append("\n");
			}
			writer.append("\n%\n");
		}
	}

	private static void writeBiases(BufferedWriter writer, double[][] biases) throws IOException {
		for(double[] b0: biases) {
			for(double b : b0)
				writer.append(String.valueOf(b)).append(":");

			writer.append("\n%\n");
		}
	}
}
