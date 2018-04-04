package week8;

import java.util.ArrayList;
import java.util.Vector;

public class Matrix {

    private int r, c;
    private Vector<Vector<Double>> rows = new Vector<>();

    Matrix(int rows, int columns) {
        r = rows;
        c = columns;
        for(int i = 0; i < r; i++) {
            this.rows.add(new Vector<>());
            this.rows.get(i).setSize(c);
        }
    }

    Matrix(Vector<Vector<Double>> rows) {
        r = rows.size();
        c = rows.get(0).size();
        this.rows = rows;
    }

    public int getColumns() {
        return c;
    }

    public int getRows() {
        return r;
    }

    public double getAt(int row, int column) {
        return rows.get(row).get(column);
    }

    public Vector<Double> getRow(int row) {
        return rows.get(row);
    }

    public Vector<Double> getCol(int col) {
        Vector<Double> res = new Vector<>();
        for(int i = 0; i < this.r; i++) {
            res.add(rows.get(i).get(col));
        }
        return res;
    }

    public void setAt(int row, int column, double value) {
        rows.get(row).set(column, value);
    }

    public void setRow(int row, Vector<Double> values) {
        rows.set(row, values);
    }

    public void setColumn(int column, Vector<Double> values) {
        if(values.size() != r)
            throw new IndexOutOfBoundsException("Doesn't fit in array!");
        for(int i = 0; i < values.size(); i++) {
            rows.get(i).set(column, values.get(i));
        }
    }

    public Matrix T() {
        Matrix res = new Matrix(c, r);
        for(int i = 0; i < r; i++) {
            res.setColumn(i, this.getRow(i));
        }
        return res;
    }

    protected double scalarMult(Vector<Double> v1, Vector<Double> v2) {
        if(v1.size() != v2.size())
            throw new IndexOutOfBoundsException("v1 and v2 doen't fits!");
        double res = 0;
        for(int i = 0; i < v1.size(); i++) {
            res += v1.get(i)*v2.get(i);
        }
        return res;
    }

    public Matrix multBy(Matrix other) {
        if(c != other.r)
            throw new IndexOutOfBoundsException("Matrices doesn't fit!!!");
        Matrix res = new Matrix(r, other.c);
        // TODO: Распараллель меня!
        for(int i = 0; i < res.getRows(); i++) {
            for(int j = 0; j < res.getColumns(); j++) {
                res.setAt(i, j, scalarMult(this.getRow(i), other.getCol(j)));
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Matrix m = new Matrix(2, 2);
        m.setAt(0, 0, 1);
        m.setAt(0, 1, 2);
        m.setAt(1, 0, 3);
        m.setAt(1, 1, 4);
        Matrix m1 = m.T();
        Matrix m2 = m.multBy(m);
        System.out.println("HELLO");
    }


}
