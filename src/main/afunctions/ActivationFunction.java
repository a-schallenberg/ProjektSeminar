package main.afunctions;

public interface ActivationFunction extends OutputFunction {

	double derivative(double x);

	default double[] derivative(double[] d) {
		return OutputFunction.array(d, this::derivative);
	}

	String toString();
}
