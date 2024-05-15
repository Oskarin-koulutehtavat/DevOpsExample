package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for Matrix class.
 */
class MatrixTest {
    /**
     * Test creating new empty matrix.
     */
    @Test
    void testNewEmptyMatrix() {
        final int width = 3;
        final int height = 3;
        final Matrix matrixA = new Matrix(width, height);
        assertEquals(matrixA.height(), height);
        assertEquals(matrixA.width(), width);
    }

    /**
     * Test creating new matrix from array.
     */
    @Test
    void testNewMatrixFromArray() {
        final double[][] array = {
                {1, 0},
                {0, 1}};
        final Matrix matrixA = new Matrix(array);
        assertEquals(array.length, matrixA.height());
        assertEquals(array[0].length, matrixA.width());
        assertEquals(array, matrixA.array());
    }

    /**
     * Test creating new matrix from matrix.
     */
    @Test
    void testNewMatrixFromMatrix() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 0},
                {0, 1}});
        final Matrix matrixB = new Matrix(matrixA);
        assertEquals(matrixA, matrixB);
    }

    /**
     * Test sum of matrices.
     */
    @Test
    void testAddition() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 3, 1},
                {1, 0, 0}});
        final Matrix matrixB = new Matrix(new double[][]{
                {0, 0, 5},
                {7, 5, 0}});
        final Matrix matrixAPB = new Matrix(new double[][]{
                {1, 3, 6},
                {8, 5, 0}});
        assertEquals(matrixAPB, matrixA.add(matrixB));
    }

    /**
     * Test sum of matrices fails on different size matrices.
     */
    @Test
    void testAdditionDifferentSize() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 3, 1},
                {1, 0, 0}});
        final Matrix matrixB = new Matrix(new double[][]{
                {0, 0},
                {7, 5}});
        assertThrows(IllegalArgumentException.class, () -> matrixA.add(matrixB));
    }

    /**
     * Test multiplying matrix by a scalar.
     */
    @Test
    void testMultiplyScalar() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2},
                {3, 4}});
        final Matrix matrixAM2 = new Matrix(new double[][]{
                {2, 4},
                {6, 8}});
        final double scalar = 2;
        assertEquals(matrixAM2, matrixA.multiply(scalar));
    }

    /**
     * Test difference of matrices.
     */
    @Test
    void testSubtraction() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 3, 1},
                {1, 0, 0}});
        final Matrix matrixB = new Matrix(new double[][]{
                {0, 0, 5},
                {7, 5, 0}});
        final Matrix matrixAMB = new Matrix(new double[][]{
                {1, 3, -4},
                {-6, -5, 0}});
        assertEquals(matrixAMB, matrixA.subtract(matrixB));
    }

    /**
     * Test difference of matrices fails on different size matrices.
     */
    @Test
    void testSubtractionDifferentSize() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 3, 1},
                {1, 0, 0}});
        final Matrix matrixB = new Matrix(new double[][]{
                {0, 0},
                {7, 5}});
        assertThrows(IllegalArgumentException.class, () -> matrixA.subtract(matrixB));
    }

    /**
     * Test transposing matrix.
     */
    @Test
    void testTranspose() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3},
                {0, -6, 7}});
        final Matrix matrixAT = new Matrix(new double[][]{
                {1, 0},
                {2, -6},
                {3, 7}});
        assertEquals(matrixAT, matrixA.transpose());
    }

    /**
     * Test matrix multiplication.
     */
    @Test
    void testMultiply() {
        final Matrix matrixA = new Matrix(new double[][]{
                {2, 3, 4},
                {1, 0, 0}});
        final Matrix matrixB = new Matrix(new double[][]{
                {0, 1000},
                {1, 100},
                {0, 10}});
        final Matrix matrixAB = new Matrix(new double[][]{
                {3, 2340},
                {0, 1000}});
        assertEquals(matrixAB, matrixA.multiply(matrixB));
    }

    /**
     * Test matrix multiplication fails when the number of columns of A does not
     * match the number of rows of B.
     */
    @Test
    void testMultiplyRowColumnMismatch() {
        final Matrix matrixA = new Matrix(new double[][]{
                {2, 3, 4},
                {1, 0, 0}});
        final Matrix matrixB = new Matrix(new double[][]{
                {0, 1000, 0},
                {0, 10, 0}});
        assertThrows(IllegalArgumentException.class, () -> matrixA.multiply(matrixB));
    }

    /**
     * Test submatrix.
     */
    @Test
    void testSubmatrix() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}});
        final Matrix subMatrix = new Matrix(new double[][]{
                {1, 3, 4},
                {5, 7, 8}});
        final int row = 3;
        final int column = 2;
        assertEquals(subMatrix, matrixA.submatrix(row, column));
    }

    /**
     * Test submatrix for 1x1 matrix.
     */
    @Test
    void testSubmatrix1x1() {
        final Matrix matrixA = new Matrix(new double[][]{{1}});
        final int row = 1;
        final int column = 1;
        assertEquals(matrixA, matrixA.submatrix(row, column));
    }

    /**
     * Test submatrix row out of bounds.
     */
    @Test
    void testSubmatrixRowOOB() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}});
        final int row = 4;
        final int column = 2;
        assertThrows(IndexOutOfBoundsException.class, () -> matrixA.submatrix(row, column));
    }

    /**
     * Test submatrix column out of bounds.
     */
    @Test
    void testSubmatrixColumnOOB() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}});
        final int row = 3;
        final int column = 5;
        assertThrows(IndexOutOfBoundsException.class, () -> matrixA.submatrix(row, column));
    }

    /**
     * Test identity matrix.
     */
    @Test
    void testIdentityMatrix() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}});
        final int size = 3;
        assertEquals(matrixA, Matrix.identity(size));
    }

    /**
     * Test calcualtion of determinant for 1x1 matrix.
     */
    @Test
    void testDeterminant1x1() {
        final double determinant = 2;
        final Matrix matrixA = new Matrix(new double[][]{{determinant}});
        assertEquals(determinant, matrixA.determinant());
    }

    /**
     * Test calcualtion of determinant for 2x2 matrix.
     */
    @Test
    void testDeterminant2x2() {
        final double determinant = -19;
        final Matrix matrixA = new Matrix(new double[][]{
                {3, 7},
                {1, -4}});
        assertEquals(determinant, matrixA.determinant());
    }

    /**
     * Test calcualtion of determinant for matrices bigger than 2x2.
     */
    @Test
    void testDeterminant() {
        final double determinant = -924;
        final Matrix matrixA = new Matrix(new double[][]{
                {3, 7, 6, 23},
                {1, -4, 25, 1},
                {4, 23, 7, 0},
                {0, 0, 0, 1}});
        assertEquals(determinant, matrixA.determinant());
    }

    /**
     * Test calcualtion of determinant fails for non-square matrices.
     */
    @Test
    void testDeterminantNonSquare() {
        final Matrix matrixA = new Matrix(new double[][]{
                {2, 3, 4},
                {1, 0, 0}});
        assertThrows(IllegalArgumentException.class, () -> matrixA.determinant());
    }

    /**
     * Test dividing matrix by a scalar.
     */
    @Test
    void testDivideScalar() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2},
                {3, 4}});
        final Matrix matrixAM2 = new Matrix(new double[][]{
                {0.5, 1},
                {1.5, 2}});
        final double scalar = 2;
        assertEquals(matrixAM2, matrixA.divide(scalar));
    }

    /**
     * Test inverting matrix.
     */
    @Test
    void testInverse() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 3, 3},
                {1, 4, 3},
                {1, 3, 4}});
        final Matrix matrixI = new Matrix(new double[][]{
                {7, -3, -3},
                {-1, 1, 0},
                {-1, 0, 1}});
        assertEquals(matrixI, matrixA.inverse());
    }

    /**
     * Test dividing non-square matrix fails.
     */
    @Test
    void testInverseNonSquare() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6}});
        assertThrows(IllegalArgumentException.class, () -> matrixA.inverse());
    }

    /**
     * Test dividing matrix with determinant of zero fails.
     */
    @Test
    void testInverseZeroDeterminant() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}});
        assertThrows(IllegalArgumentException.class, () -> matrixA.inverse());
    }

    /**
     * Test dividing matrix by another matrix.
     */
    @Test
    void testDivide() {
        final Matrix matrixA = new Matrix(new double[][]{
                {5, 6},
                {7, 8}});
        final Matrix matrixB = new Matrix(new double[][]{
                {1, 2},
                {3, 4}});
        final Matrix matrixAM2 = new Matrix(new double[][]{
                {-1, 2},
                {-2, 3}});
        assertEquals(matrixAM2, matrixA.divide(matrixB));
    }

    /**
     * Test dividing matrix by another matrix fails with a matrix with determinant
     * of zero.
     */
    @Test
    void testDivideZeroDeterminant() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        final Matrix matrixB = new Matrix(new double[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}});
        assertThrows(IllegalArgumentException.class, () -> matrixA.divide(matrixB));
    }

    /**
     * Test calculating trace of matrix.
     */
    @Test
    void testTrace() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 9, 9}});
        final double trace = 1 + 5 + 9;
        assertEquals(trace, matrixA.trace());
    }

    /**
     * Test calculating trace of matrix fails for non-square matrices.
     */
    @Test
    void testTraceNonSquare() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6}});
        assertThrows(IllegalArgumentException.class, () -> matrixA.trace());
    }

    /**
     * Test.
     */
    @Test
    void testPowerPositiveSmall() {
        final Matrix matrixA = new Matrix(new double[][]{
                {2, 2, 2},
                {2, 2, 2},
                {2, 2, 2}});
        final Matrix matrixB = new Matrix(new double[][]{
                {12, 12, 12},
                {12, 12, 12},
                {12, 12, 12}});
        final int power = 2;
        assertEquals(matrixB, matrixA.power(power));
    }

    /**
     * Test.
     */
    @Test
    void testPowerPositiveBig() {
        final Matrix matrixA = new Matrix(new double[][]{
                {2, 2},
                {2, 2}});
        final Matrix matrixB = new Matrix(new double[][]{
                {32768, 32768},
                {32768, 32768}});
        final int power = 8;
        assertEquals(matrixB, matrixA.power(power));
    }

    /**
     * Test.
     */
    @Test
    void testPowerNegativeSmall() {
        final Matrix matrixA = new Matrix(new double[][]{
                {2, 1},
                {1, 2}});
        final Matrix matrixB = new Matrix(new double[][]{
                {14, 13},
                {13, 14}});
        final int power = -3;
        assertEquals(matrixB.inverse(), matrixA.power(power));
    }

    /**
     * Test.
     */
    @Test
    void testPowerNegativeBig() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2},
                {7, 8}});
        final Matrix matrixB = new Matrix(new double[][]{
                {11654847, 14357898},
                {50252643, 61907490}});
        final int power = -8;
        assertEquals(matrixB.inverse(), matrixA.power(power));
    }

    /**
     * Test raising matrix to power 0. Results in identity matrix of the same size.
     */
    @Test
    void testPowerZero() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        final Matrix matrixB = Matrix.identity(matrixA.height());
        final int power = 0;
        assertEquals(matrixB, matrixA.power(power));
    }

    /**
     * Test raising a non-square matrix to a power fails.
     */
    @Test
    void testPowerNonSquare() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6}});
        assertThrows(IllegalArgumentException.class, () -> matrixA.power(2));
    }

    /**
     * Test calculatying adjugate of a non-square matrix fails.
     */
    @Test
    void testAdjugateNonSquare() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6}});
        assertThrows(IllegalArgumentException.class, () -> matrixA.adjugate());
    }

    /**
     * Test hashcode.
     */
    @Test
    void testHashcode() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 3, 3},
                {1, 4, 3},
                {1, 3, 4}});
        final Matrix matrixI = new Matrix(new double[][]{
                {7, -3, -3},
                {-1, 1, 0},
                {-1, 0, 1}});
        assertEquals(matrixI.hashCode(), matrixA.inverse().hashCode());
    }

    /**
     * Test toSting.
     */
    @Test
    void testToString() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        final String matrixString = "[1.0,2.0,3.0]\n[4.0,5.0,6.0]\n[7.0,8.0,9.0]";
        assertEquals(matrixString, matrixA.toString());
    }

    /**
     * Test toSting.
     */
    @Test
    void testEquals() {
        final Matrix matrixA = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        final Matrix matrixB = new Matrix(new double[][]{
                {7, -3, -3},
                {-1, 1, 0},
                {-1, 0, 1}});
        assertNotEquals(null, matrixA);
        assertNotEquals("matrixA", matrixA);
        assertNotEquals(new Matrix(1, 1), matrixA);
        assertNotEquals(new Matrix((double[][]) null), matrixA);
        assertNotEquals(new Matrix(new double[][]{null}), matrixA);
        assertNotEquals(matrixB, matrixA);
    }

}
