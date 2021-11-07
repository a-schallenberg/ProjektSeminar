package main.afunctions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class SignFunction implements ActivationFunction {
	public static final String NAME = "Sign";
	private double threshold, lowerLimit, upperLimit;

	public SignFunction(){
		this(0);
	}

	public SignFunction(double threshold) {
		this(threshold, -1, 1);
	}

	public SignFunction(double threshold, double lowerLimit, double upperLimit) {
		this.threshold = threshold;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	@Override
	public double function(double x) {
		return x < threshold ? lowerLimit : upperLimit;
	}

	@Override
	public double derivative(double x) {
		return 0;
	}

	@Override
	public void toBuffer(BufferedWriter writer) throws IOException {
		writer.append(" " + threshold + " " + lowerLimit + " " + upperLimit);
	}

	@Override
	public void fromBuffer(Scanner scanner) throws IOException {
		threshold = scanner.nextDouble();
		lowerLimit = scanner.nextDouble();
		upperLimit = scanner.nextDouble();
	}

	@Override
	public String toString() {
		return NAME + "(" + threshold + " | " + lowerLimit + " | " + upperLimit + ")";
	}
}
