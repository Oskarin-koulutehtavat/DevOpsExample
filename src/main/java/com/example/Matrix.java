package com.example;

import java.util.Arrays;

/**
 * Matrix.
 */
public class Matrix {

    /**
     * Matrix contents.
     */
    private double[][] array;

    /**
     * Creates an empty matrix filled with 0s.
     *
     * @param height
     * @param width
     */
    public Matrix(int height, int width) {
        this.array = new double[height][width];
    }

    /**
     * Creates a matrix from a 2D array of numbers.
     *
     * @param array
     */
    public Matrix(double[][] array) {
        this.array = array;
    }

    /**
     * Clones a matrix.
     *
     * @param matrix
     */
    public Matrix(Matrix matrix) {
        this.array = matrix.array;
    }

    /**
     * Number of rows in the matrix.
     *
     * @return heigth
     */
    public int height() {
        return this.array.length;
    }

    /**
     * Number of columns in the matrix.
     *
     * @return width
     */
    public int width() {
        return this.array[0].length;
    }

    /**
     * Matrix contents in a 2D array.
     *
     * @return array
     */
    public double[][] array() {
        return array;
    }

    /*
     * TODO Row operations
     *
     * row addition, that is adding a row to another
     *
     * row multiplication, that is multiplying all entries
     * of a row by a non-zero constant
     *
     * row switching, that is interchanging two rows of a matrix
     */

    /**
     * Flips a matrix along the main diagonal.
     *
     * @param matrix
     * @return transposed matrix
     */
    public static Matrix transpose(Matrix matrix) {

        Matrix transposedMatrix = new Matrix(matrix.width(), matrix.height());

        for (int m = 0; m < matrix.height(); m++) {
            for (int n = 0; n < matrix.width(); n++) {
                transposedMatrix.array[n][m] = matrix.array[m][n];
            }
        }

        return transposedMatrix;
    }

    /**
     * Flips the matrix along the main diagonal.
     *
     * @return transposed matrix
     */
    public Matrix transpose() {
        return transpose(this);
    }

    /**
     * Multiplies a matrix by a scalar.
     *
     * @param matrix
     * @param scalar
     * @return multiplied matrix
     */
    public static Matrix multiply(Matrix matrix, double scalar) {

        Matrix multipliedMatrix = new Matrix(matrix.height(), matrix.width());

        for (int m = 0; m < matrix.height(); m++) {
            for (int n = 0; n < matrix.width(); n++) {
                multipliedMatrix.array[m][n] = matrix.array[m][n] * scalar;
            }
        }

        return multipliedMatrix;
    }

    /**
     * Multiplies the matrix by a scalar.
     *
     * @param scalar
     * @return multiplied matrix
     */
    public Matrix multiply(double scalar) {
        return multiply(this, scalar);
    }

    /**
     * Multiply a matrix with another matrix. The number of columns in the first
     * matrix has to match the number of rows of the second matrix.
     *
     * @param matrixA
     * @param matrixB
     * @return multiplied matrix
     */
    public static Matrix multiply(Matrix matrixA, Matrix matrixB) {

        if (matrixA.height() != matrixB.width()) {
            throw new IllegalArgumentException("The number of columns of A does not match the number of rows of B");
        }

        Matrix multipliedMatrix = new Matrix(matrixA.height(), matrixB.width());

        Matrix matrixBT = matrixB.transpose();

        for (int a = 0; a < matrixA.height(); a++) {
            for (int b = 0; b < matrixBT.height(); b++) {
                double productsum = 0;

                for (int i = 0; i < matrixA.array[a].length; i++) {
                    productsum += matrixA.array[a][i] * matrixBT.array[b][i];
                }

                multipliedMatrix.array[a][b] = productsum;
            }
        }

        return multipliedMatrix;
    }

    /**
     * Multiply the matrix with another matrix. The number of columns in the first
     * matrix has to match the number of rows of the second matrix.
     *
     * @param matrix
     * @return multiplied matrix
     */
    public Matrix multiply(Matrix matrix) {
        return multiply(this, matrix);
    }

    /**
     * Add a matrix to another matrix. Matrices have to be the same shape.
     *
     * @param matrixA
     * @param matrixB
     * @return subtracted matrix
     */
    public static Matrix add(Matrix matrixA, Matrix matrixB) {

        if (matrixA.height() != matrixB.height() || matrixA.width() != matrixB.width()) {
            throw new IllegalArgumentException("A and B are not the same size");
        }

        Matrix sumMatrix = new Matrix(matrixA.height(), matrixA.width());

        for (int m = 0; m < matrixA.height(); m++) {
            for (int n = 0; n < matrixA.width(); n++) {
                sumMatrix.array[m][n] = matrixA.array[m][n] + matrixB.array[m][n];
            }
        }

        return sumMatrix;
    }

    /**
     * Add a matrix to the matrix. Matrices have to be the same shape.
     *
     * @param matrix
     * @return subtracted matrix
     */
    public Matrix add(Matrix matrix) {
        return add(this, matrix);
    }

    /**
     * Subtract a matrix from another matrix. Matrices have to be the same shape.
     *
     * @param matrixA
     * @param matrixB
     * @return subtracted matrix
     */
    public static Matrix subtract(Matrix matrixA, Matrix matrixB) {
        return add(matrixA, multiply(matrixB, -1));
    }

    /**
     * Subtract a matrix from the matrix. Matrices have to be the same shape.
     *
     * @param matrix
     * @return subtracted matrix
     */
    public Matrix subtract(Matrix matrix) {
        return subtract(this, matrix);
    }

    /**
     * Creates a square identity matrix of a certain size. An identity matrix is a
     * square matrix that has 1s on the main diagonal from the upper left to
     * the lower right and 0s everywehere else.
     *
     * @param size width and height of the matrix
     * @return identity matrix
     */
    public static Matrix identity(int size) {

        Matrix identityMatrix = new Matrix(size, size);

        for (int m = 0; m < size; m++) {
            for (int n = 0; n < size; n++) {
                identityMatrix.array[m][n] = m == n ? 1 : 0;
            }
        }

        return identityMatrix;
    }

    /**
     * Creates the submatrix of a matrix. It is obtained by removing one of the rows
     * and columns.
     *
     * @param matrix
     * @param row    to remove
     * @param column to remove
     * @return submatrix
     */
    public static Matrix submatrix(Matrix matrix, int row, int column) {

        if (row > matrix.height()) {
            throw new IndexOutOfBoundsException(
                    String.format("%d out of bounds for size %d", row, matrix.height()));
        }
        if (column > matrix.width()) {
            throw new IndexOutOfBoundsException(
                    String.format("%d out of bounds for size %d", column, matrix.width()));
        }

        if (matrix.width() < 2 && matrix.height() < 2) {
            return matrix;
        }

        Matrix subMatrix = new Matrix(matrix.height() - 1, matrix.width() - 1);

        for (int m = 0; m < matrix.height(); m++) {
            if (m == row - 1) {
                continue;
            }
            for (int n = 0; n < matrix.width(); n++) {
                if (n == column - 1) {
                    continue;
                }
                int mM = m < row ? m : m - 1;
                int nM = n < column ? n : n - 1;
                subMatrix.array[mM][nM] = matrix.array[m][n];
            }
        }

        return subMatrix;
    }

    /**
     * Creates the submatrix of the matrix. It is obtained by removing one of the
     * rows and columns.
     *
     * @param row    to remove
     * @param column to remove
     * @return submatrix
     */
    public Matrix submatrix(int row, int column) {
        return submatrix(this, row, column);
    }

    /**
     * The sign of a number is 1 if it is even and -1 if it is odd.
     *
     * @param number
     * @return sign
     */
    private static int sign(int number) {
        return (number % 2 == 0) ? 1 : -1;
    }

    /**
     * Calcualtes the determinant of a matrix. The determinant of a matrix is a
     * special number that gives us important information about the matrix, such as
     * if it can be used in matrix division, wether it is invertible, and what the
     * results of transformations are on area, volume, and orientation.
     *
     * @param matrix
     * @return
     */
    public static double determinant(Matrix matrix) {

        if (matrix.height() != matrix.width()) {
            throw new IllegalArgumentException("Non-square matrices do not have a determinant");
        }

        if (matrix.height() == 1 && matrix.width() == 1) {
            return matrix.array[0][0];
        }

        if (matrix.height() == 2 && matrix.width() == 2) {
            return matrix.array[0][0] * matrix.array[1][1] - matrix.array[1][0] * matrix.array[0][1];
        }

        double determinantSum = 0;

        for (int n = 0; n < matrix.width(); n++) {
            determinantSum += sign(n) * matrix.array[0][n] * matrix.submatrix(1, n + 1).determinant();
        }

        return determinantSum;
    }

    /**
     * Calcualtes the determinant of a matrix. The determinant of a matrix is a
     * special number that gives us important information about the matrix, such as
     * if it can be used in matrix division, wether it is invertible, and what the
     * results of transformations are on area, volume, and orientation.
     *
     * @return
     */
    public double determinant() {
        return determinant(this);
    }

    /**
     * Divides the matrix by a scalar.
     *
     * @param matrix
     * @param scalar
     * @return divided matrix
     */
    public static Matrix divide(Matrix matrix, double scalar) {
        return multiply(matrix, 1 / scalar);
    }

    /**
     * Divides the matrix by a scalar.
     *
     * @param scalar
     * @return divided matrix
     */
    public Matrix divide(double scalar) {
        return divide(this, scalar);
    }

    /**
     * Divides a matrix by another matrix. Matrices can't be divided if either has a
     * determinant of zero.
     *
     * @param matrixA
     * @param matrixB
     * @return divided matrix
     */
    public static Matrix divide(Matrix matrixA, Matrix matrixB) {

        if (matrixA.determinant() == 0 || matrixB.determinant() == 0) {
            throw new IllegalArgumentException(
                    "One or both matrices have a determinant of zero. Division not possible.");
        }

        return multiply(matrixA, matrixB.inverse());
    }

    /**
     * Divides the matrix by another matrix. Matrices can't be divided if either has
     * a determinant of zero.
     *
     * @param matrix
     * @return divided matrix
     */
    public Matrix divide(Matrix matrix) {
        return divide(this, matrix);
    }

    /**
     * Calcualtes the minor of a matrix. It is the determinant of a submatrix
     * obtained by removing one of the rows and columns.
     *
     * @param matrix
     * @param i      row to remove
     * @param j      column to remove
     * @return minor
     */
    public static double minor(Matrix matrix, int i, int j) {
        return matrix.submatrix(i, j).determinant();
    }

    /**
     * Calcualtes the minor of the matrix. It is the determinant of a submatrix
     * obtained by removing one of the rows and columns.
     *
     * @param i row to remove
     * @param j column to remove
     * @return minor
     */
    public double minor(int i, int j) {
        return minor(this, i, j);
    }

    /**
     * Calculates the cofactor of a submatrix. The cofactor is positive if row +
     * column is even, and negative if it is odd.
     *
     * @param matrix
     * @param i      row to remove
     * @param j      column to remove
     * @return
     */
    public static double cofactor(Matrix matrix, int i, int j) {
        return sign(i + j) * matrix.minor(i, j);
    }

    /**
     * Calculates the cofactor of a submatrix. The cofactor is positive if row +
     * column is even, and negative if it is odd.
     *
     * @param i row to remove
     * @param j column to remove
     * @return
     */
    public double cofactor(int i, int j) {
        return cofactor(this, i, j);
    }

    /**
     * For each cell calculates the cofactor of the submatrix obtained by removing
     * the row and columnt that cell is in.
     *
     * @param matrix
     * @return cofactor matrix
     */
    public static Matrix cofactor(Matrix matrix) {
        Matrix cofactorMatrix = new Matrix(matrix.height(), matrix.width());

        for (int i = 0; i < matrix.height(); i++) {
            for (int j = 0; j < matrix.width(); j++) {
                cofactorMatrix.array[i][j] = cofactor(matrix, i + 1, j + 1);
            }
        }

        return cofactorMatrix;
    }

    /**
     * For each cell calculates the cofactor of the submatrix obtained by removing
     * the row and columnt that cell is in.
     *
     * @return cofactor matrix
     */
    public Matrix cofactor() {
        return cofactor(this);
    }

    /**
     * Calculates the adjugate of a square matrix. It is the transpose of its
     * cofactor matrix.
     *
     * @param matrix
     * @return adjugate matrix
     */
    public static Matrix adjugate(Matrix matrix) {

        if (matrix.height() != matrix.width()) {
            throw new IllegalArgumentException("Non-square matrices do not have an adjoint.");
        }

        return matrix.cofactor().transpose();
    }

    /**
     * Calculates the adjugate of a square matrix. It is the transpose of its
     * cofactor matrix.
     *
     * @return adjugate matrix
     */
    public Matrix adjugate() {
        return adjugate(this);
    }

    /**
     * Calcualtes the inverse matrix of an invertible matrix. Multiplying a matrix
     * by its inverse results in an identity matrix. It is the adjugate divided by
     * its determinant.
     *
     * @param matrix
     * @return inverse matrix
     */
    public static Matrix inverse(Matrix matrix) {

        if (matrix.height() != matrix.width()) {
            throw new IllegalArgumentException("Non-square matrices are not invertible.");
        }

        double determinant = matrix.determinant();

        if (determinant == 0) {
            throw new IllegalArgumentException("Determinant is zero. Inverse does not exist.");
        }

        return divide(matrix.adjugate(), determinant);
    }

    public Matrix inverse() {
        return inverse(this);
    }

    /**
     * Raise a square matrix to a power.
     *
     * @param matrix to raise to power
     * @param power  to raise matrix to
     * @return matrix raised to power
     */
    public static Matrix power(Matrix matrix, int power) {
        // only square matrices can be raised to a power
        if (matrix.height() != matrix.width()) {
            throw new IllegalArgumentException("Non-square matrices can not be raised to a power.");
        }
        // power 0 results in an identity matrix
        if (power == 0) {
            return identity(matrix.height());
        }
        Matrix powerMatrix;
        // invert matrix if using negative powers
        if (power < 0) {
            powerMatrix = matrix.inverse();
        } else {
            powerMatrix = matrix;
        }
        // with small powers it is faster to use naïve multiplying, and with larger
        // powers it is faster to use the square-and-multiply algorithm. 8 seems to be a
        // good cutoof power.
        final int cutoff = 8;
        if (Math.abs(power) < cutoff) {
            for (int a = 1; a < Math.abs(power); a++) {
                powerMatrix = powerMatrix.multiply(matrix);
            }
            return powerMatrix;
        } else {
            int powerCounter = Math.abs(power);
            Matrix identityMatrix = Matrix.identity(Math.max(matrix.width(), matrix.height()));
            while (powerCounter > 0) {
                // if counter is odd multiply identity matrix with initial matrix
                if (powerCounter % 2 == 1) {
                    identityMatrix = identityMatrix.multiply(powerMatrix);
                }
                // square initial matrix
                powerMatrix = powerMatrix.power(2);
                // divide counter by 2
                powerCounter = powerCounter >> 1;
            }
            return identityMatrix;
        }
    }

    /**
     * Raise square matrix to power.
     *
     * @param power to raise matrix to
     * @return matrix raised to power
     */
    public Matrix power(int power) {
        return power(this, power);
    }

    /**
     * Calculates the sum of elements on the main diagonal from the upper left to
     * the lower right of a square matrix.
     *
     * @param matrix to take the trace of
     * @return sum of elements on the main diagonal
     */
    public static double trace(Matrix matrix) {

        if (matrix.height() != matrix.width()) {
            throw new IllegalArgumentException("Non-square matrices do not have a trace.");
        }

        double traceSum = 0;

        for (int i = 0; i < matrix.width(); i++) {
            traceSum += matrix.array[i][i];
        }

        return traceSum;
    }

    /**
     * Calculates the sum of elements on the main diagonal from the upper left to
     * the lower right of a square matrix.
     *
     * @return sum of elements on the main diagonal
     */
    public double trace() {
        return trace(this);
    }

    @Override
    public String toString() {
        StringBuilder stringForm = new StringBuilder();

        for (int m = 0; m < this.height(); m++) {

            stringForm.append("[");

            for (int n = 0; n < this.width(); n++) {
                stringForm.append(this.array[m][n]);
                if (n != this.width() - 1) {
                    stringForm.append(",");
                }
            }

            stringForm.append("]");
            if (m != this.height() - 1) {
                stringForm.append("\n");
            }
        }

        return stringForm.toString();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Matrix matrixObject = (Matrix) object;
        return Arrays.deepEquals(array, matrixObject.array);
    }
}
