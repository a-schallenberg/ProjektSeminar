package main;

import java.util.Random;

import static java.lang.String.format;

public class Util {

	/**
	 * Creates a new vector with random double values in the interval [-0.5, 0.5).
	 * @param dim Dimension of the vector.
	 * @return The vector filled with the random generated values.
	 * @throws NegativeArraySizeException If dimension is negative.
	 */
	static double[] random(int dim) {
		double[] vec = new double[dim];
		Random random = new Random();

		for(int i = 0; i < dim; i++)
			vec[i] = (random.nextDouble() - 0.5);

		return vec;
	}

	/**
	 * Adds the second vector to the first vector.
	 * @param vec1 The first vector to which the second vector is added. The vector is affected by the addition.
	 * @param vec2 The second vector to be added by the first vector. The vector is not affected by the addition.
	 * @throws NullPointerException If one vector is null.
	 * @throws ArithmeticException If the dimensions of the two vectors in the argument list are not equal.
	 */
	static void addToVec1(double[] vec1, double[] vec2) {
		if(vec1.length != vec2.length)
			throw new ArithmeticException(format("Cannot add vectors (%d and %d) with different dimensions", vec1.length, vec2.length));

		for(int i = 0; i < vec1.length; i++)
			vec1[i] += vec2[i];
	}

	/**
	 * Multiplies a vector with a scalar.
	 * @param scalar The scalar to be multiplied with the vector. The scalar is not affected by the multiplication.
	 * @param vector The vector to be multiplied with the scalar. The vector is affected by the multiplication.
	 * @throws NullPointerException If the vector is null.
	 */
	static void mulToVec(double scalar, double[] vector) {
		for(int i = 0; i < vector.length; i++)
			vector[i] *= scalar;
	}
}
