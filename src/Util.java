import java.util.Random;

import static java.lang.String.format;

public class Util {

	/**
	 * Creates a new vector filled with a specific value.
	 * @param dim Dimension of the vector.
	 * @param value Specific value for each cell of the vector.
	 * @return A new vector filled with the specific value.
	 * @throws NegativeArraySizeException If dimension is negative.
	 */
	public static double[] fill(int dim, double value) {
		double[] vec = new double[dim];

		for(int i = 0; i < dim; i++)
			vec[i] = value;

		return vec;
	}

	/**
	 * Creates a new matrix filled with a specific value.
	 * @param rows Number of rows of the matrix.
	 * @param columns Number of columns of the matrix.
	 * @param value Specific value for each cell of the matrix.
	 * @return A new matrix filled with the specific value.
	 * @throws NegativeArraySizeException If rows or columns are negative.
	 */
	public static double[][] fill(int rows, int columns, double value) {
		double[][] matrix = new double[rows][columns];

		for(int row = 0; row < rows; row++)
			for(int column = 0; column < columns; column++)
				matrix[row][column] = value;

		return matrix;
	}

	/**
	 * Creates a new vector with random double values in the interval [-0.5, 0.5).
	 * @param dim Dimension of the vector.
	 * @return The vector filled with the random generated values.
	 * @throws NegativeArraySizeException If dimension is negative.
	 */
	public static double[] random(int dim) {
		return random(dim, -0.5, 0.5);
	}

	/**
	 * Creates a new vector with random double values.
	 * @param dim Dimension of the vector.
	 * @param min The minimal value of the random values in the vector (inclusive).
	 * @param max The maximal value of the random values in the vector (exclusive).
	 * @return The vector filled with the random generated values.
	 * @throws NegativeArraySizeException If dimension is negative.
	 */
	public static double[] random(int dim, double min, double max) {
		double[] vec = new double[dim];
		Random random = new Random();

		for(int i = 0; i < dim; i++)
				vec[i] = (random.nextDouble() * (max - min) + min);

		return vec;
	}

	/**
	 * Creates a new matrix with random double values in the interval [-0.5, 0.5).
	 * @param rows Number of rows of the matrix.
	 * @param columns Number of columns of the matrix.
	 * @return The matrix filled with the random generated values.
	 * @throws NegativeArraySizeException If rows or columns are negative.
	 */
	public static double[][] random(int rows, int columns) {
		return random(rows, columns, -0.5, 0.5);
	}

	/**
	 * Creates a new matrix with random double values.
	 * @param rows Number of rows of the matrix.
	 * @param columns Number of columns of the matrix.
	 * @param min The minimal value of the random values in the matrix (inclusive).
	 * @param max The maximal value of the random values in the matrix (exclusive).
	 * @return The matrix filled with the random generated values.
	 * @throws NegativeArraySizeException If rows or columns are negative.
	 */
	public static double[][] random(int rows, int columns, double min, double max) {
		double[][] matrix = new double[rows][columns];
		Random random = new Random();

		for(int row = 0; row < rows; row++)
			for(int column = 0; column < columns; column++)
				matrix[row][column] = (random.nextDouble() * (max - min) + min);

		return matrix;
	}

	/**
	 * Multiplies a vector with a scalar.
	 * @param scalar The scalar to be multiplied with the vector. The scalar is not affected by the multiplication.
	 * @param vector The vector to be multiplied with the scalar. The vector is not affected by the multiplication.
	 * @return A copy of the vector in the argument list in which each value is multiplied by the scalar.
	 * @throws NullPointerException If the vector is null.
	 */
	public static double[] mul(double scalar, double[] vector) {
		double[] result = new double[vector.length];

		for(int i = 0; i < vector.length; i++)
			result[i] = scalar * vector[i];

		return result;
	}

	/**
	 * Multiplies all values of the first vector with the corresponding value of the second vector.
	 * @param vec1 The first vector to be multiplied with the second vector. The vector is not affected by the multiplication.
	 * @param vec2 The second vector to be multiplied with the first vector. The vector is not affected by the multiplication.
	 * @return A copy of the first vector in the argument list in which each value is multiplied by the corresponding value of the second vector.
	 * @throws NullPointerException If one of the vectors is null.
	 * @throws ArithmeticException If the dimensions of the vectors are not equal.
	 */
	public static double[] mul(double[] vec1, double[] vec2) {
		if(vec1.length != vec2.length)
			throw new ArithmeticException("Vectors do not match");

		double[] result = new double[vec1.length];
		for(int i = 0; i < vec1.length; i++)
			result[i] = vec1[i] * vec2[i];

		return result;
	}

	/**
	 * Multiplies the matrix with the vector via matrix vector multiplication.
	 * @param matrix The matrix to be multiplied with the vector. The matrix is not affected by the multiplication.
	 * @param vec The vector to be multiplied by the matrix. The vector is not affected by the multiplication.
	 * @return A new vector with the result of the matrix vector multiplication.
	 * @throws NullPointerException If the matrix or the vector is null.
	 * @throws ArithmeticException If the dimension of the vector does not equal to the number of columns of the matrix.
	 * @throws IndexOutOfBoundsException If the matrix is empty.
	 */
	public static double[] mul(double[][] matrix, double[] vec) {
		if(matrix[0].length != vec.length)
			throw new ArithmeticException(format("Matrix (%dx%d) and vector (%d) do not match", matrix.length, matrix[0].length, vec.length));

		double[] result = new double[matrix.length];

		for (int row = 0; row < matrix.length; row++) {
			double sum = 0;
			for (int column = 0; column < matrix[0].length; column++)
				sum += matrix[row][column] * vec[column];

			result[row] = sum;
		}
		return result;
	}

	/**
	 * Multiplies the vector with the matrix via vector matrix multiplication.
	 * @param vec The vector to be multiplied with the matrix. The vector is not affected by the multiplication.
	 * @param matrix The matrix to be multiplied by the vector. The matrix is not affected by the multiplication.
	 * @return A new matrix with the result of the vector matrix multiplication.
	 * @throws NullPointerException If the vector or the matrix is null.
	 * @throws ArithmeticException If the dimension of the vector does not equal to the number of columns of the matrix.
	 * @throws IndexOutOfBoundsException If the matrix is empty.
	 */
	public static double[][] mul(double[] vec, double[][] matrix) {
		if(matrix.length != 1)
			throw new ArithmeticException(format("Cannot multiply vector (%d) with matrix (%dx%d)", vec.length, matrix.length, matrix[0].length));

		double[][] result = new double[vec.length][matrix[0].length];

		for (int row = 0; row < vec.length; row++)
			for (int column = 0; column < matrix[0].length; column++)
				result[row][column] = vec[row] * matrix[0][column];

			return result;
	}

	/**
	 * Multiplies two matrices and returns the result.
	 * @param matrix1 The first matrix (int[rows][columns]). The matrix is not affected by the multiplication.
	 * @param matrix2 The second matrix (int[rows][columns]). The matrix is not affected by the multiplication.
	 * @return The product of the two  matrices.
	 * @throws NullPointerException If one matrix is null.
	 * @throws ArithmeticException If the number of columns of the first matrix does not equal to the number of rows of the second matrix.
	 * @throws IndexOutOfBoundsException If one of the matrices is empty.
	 */
	public static double[][] mul(double[][] matrix1, double[][] matrix2) {
		if(matrix1[0].length != matrix2.length)
			throw new ArithmeticException("Matrices do not match");

		double[][] result = new double[matrix1.length][matrix2[0].length];
		for(int row = 0; row < result.length; row++)
			for(int col = 0; col < result[0].length; col++) {
				result[row][col] = 0;
				for(int i = 0; i < matrix2.length; i++)
					result[row][col] += matrix1[row][i] * matrix2[i][col];
			}

		return result;
	}

	/**
	 * Adds two matrices.
	 * @param matrix1 The first matrix to which the second matrix is added. The matrix is not affected by the addition.
	 * @param matrix2 The second matrix to be added by the first matrix. The matrix is not affected by the addition.
	 * @return A new matrix with the sum of the corresponding values of the first and second matrix in the argument list.
	 * @throws NullPointerException If one matrix is null.
	 * @throws ArithmeticException If the number of rows or columns of the two matrices in the argument list are not equal.
	 * @throws IndexOutOfBoundsException If one of the matrices is empty.
	 */
	public static double[][] add(double[][] matrix1, double[][] matrix2) {
		if(matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length)
			throw new ArithmeticException("Cannot add a " + matrix1.length + "x" + matrix1[0].length + " matrix and a " + matrix2.length + "x" + matrix2[0].length + " matrix");

		double[][] result = new double[matrix1.length][matrix1[0].length];

		for(int row = 0; row < matrix1.length; row++)
			for(int column = 0; column < matrix1[0].length; column++)
				result[row][column] = matrix1[row][column] + matrix2[row][column];

		return result;
	}

	/**
	 * Adds two vectors.
	 * @param vec1 The first vector to which the second vector is added. The vector is not affected by the addition.
	 * @param vec2 The second vector to be added by the first vector. The vector is not affected by the addition.
	 * @return A new vector with the sum of the corresponding values of the first and second vector in the argument list.
	 * @throws NullPointerException If one vector is null.
	 * @throws ArithmeticException If the dimensions of the two vectors in the argument list are not equal.
	 */
	public static double[] add(double[] vec1, double[] vec2) {
		if(vec1.length != vec2.length)
			throw new ArithmeticException(format("Cannot add vectors (%d and %d) with different dimensions", vec1.length, vec2.length));

		double[] result = new double[vec1.length];

		for(int i = 0; i < vec1.length; i++)
			result[i] = vec1[i] + vec2[i];

		return result;
	}

	/**
	 * Subtracts two vectors.
	 * @param vec1 The first vector to which the second vector is subtracted. The vector is not affected by the subtraction.
	 * @param vec2 The second vector to be subtracted by the first vector. The vector is not affected by the subtraction.
	 * @return A new vector with the difference of the corresponding values of the first and second vector in the argument list.
	 * @throws NullPointerException If one vector is null.
	 * @throws ArithmeticException If the dimensions of the two vectors in the argument list are not equal.
	 */
	public static double[] sub(double[] vec1, double[] vec2) {
		if(vec1.length != vec2.length)
			throw new ArithmeticException("Cannot subtract vectors with different dimensions");

		double[] result = new double[vec1.length];

		for(int i = 0; i < vec1.length; i++)
			result[i] = vec1[i] - vec2[i];

		return result;
	}

	/**
	 * Transposes a vector.
	 * @param vec The vector which is transposed. The vector is not affected by the transposition.
	 * @return The matrix which is produced by the transposition.
	 * @throws NullPointerException If the vector in the argument list is null.
	 */
	public static double[][] transpose(double[] vec) {
		double[][] result = new double[1][];
		result[0] = vec;

		return result;
	}

	/**
	 * Transposes a matrix.
	 * @param matrix The matrix to be transposed. The matrix is not affected by transposition.
	 * @return The matrix produced by transposition.
	 * @throws NullPointerException If the matrix in the argument list is null.
	 * @throws IndexOutOfBoundsException If the matrix is empty.
	 */
	public static double[][] transpose(double[][] matrix) {
		double[][] result = new double[matrix[0].length][matrix.length];

		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < matrix[0].length; j++)
				result[j][i] = matrix[i][j];

		return result;
	}

	/**
	 * Transposes a matrix.
	 * @param matrix The matrix to be transposed. The matrix is not affected by transposition.
	 * @return The vector produced by transposition.
	 * @throws NullPointerException If the matrix in the argument list is null.
	 * @throws ArithmeticException If the matrix is not equal to a transposed vector.
	 * @throws IndexOutOfBoundsException If the matrix is empty.
	 */
	public static double[] transposeToVector(double[][] matrix) {
		if(matrix.length != 1)
			throw new ArithmeticException("Cannot transpose matrix with " + matrix.length + " rows to a vector");

		return matrix[0];
	}

	/**
	 * The sigmoid function or logistic function which produces a value between zero and one.
	 * @param x A real value.
	 * @return A value between zero and one depending on x.
	 */
	public static double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	/**
	 * The derivative of the {@link #sigmoid(double)} function.
	 * @param x A real value and result of the {@link #sigmoid(double)} function.
	 * @return The function value at x.
	 */
	public static double dSigmoid(double x) {
		return x * (1 - x);
	}

	/**
	 * The semi-linear function produces a value between zero and one (both inclusive).
	 * If x is equal or less than 0 the function returns 0.
	 * If x is equal or greater than 1 the function returns 1.
	 * Else the function returns x.
	 * @param x A real value.
	 * @return A value between zero and one (both inclusive) as described above.
	 */
	public static double semiLinear(double x) {
		return x <= 0 ? 0 : (x >= 1 ? 1 : x);
	}

	/**
	 * The sgn function produces zero or one depending on x and the offset.
	 * If x is less than the offset the function returns 0.
	 * Else the function returns 1.
	 * @param x A real value.
	 * @return Zero or one as described above.
	 */
	public static int sgn(double x, double offset) {
		return x < offset ? 0 : 1;
	}

	/**
	 * Uses the {@link #sigmoid(double)} function on all the values in a vector.
	 * @param vec A vector with real values. The vector is not affected by the function.
	 * @return A vector with values between zero and one depending on corresponding value of the vector in the argument list.
	 */
	public static double[] sigmoid(double[] vec) {
		double[] result = new double[vec.length];

		for(int i = 0; i < vec.length; i++)
			result[i] = sigmoid(vec[i]);

		return result;
	}

	/**
	 * Uses the {@link #dSigmoid(double)} function on all the values in a vector.
	 * @param vec A vector with real values and results of the {@link #sigmoid(double)} function.The vector is not affected by the function.
	 * @return A vector with function values of the corresponding values in the vector in the argument list.
	 */
	public static double[] dSigmoid(double[] vec) {
		double[] result = new double[vec.length];

		for(int i = 0; i < vec.length; i++)
			result[i] = dSigmoid(vec[i]);

		return result;
	}

	/**
	 * Uses the {@link #semiLinear(double)} function on all the values in a vector.
	 * @param vec A vector with real values. The vector is not affected by the function.
	 * @return A vector with values between zero and one (both inclusive) depending on corresponding value of the vector in the argument list.
	 */
	public static double[] semiLinear(double[] vec) {
		double[] result = new double[vec.length];

		for(int i = 0; i < vec.length; i++)
			result[i] = semiLinear(vec[i]);

		return result;
	}

	/**
	 * Uses the {@link #sgn(double, double)} function on all the values in a vector.
	 * @param vec A vector with real values. The vector is not affected by the function.
	 * @return A vector with values zero or one depending on corresponding value of the vector and the offset in the argument list.
	 */
	public static int[] sgn(double[] vec, double[] offsets) {
		int[] result = new int[vec.length];

		for(int i = 0; i < vec.length; i++)
			result[i] = sgn(vec[i], offsets[i]);

		return result;
	}
}
