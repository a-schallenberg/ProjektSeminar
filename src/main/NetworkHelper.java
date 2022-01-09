package main;

import main.functions.ActivationFunction;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Scanner;

public class NetworkHelper {

	public static void save(Network network, String filename) throws IOException {
		File file = new File("resources/networkfiles/" + filename + ".csv");
		file.createNewFile();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

		network.save(writer);

		saveFunctions(network, filename);

		writer.close();
	}

	private static void saveFunctions(Network network, String filename) throws IOException {
		File file = new File("resources/networkfiles/" + filename + ".fct");
		file.createNewFile();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

		network.saveFunctions(writer);

		writer.close();
	}

	public static Network load(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("resources/networkfiles/" + filename + ".csv"));

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

		ActivationFunction[][] functions = loadFunctions(filename);

		if(functions == null)
			return new Network(inputLength, outputLength, weights, biases, hiddenLengths);

		return new Network(inputLength, outputLength, weights, biases, functions, hiddenLengths);
	}

	private static ActivationFunction[][] loadFunctions(String filename) throws IOException {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("resources/networkfiles/" + filename + ".fct"));
		} catch(FileNotFoundException e) {
			return null;
		}

		LinkedList<LinkedList<ActivationFunction>> functions = new LinkedList<>();
		functions.add(new LinkedList<>());

		while(scanner.hasNext()) {
			String name = scanner.next();
			if(name.equals("#"))
				functions.add(new LinkedList<>());
			else {
				try {
					Class<ActivationFunction> clazz = (Class<ActivationFunction>) ClassLoader.getSystemClassLoader().loadClass(name);
					ActivationFunction function = clazz.getConstructor().newInstance();
					function.fromBuffer(scanner);
					functions.getLast().add(function);
				} catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
					functions.getLast().add(ActivationFunction.DEFAULT_FUNCTION);
					e.printStackTrace();
				}
			}
		}
		scanner.close();

		ActivationFunction[][] funcs = new ActivationFunction[functions.size()][];

		for(int i = 0; i < funcs.length; i++)
			funcs[i] = functions.removeFirst().toArray(ActivationFunction[]::new);

		return funcs;
	}
}
