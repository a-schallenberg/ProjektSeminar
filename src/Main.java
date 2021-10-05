import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Network network = new Network(2, 2);

		System.out.println(Arrays.toString(network.compute(new double[]{1d, 1d})));
	}
}
