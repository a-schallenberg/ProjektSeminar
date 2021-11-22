package main.functions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Logistic implements ActivationFunction{
	double xOffset, yOffset, height, factor;

	@Deprecated
	public Logistic() {
		this(0, 0, 1, 1);
	}

	public Logistic(double xOffset, double yOffset, double height, double factor) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.height = height;
		this.factor = factor;
	}

	@Override
	public double function(double x) {
		return height / (1 + Math.exp(-factor * (x - xOffset))) + yOffset;
	}

	@Override
	public double derivative(double x) {
		double e = Math.exp(factor * (x - xOffset));
		return factor * height * e / Math.pow(e + 1, 2); //TODO done
	}

	@Override
	public void toBuffer(BufferedWriter writer) throws IOException {
		writer.append(xOffset + " " + yOffset + " " + height + " " + factor);
	}

	@Override
	public void fromBuffer(Scanner scanner) throws IOException {
		xOffset = scanner.nextDouble();
		yOffset = scanner.nextDouble();
		height = scanner.nextDouble();
		factor = scanner.nextDouble();
	}

	@Override
	public String toString() {
		return "Logistic{" +
				"xOffset=" + xOffset +
				", yOffset=" + yOffset +
				", height=" + height +
				", factor=" + factor +
				'}';
	}
}
