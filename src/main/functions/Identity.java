package main.functions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Identity implements ActivationFunction {

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
	public void fromBuffer(Scanner scanner) throws IOException {}

	@Override
	public String toString() {
		return "Identity";
	}
}
