package main.functions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * IMPORTANT: Every subclass of this interface has to implement a constructor without parameters!
 */
public interface ActivationFunction {
	ActivationFunction DEFAULT_FUNCTION = new Sigmoid();

	double function(double x);

	double derivative(double x);

	void toBuffer(BufferedWriter writer) throws IOException;

	void fromBuffer(Scanner scanner) throws IOException;
}
