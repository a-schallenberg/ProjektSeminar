package main.functions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class SemiLinear implements ActivationFunction {
	double lowerLimit, upperLimit;

	public SemiLinear() {
		this(-1, 1);
	}

	public SemiLinear(double lowerLimit, double upperLimit) {
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	@Override
	public double function(double x) {
		if(x < lowerLimit)
			return lowerLimit;
		else if(x > upperLimit)
			return upperLimit;
		else
			return x;
	}

	@Override
	public double derivative(double x) {
		return Double.NaN;
	}

	@Override
	public void toBuffer(BufferedWriter writer) throws IOException {
		writer.append(lowerLimit + " " + upperLimit);
	}

	@Override
	public void fromBuffer(Scanner scanner) throws IOException {
		lowerLimit = scanner.nextDouble();
		upperLimit = scanner.nextDouble();
	}

	@Override
	public String toString() {
		return "SemiLinear{" +
				"lowerLimit=" + lowerLimit +
				", upperLimit=" + upperLimit +
				'}';
	}
}
