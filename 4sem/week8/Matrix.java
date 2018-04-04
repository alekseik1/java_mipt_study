package week8;

import java.util.ArrayList;
import java.util.Vector;

public class Matrix {

    private int r, c;
    private Vector<Vector<Double>> rows;

    Matrix(int rows, int columns) {
        r = rows;
        c = columns;
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


}
