package com.example;

import java.util.Arrays;

public class Matrix {

  private double[][] array;

  public Matrix(int height, int width) {
    this.array = new double[height][width];
  }
  public Matrix(double[][] array) {
    this.array = array;
  }
  public Matrix(Matrix matrix) {
    this.array = matrix.array;
  }

  public int height() {
    return this.array.length;
  }
  public int width() {
    return this.array[0].length;
  }

  public static Matrix transpose(Matrix matrix) {

    Matrix transposedMatrix = new Matrix(matrix.width(), matrix.height());

    for (int m = 0; m < matrix.height(); m++) {
      for (int n = 0; n < matrix.width(); n++) {
        transposedMatrix.array[n][m] = matrix.array[m][n];
      }
    }

    return transposedMatrix;
  }
  public Matrix transpose() {
    return transpose(this);
  }

  public static double dot(double[] vectorA, double[] vectorB) {

    if (vectorA.length != vectorB.length) {
      throw new IllegalArgumentException("Vectors are not the same dimension");
    }

    double productsum = 0;

    for (int i = 0; i < vectorA.length; i++) {
      productsum += vectorA[i] * vectorB[i];
    }

    return productsum;
  }

  public static Matrix multiply(Matrix matrix, double scalar) {

    Matrix multipliedMatrix = new Matrix(matrix.height(), matrix.width());

    for (int m = 0; m < matrix.height(); m++) {
      for (int n = 0; n < matrix.width(); n++) {
        multipliedMatrix.array[m][n] = matrix.array[m][n] * scalar;
      }
    }

    return multipliedMatrix;
  }
  public Matrix multiply(double scalar) {
    return multiply(this, scalar);
  }
 
  public static Matrix multiply(Matrix matrixA, Matrix matrixB) {

    if (matrixA.height() != matrixB.width()) {
      throw new IllegalArgumentException("The number of columns of A does not match the number of rows of B");
    }

    Matrix multipliedMatrix = new Matrix(matrixA.height(), matrixB.width());

    Matrix bt = matrixB.transpose();

    for (int a = 0; a < matrixA.height(); a++) {
      for (int b = 0; b < bt.height(); b++) {
        multipliedMatrix.array[a][b] = dot(matrixA.array[a], bt.array[b]);
      }
    }

    return multipliedMatrix;
  }
  public Matrix multiply(Matrix matrix) {
    return multiply(this,matrix);
  }

  public static Matrix divide(Matrix matrix, double scalar) {
    return multiply(matrix, 1 / scalar);
  }
  public Matrix divide(double scalar) {
    return divide(this,scalar);
  }

  public static Matrix add(Matrix matrixA, Matrix matrixB) {

    if (matrixA.height() != matrixB.height() || matrixA.width() != matrixB.width()) {
      throw new IllegalArgumentException("A and B are not the same size");
    }

    Matrix sumMatrix = new Matrix(matrixA.height(),matrixA.width());

    for (int m = 0; m < matrixA.height(); m++) {
      for (int n = 0; n < matrixA.width(); n++) {
        sumMatrix.array[m][n] = matrixA.array[m][n] + matrixB.array[m][n];
      }
    }

    return sumMatrix;
  }
  public Matrix add(Matrix matrix) {
    return add(this,matrix);
  }

  public static Matrix subtract(Matrix matrixA, Matrix matrixB) {
    return add(matrixA, multiply(matrixB, -1));
  }
  public Matrix subtract(Matrix matrix) {
    return subtract(this,matrix);
  }

  public static Matrix identity(int size) {

    Matrix identityMatrix = new Matrix(size, size);

    for (int m = 0; m < size; m++) {
      for (int n = 0; n < size; n++) {
        identityMatrix.array[m][n] = m == n ? 1 : 0;
      }
    }

    return identityMatrix;
  }

  public static Matrix submatrix(Matrix matrix, int i, int j) {

    if (i > matrix.height()) {
      throw new IndexOutOfBoundsException(
        String.format("%d out of bounds for size %d", i, matrix.height())
      );
    }
    if (j > matrix.width()) {
      throw new IndexOutOfBoundsException(
        String.format("%d out of bounds for size %d", j, matrix.width())
      );
    }

    if (matrix.width() < 2 && matrix.height() < 2) {
      return matrix;
    }

    Matrix subMatrix = new Matrix(matrix.height()-1, matrix.width()-1);
    
    for (int m = 0; m < matrix.height(); m++) {
      if (m == i - 1) continue;
      for (int n = 0; n < matrix.width(); n++) {
        if (n == j - 1) continue;
        int mM = m < i ? m : m - 1;
        int nM = n < j ? n : n - 1;
        subMatrix.array[mM][nM] = matrix.array[m][n];
      }
    }

    return subMatrix;
  }
  public Matrix submatrix(int i, int j) {
    return submatrix(this, i, j);
  }

  private static int sign(int n) {
    return n % 2 == 0 ? 1 : -1;
  }

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
  public double determinant() {
    return determinant(this);
  }

  public static double minor(Matrix matrix, int i, int j) {
    return matrix.submatrix(i, j).determinant();
  }
  public double minor(int i, int j) {
    return minor(this, i, j);
  }

  public static double cofactor(Matrix matrix, int i, int j) {
    return sign(i+j) * matrix.minor(i, j);
  }
  public double cofactor(int i, int j) {
    return cofactor(this, i, j);
  }

  public static Matrix cofactor(Matrix matrix) {
    Matrix cofactorMatrix = new Matrix(matrix.height(), matrix.width());

    for (int i = 1; i <= matrix.height(); i++) {
      for (int j = 1; j <= matrix.width(); j++) {
        cofactorMatrix.array[i - 1][j - 1] = cofactor(matrix, i, j);
      }
    }

    return cofactorMatrix;
  }
  public Matrix cofactor() {
    return cofactor(this);
  }

  public static Matrix adjoint(Matrix matrix) {

    if (matrix.height() != matrix.width()) {
      throw new IllegalArgumentException("Non-square matrices do not have an adjoint");
    }

    return matrix.cofactor().transpose();
  }
  public Matrix adjoint() {
    return adjoint(this);
  }

  public static Matrix inverse(Matrix matrix) {

    if (matrix.height() != matrix.width()) {
      throw new IllegalArgumentException("Non-square matrices do not have an inverse");
    }

    double determinant = matrix.determinant();

    if (determinant == 0) {
      throw new IllegalArgumentException("Inverse does not exist (determinant is zero)");
    }

    return divide(matrix.adjoint(), determinant);
  }
  public Matrix inverse() {
    return inverse(this);
  }

  public static Matrix power(Matrix matrix, int power) {

    if (power <= 0 && matrix.height() != matrix.width()) {
      throw new IllegalArgumentException("Non-square matrices can only be raised to a power greater than zero");
    }

    if (power == 0) return identity(matrix.height());

    Matrix powerMatrix;

    if (power < 0) {
      powerMatrix = matrix.inverse();
      power *= -1;
    }
    else{
      powerMatrix = matrix;
    }

    for (int a = 1; a < power; a++) {
      powerMatrix = powerMatrix.multiply(matrix);
    }

    return powerMatrix;
  }
  public Matrix power(int power) {
    return power(this, power);
  }

  public static Matrix divide(Matrix matrixA, Matrix matrixB) {

    if (matrixA.determinant() == 0 || matrixB.determinant() == 0) {
      throw new IllegalArgumentException("Division not possible (a determinant is zero)");
    }

    return multiply(matrixA, matrixB.inverse());
  }
  public Matrix divide(Matrix matrix) {
    return divide(this,matrix);
  }

  public static double trace(Matrix matrix) {

    if (matrix.height() != matrix.width()) {
      throw new IllegalArgumentException("Non-square matrices can not have a trace");
    }

    double traceSum = 0;

    for (int i = 0; i < matrix.width(); i++) {
      traceSum += matrix.array[i][i];
    }

    return traceSum;
  }
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
        if (n != this.width() - 1) stringForm.append(",");
      }

      stringForm.append("]");
      if (m != this.height() - 1) stringForm.append("\n");
    }

    return stringForm.toString();
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(array);
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (object == null || this.getClass() != object.getClass()) return false;
    Matrix matrixObject = (Matrix)object;
    return Arrays.deepEquals(array, matrixObject.array);
  }
}
