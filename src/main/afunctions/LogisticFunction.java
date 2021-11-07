package main.afunctions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class LogisticFunction implements ActivationFunction {
	public static final String NAME = "Logistic";
	private double offset, height, factor, ordinate;

	/**
	 * Constructor for loading a {@link LogisticFunction}.
	 * Instead of using this constructor use the {@link SigmoidFunction}.
	 */
	@Deprecated
	public LogisticFunction() {
		this(0, 1, 1, 0);
	}

	public LogisticFunction(double offset, double height, double factor, double ordinate) {
		this.offset = offset;
		this.height = height;
		this.factor = factor;
		this.ordinate = ordinate;
	}

	@Override
	public double function(double x) {
		return height/(1 + Math.exp(-factor * (x - offset)));
	}

	@Override
	public double derivative(double x) {
		double x0 = x - offset;
		return x0 * (1 - x0);
	}

	@Override
	public void toBuffer(BufferedWriter writer) throws IOException {
		writer.append(" " + offset + " " +  height + " " + factor + " " + ordinate);
	}

	@Override
	public void fromBuffer(Scanner scanner) throws IOException {
		offset = scanner.nextDouble();
		height = scanner.nextDouble();
		factor = scanner.nextDouble();
		ordinate = scanner.nextDouble();
	}

	@Override
	public String toString() {
		return NAME + "(" + offset + ", " +  height + ", " + factor + ", " + ordinate + ")";
	}
}
