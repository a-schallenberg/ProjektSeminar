import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Netz network = new Netz(2, 2);

		System.out.println(Arrays.toString(network.compute(new double[]{1d, 1d})));
	}
}
