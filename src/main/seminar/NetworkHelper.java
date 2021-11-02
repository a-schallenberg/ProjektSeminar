package main.seminar;

import java.io.*;
import java.util.Scanner;

public class NetworkHelper {

	public static void save(Network network, String filename) throws IOException {
		File file = new File("resources/networkfiles/" + filename + ".network");
		file.createNewFile();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

		network.save(writer);

		writer.close();
	}

	public static Network load(String filename) throws FileNotFoundException {
		Scanner scanner  = new Scanner(new File("resources/networkfiles/" + filename + ".network"));

		Network network = Network.load(scanner);
		scanner.close();

		return network;
	}
}
