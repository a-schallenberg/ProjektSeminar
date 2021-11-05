package main.seminarCopy;

import main.util.Tuple;

import java.io.*;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.function.Function;

public class NetworkHelper {

	public static void save(Network network, String filename) throws IOException {
		saveWeights(network, filename);
		//saveFunctions(network, filename);
	}

	private static void saveWeights(Network network, String filename) throws IOException {
		File file = new File("resources/networkfiles/" + filename + "_weights.csv");
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

		network.save(writer);

		writer.close();
	}

	private static void saveFunctions(Network network, String filename) throws IOException {
		File file = new File("resources/networkfiles/" + filename + "_functions.csv");
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

		// TODO implement

		writer.close();
	}

	public static Network load(String filename) throws FileNotFoundException {

		Network network = null; // TODO implement


		return network;
	}

	public static Tuple<double[][], double[]> loadWeights(String filename) throws IOException {
		BufferedReader reader  = new BufferedReader(new FileReader("resources/networkfiles/" + filename + "_weights.csv"));
		int inputLength, outputLength;
		LinkedList<Integer> hiddenLengths = new LinkedList<>();

		// TODO implement;

		reader.close();

		return null;
	}

	public static Tuple<Function<Double, Double>, Function<Double, Double>> loadFunctions(String filename) throws FileNotFoundException {
		Scanner scanner  = new Scanner(new File("resources/networkfiles/" + filename + "_functions.csv"));

		// TODO implement

		scanner.close();

		return null;
	}
}
