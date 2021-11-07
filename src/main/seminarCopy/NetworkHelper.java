package main.seminarCopy;

import main.afunctions.ActivationFunction;
import main.util.Quintuple;
import main.util.Util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Scanner;

public class NetworkHelper {
	public static final String PATH = "resources/networkfiles/";
	public static final String BASICS = "_basics.csv";
	public static final String FUNCTIONS = "_functions.csv";

	public static void removeFile(String filename) {
		(new File(path(filename, true))).delete();
		(new File(path(filename, false))).delete();
	}

	public static void save(Network network, String filename) throws IOException {
		saveWeights(network, filename);
		saveFunctions(network, filename);
	}

	private static void saveWeights(Network network, String filename) throws IOException {
		File file = new File(path(filename, true));
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

		network.saveBasics(writer);

		writer.close();
	}

	private static void saveFunctions(Network network, String filename) throws IOException {
		File file = new File(path(filename, false));
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

		network.saveFunctions(writer);

		writer.close();
	}

	public static Network load(String filename) throws IOException {
		Quintuple<Integer, Integer, int[], double[][][], double[][]> basics = loadBasics(filename);
		ActivationFunction[][] functions = loadFunctions(filename);

		if(functions == null)
			return new Network(basics.t, basics.u, basics.w, basics.x, basics.v);

		return new Network(basics.t, basics.u, basics.w, basics.x, functions, basics.v);
	}

	private static Quintuple<Integer, Integer, int[], double[][][], double[][]> loadBasics(String filename) throws IOException {
		BufferedReader reader  = new BufferedReader(new FileReader(path(filename, true)));

		String[] raw = reader.readLine().replaceAll(" ", "").split(";");
		int inputLength = Integer.parseInt(raw[1]);
		int outputLength = Integer.parseInt(raw[raw.length - 1]);
		int[] hiddenLengths = new int[raw.length - 3]; // - ("labels", input, output)
		LinkedList<double[][]> weights = new LinkedList<>();
		LinkedList<double[]> biases = new LinkedList<>();

		for(int i = 0; i < hiddenLengths.length; i++)
			hiddenLengths[i] = Integer.parseInt(raw[i + 2]);

		while(reader.ready()) {
			LinkedList<double[]> rawWeights = new LinkedList<>();
			String rawString;
			while((rawString = reader.readLine()) != null && !(raw = rawString.replaceAll(" ", "").split(";"))[0].equals("")) {
				double[] w = new double[raw.length];
				for(int i = 0; i < raw.length; i++)
					w[i] = Double.parseDouble(raw[i]);

				rawWeights.add(w);
			}

			biases.add(rawWeights.removeLast());
			weights.add(Util.transpose(rawWeights.toArray(double[][]::new)));
		}

		reader.close();
		return new Quintuple<>(inputLength, outputLength, hiddenLengths, weights.toArray(double[][][]::new), biases.toArray(double[][]::new));
	}

	private static ActivationFunction[][] loadFunctions(String filename) throws IOException {
		Scanner scanner;
		try {
			scanner = new Scanner(new File(path(filename, false)));
		} catch(FileNotFoundException e) {
			return null;
		}

		LinkedList<LinkedList<ActivationFunction>> functions = new LinkedList<>();
		functions.add(new LinkedList<>());

		while(scanner.hasNext()) {
			String name;
			if((name = scanner.next()).equals("#"))
				functions.add(new LinkedList<>());
			else {
				try {
					Class<ActivationFunction> clazz = (Class<ActivationFunction>) ClassLoader.getSystemClassLoader().loadClass(name);
					ActivationFunction function = clazz.getConstructor().newInstance();
					function.fromBuffer(scanner);
					functions.getLast().add(function);
				} catch(NoSuchMethodException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
					functions.getLast().add(ActivationFunction.DEFAULT_FUNCTION);
					e.printStackTrace();
				}
			}
		}

		ActivationFunction[][] funcs = new ActivationFunction[functions.size()][];

		for(int i = 0; i < funcs.length; i++)
			funcs[i] = functions.removeFirst().toArray(ActivationFunction[]::new);

		scanner.close();

		return funcs;
	}

	private static String path(String filename, boolean basics) {
		return PATH + filename + (basics ? BASICS : FUNCTIONS);
	}
}
