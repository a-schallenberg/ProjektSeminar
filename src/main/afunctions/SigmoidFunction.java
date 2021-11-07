package main.afunctions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class SigmoidFunction extends LogisticFunction {
	public static final String NAME = "Sigmoid";

	public SigmoidFunction() {
		super(0, 1, 1, 0);
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
