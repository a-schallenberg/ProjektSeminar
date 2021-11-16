package main.functions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Sigmoid extends Logistic {

	public Sigmoid() {
		super(0, 0, 1, 1);
	}

	@Override
	public double function(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	@Override
	public double derivative(double x) {
		double x0 = function(x);
		return x0 * (1 - x0);
	}

	@Override
	public void toBuffer(BufferedWriter writer) throws IOException {}

	@Override
	public void fromBuffer(Scanner scanner) throws IOException {}

	@Override
	public String toString() {
		return "Sigmoid";
	}
}
