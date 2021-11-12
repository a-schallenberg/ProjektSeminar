package main;

import java.io.*;
import java.util.Scanner;

public class NetworkHelper {

	public static void save(Network network, String filename) throws IOException {
		File file = new File("resources/networkfiles/" + filename + "_basics.csv");
		file.createNewFile();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

		network.save(writer);

		writer.close();
	}

	public static Network load(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("resources/networkfiles/" + filename + "_basics.csv"));

			String[] raw;
			raw = reader.readLine().split(";");

			int inputLength = Integer.parseInt(raw[1]);

			int[] hiddenLengths = new int[raw.length - 3];
			for(int i = 0; i < hiddenLengths.length; i++)
				hiddenLengths[i] = Integer.parseInt(raw[i + 2]);

			int outputLength = Integer.parseInt(raw[raw.length - 1]);



		double[][][] weights = new double[hiddenLengths.length + 1][][];
		double[][] biases = new double[hiddenLengths.length + 1][];
		for(int i = 0; i < weights.length; i++) { // Layers
			weights[i] = new double[(i < hiddenLengths.length) ? hiddenLengths[i] : outputLength][(i == 0) ? inputLength : hiddenLengths[i-1]];
			for(int j = 0; j < weights[i][0].length; j++) { // Zeilen
				raw = reader.readLine().replaceAll(" ", "").split(";");
				for(int k = 0; k < weights[i].length; k++) { // Neuronen
					weights[i][k][j] = Double.parseDouble(raw[k]);
				}
			}

			raw = reader.readLine().replaceAll(" ", "").split(";");
			biases[i] = new double[raw.length];
			for(int j = 0; j < weights[i].length; j++) { // Neuronen
				biases[i][j] = Double.parseDouble(raw[j]);
			}

			if(reader.ready()) reader.readLine();
		}

		reader.close();

		return new Network(inputLength, outputLength, weights, biases, hiddenLengths);
	}
}
