import java.util.Random;

public class Util {

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
	 * @param matrix1 The first matrix (int[rows][columns]).
	 * @param matrix2 The second matrix (int[rows][columns]).
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

	public static double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	public static double semiLinear(double x) {
		if(x <= 0)
			return 0;
		else if(x >= 1)
			return 1;

		return x;
	}
}
