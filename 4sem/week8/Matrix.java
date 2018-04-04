package week8;

import java.util.Vector;
import java.util.concurrent.*;

// TODO: Люди добрые, сделайте это через связные списки. Мне больно смотреть на этот код
public class Matrix {

    protected int r, c;
    // А-а-а, какой ты классный. Хранить массив как лист из рядов. А если я захочу считать колонку?
    protected Vector<Vector<Double>> rows = new Vector<>();

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
        // Я так и думал. Ты итерируешься по всем рядам. Это что, новый вид насилия над моим процессором?
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

    protected class OneShot implements Callable<Double> {

        Vector<Double> row = new Vector<>();
        Vector<Double> column = new Vector<>();

        OneShot(Vector<Double> row, Vector<Double> column) {
            this.row = row;
            this.column = column;
        }

        public Double call() {
            return scalarMult(row, column);
        }
    }

    public Matrix multBy(Matrix other) throws ExecutionException, InterruptedException {
        if(c != other.r)
            throw new IndexOutOfBoundsException("Matrices doesn't fit!!!");
        Matrix res = new Matrix(r, other.c);
        ExecutorService executor = Executors.newCachedThreadPool();
        Runnable count_one_row = () -> {

        };
        Future<Double>[][] results = new Future[r][other.c];
        for(int i = 0; i < res.getRows(); i++) {
            for(int j = 0; j < res.getColumns(); j++) {
                // Ой-ой-ой. Да у нас тут getCol(), который работает дольше всего!
                // Кажется, кому-то пора уже научиться нормально прогать
                results[i][j] = executor.submit(new OneShot(getRow(i), other.getCol(j)));
            }
        }
        for(int i = 0; i < res.getRows(); i++) {
            for(int j = 0; j < res.getColumns(); j++) {
                // Хороший способ брать результаты. Нет, правда. Матрица Future[][] -- это сильно!
                res.setAt(i, j, results[i][j].get());
            }
        }
        // Убьем потоки. Почему-то они сами не хотят этого делать
        executor.shutdownNow();
        return res;
    }

    public void print() {
        for(Vector<Double> row : rows) {
            for(double d : row) {
                System.out.print(d);
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int size = 100;
        Matrix m = new Matrix(size, size);
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                m.setAt(i, j, i*i+2*j*j);
            }
        }
        Matrix m1 = m.T();
        Matrix m2 = m.multBy(m);
        m2.print();
        System.out.println("HELLO");
    }


}
