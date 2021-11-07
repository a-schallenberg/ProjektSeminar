package main.afunctions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class IdentityFunction implements ActivationFunction {
	public static final String NAME = "Identity";

	@Override
	public double function(double x) {
		return x;
	}

	@Override
	public double derivative(double x) {
		return 1;
	}

	@Override
	public void toBuffer(BufferedWriter writer) throws IOException {}

	@Override
	public void fromBuffer(Scanner scanner) {}

	@Override
	public String toString() {
		return NAME;
	}
}
