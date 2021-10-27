package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public interface INetwork {

	/**
	 * Computes the output vector via the neuronal network.
	 * @param input The input vector for computing the output vector.
	 * @return The output vector.
	 */
	double[] compute(double[] input);

	void train(double[][] inputVectors, double[][] labels, int repetitions, double learnRate);

	void fromBuffer(BufferedReader reader) throws IOException;

	void toBuffer(BufferedWriter writer) throws IOException;
}
