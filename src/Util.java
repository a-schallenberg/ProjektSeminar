import java.util.Random;

public class Util {

	/**
	 * Creates a new Matrix with random double values in the interval [-0.5, 0.5).
	 * @param rows Number of rows of the matrix.
	 * @param columns Number of columns of the matrix.
	 * @return The Matrix filled with the random generated values.
	 */
	public static double[][] randomMatrix(int rows, int columns) {
		return randomMatrix(rows, columns, -0.5, 0.5);
	}

	/**
	 * Creates a new Matrix with random double values.
	 * @param rows Number of rows of the matrix.
	 * @param columns Number of columns of the matrix.
	 * @param min The minimal value of the random values in the matrix (inclusive).
	 * @param max The maximal value of the random values in the matrix (exclusive).
	 * @return The Matrix filled with the random generated values.
	 */
	public static double[][] randomMatrix(int rows, int columns, double min, double max) {
		double[][] matrix = new double[rows][columns];
		Random random = new Random();

		for(int row = 0; row < rows; row++)
			for(int column = 0; column < columns; columns++)
				matrix[row][column] = (random.nextDouble() * (max - min) + min);

		return matrix;
	}

	/**
	 * Multiplies two matrices and returns the result.
	 * @param matrix The matrix (int[rows][columns]).
	 * @param vector The vector.
	 * @return The product of the two  matrices.
	 * @throws NullPointerException If one matrix is null.
	 * @throws ArithmeticException If the number of columns of the first matrix does not equal to the number of rows of the second matrix.
	 * @throws IndexOutOfBoundsException If one of the matrices is empty.
	 */
	public static double[] mulMatrixVector(double[][] matrix, double[] vector) {
		double[] result = new double[matrix.length];

		for (int row = 0; row < matrix.length; row++) {
			double sum = 0;
			for (int column = 0; column < matrix[0].length; column++) {
				sum += matrix[row][column]
						* vector[column];
			}
			result[row] = sum;
		}
		return result;
	}

	public static double[] addVectors(double[] vec1, double[] vec2) {
		if(vec1.length != vec2.length)
			throw new ArithmeticException("Cannot add vectors with different dimensions");

		double[] result = new double[vec1.length];

		for(int i = 0; i < vec1.length; i++)
			result[i] = vec1[i] + vec2[i];

		return result;
	}


	/**
	 * Multiplies two matrices and returns the result.
	 * @param matrix1 The first matrix (int[rows][columns]).
	 * @param matrix2 The second matrix (int[rows][columns]).
	 * @return The product of the two  matrices.
	 * @throws NullPointerException If one matrix is null.
	 * @throws ArithmeticException If the number of columns of the first matrix does not equal to the number of rows of the second matrix.
	 * @throws IndexOutOfBoundsException If one of the matrices is empty.
	 */
	public static double[][] mulMatrices(double[][] matrix1, double[][] matrix2) {
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

	public static double[][] transposeMatrix(double[][] matrix) {
		double[][] result = new double[matrix[0].length][matrix.length];

		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < matrix[0].length; j++)
				result[j][i] = matrix[i][j];

		return result;
	}

	public static double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	public static double[] sigmoid(double[] vec) {
		for(int i = 0; i < vec.length; i++)
			vec[i] = sigmoid(vec[i]);

		return vec;
	}

	public static double semiLinear(double x) {
		if(x <= 0)
			return 0;
		else if(x >= 1)
			return 1;

		return x;
	}

	public static double[] oneVector(int dim) {
		double[] vec = new double[dim];

		for(int i = 0; i < dim; i++)
			vec[i] = 1d;

		return vec;
	}
}
