package week8;

import java.util.Vector;
import java.util.concurrent.*;

public class Matrix {

    volatile protected int r, c;
    volatile protected double[][] arr;
    volatile protected double[][] arr_T;

    Matrix(int rows, int columns) {
        r = rows;
        c = columns;
        arr = new double[r][c];
        arr_T = new double[c][r];
    }

    Matrix(double[][] data) {
        r = data.length;
        c = data[0].length;
        arr = data;
        arr_T = new double[c][r];
        for(int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                arr_T[j][i] = data[i][j];
            }
        }
    }

    public int getColumnsCount() { return c; }

    public int getRowsCount() { return r; }

    public double getAt(int row, int column) { return arr[row][column]; }

    public double[] getRow(int row) { return arr[row]; }

    public double[] getColumn(int col) { return arr_T[col]; }

    public void setAt(int row, int column, double value) {
        arr[row][column] = value;
        arr_T[column][row] = value;
    }

    public void setRow(int row, double[] data) {
        if(data.length != c)
            throw new IndexOutOfBoundsException("Data doesn't fit in matrix!");
        arr[row] = data;
        for(int i = 0; i < r; i++) {
            arr_T[i][row] = data[i];
        }
    }

    public void setColumn(int column, double[] data) {
        if(data.length != r)
            throw new IndexOutOfBoundsException("Data doesn't fit in matrix!");
        arr_T[column] = data;
        for(int i = 0; i < r; i++) {
            arr[i][column] = data[i];
        }
    }

    protected double scalarMult(double[] v1, double[] v2) {
        if(v1.length != v2.length)
            throw new IndexOutOfBoundsException("v1 and v2 doen't fits!");
        double res = 0;
        for(int i = 0; i < v1.length; i++) {
            res += v1[i]*v2[i];
        }
        return res;
    }

    protected class OneShot implements Runnable {

        int row;
        int column;
        Matrix m1, m2, res;

        OneShot(int row, int column, Matrix m1, Matrix m2, Matrix res) {
            this.row = row;
            this.column = column;
            this.m1 = m1;
            this.m2 = m2;
            this.res = res;
        }

        public void run() {
            double tmp_res = scalarMult(m1.getRow(row), m2.getColumn(column));
            res.setAt(row, column, tmp_res);
        }
    }

    public Matrix multBy(Matrix other) throws ExecutionException, InterruptedException {
        if(r != other.c)
            throw new IndexOutOfBoundsException("Matrices doesn't fit!!!");
        Matrix res = new Matrix(this.r, other.c);
        ExecutorService executor = Executors.newCachedThreadPool();
        for(int i = 0; i < res.getRowsCount(); i++) {
            for(int j = 0; j < res.getColumnsCount(); j++) {
                executor.submit(new OneShot(i, j, this, other, res)).get();
            }
        }
        executor.shutdownNow();
        return res;
    }

    public Matrix T() {
        return new Matrix(arr_T);
    }

    public void print() {
        for(double[] row : arr) {
            for(double d : row) {
                System.out.print(d);
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public String toString() {
        return null;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int size = 1000;
        Matrix m1 = new Matrix(size, size);
        Matrix m2 = new Matrix(size, size);
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                m1.setAt(i, j, i*size+j+1);
            }
        }
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                m2.setAt(i, j, i*size+j+1);
            }
        }
        Matrix m3 = m1.multBy(m1);
        m3.print();
        System.out.println("HELLO");
    }
}
