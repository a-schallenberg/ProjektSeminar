package main.functions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Sign implements ActivationFunction{
	private double threshold, lowerLimit, upperLimit;

	public Sign() {
		this(0d);
	}

	public Sign(double threshold) {
		this(threshold, -1d, 1d);
	}

	public Sign(double threshold, double lowerLimit, double upperLimit) {
		this.threshold = threshold;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	@Override
	public double function(double x) {
		return (x < threshold) ? lowerLimit : upperLimit;
	}

	@Override
	public double derivative(double x) {
		return Double.NaN;
	}

	@Override
	public void toBuffer(BufferedWriter writer) throws IOException {
		writer.append(threshold + " " + lowerLimit + " " + upperLimit);
	}

	@Override
	public void fromBuffer(Scanner scanner) throws IOException {
		threshold = scanner.nextDouble();
		lowerLimit = scanner.nextDouble();
		upperLimit = scanner.nextDouble();
	}

	@Override
	public String toString() {
		return "Sign{" +
				"threshold=" + threshold +
				", lowerLimit=" + lowerLimit +
				", upperLimit=" + upperLimit +
				'}';
	}
}
