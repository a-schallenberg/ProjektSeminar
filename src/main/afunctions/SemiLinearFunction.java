package main.afunctions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class SemiLinearFunction implements ActivationFunction{
	public static final String NAME = "Semi-Linear";

	private double lowerLimit, upperLimit;

	public SemiLinearFunction() {
		this(-1, 1);
	}

	public SemiLinearFunction(double lowerLimit, double upperLimit) {
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	@Override
	public double function(double x) {
		if(x <= lowerLimit) return lowerLimit;
		if(x >= upperLimit) return upperLimit;
		return x;
	}

	@Override
	public double derivative(double x) {
		return x == lowerLimit || x == upperLimit ? Double.NaN : (x < lowerLimit || x > upperLimit ? 0 : 1 );
	}

	@Override
	public void toBuffer(BufferedWriter writer) throws IOException {
		writer.append(" " + lowerLimit + " " + upperLimit);
	}

	@Override
	public void fromBuffer(Scanner scanner) throws IOException {
		lowerLimit = scanner.nextDouble();
		upperLimit = scanner.nextDouble();
	}

	@Override
	public String toString() {
		return NAME + "(" + lowerLimit + " | " + upperLimit + ")";
	}
}
