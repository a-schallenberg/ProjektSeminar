package main.afunctions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class TangensHyperbolicus implements ActivationFunction {
	public static final String NAME = "Tanh";

	@Override
	public double function(double x) {
		return Math.tanh(x);
	}

	@Override
	public double derivative(double x) {
		double tanh = Math.tanh(x);
		return 1 - tanh * tanh;
	}

	@Override
	public void toBuffer(BufferedWriter writer) throws IOException {}

	@Override
	public void fromBuffer(Scanner scanner) throws IOException {}

	@Override
	public String toString() {
		return NAME;
	}
}
