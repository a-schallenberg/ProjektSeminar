package main.afunctions;

public class SigmoidFunction implements AFunction{
	@Override
	public double function(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	@Override
	public double derivative(double x) {
		double sx = function(x);
 		return sx * (1 - sx);
	}
}
