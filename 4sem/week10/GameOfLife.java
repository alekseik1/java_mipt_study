package week10;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Игра "Жизнь" Джона Конвоя
 */
public class GameOfLife {

    protected final int width, height;

    protected char[][] cells;

    GameOfLife(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new char[width][height];
    }

    /**
     * Инициализировать игру, забив определенные ячейки живыми клетками
     * @param living_cells ArrayList с Pair</>: места, куда закинуть клетки (сначала строка, потом столбец)
     */
    public void initialize(Pair<Integer ,Integer>[] living_cells) {
        for(Pair<Integer, Integer> pair : living_cells) {
            cells[pair.getKey()][pair.getValue()] = 1;
        }
    }

    /**
     * Считает число соседей для конкретной клетки.
     * @param row Строка, на котором находится клетка
     * @param col Столбец, на котором находится клетка
     * @param isCircled Закольцевать ли (т.е. для граничных клеток проверять также те клетки,
     *                  которые идут с противоположного конца)
     * @return Число соседей
     */
    protected int _countNeighbours(int row, int col, boolean isCircled) {
        char res = 0;
        for(int i = -1; i < 2; i++) {
            int i_1 = i;
            if(row + i == width)
                if(isCircled)
                    i_1 = -width + 1;
                else
                    continue;
            else if(row + i < 0)
                if(isCircled)
                    i_1 = width - 1;
                else
                    continue;
            for(int j = -1; j < 2; j++) {
                int j_1 = j;
                if(col + j == height)
                    j_1 = -height + 1;
                else if(col + j < 0)
                    j_1 = height - 1;
                res += cells[row+i_1][col+j_1];
            }
        }
        res--;
        return res;
    }

    /**
     * Считает число соседей для конкретной клетки.
     * @param row Строка, на котором находится клетка
     * @param col Столбец, на котором находится клетка
     * @return Число соседей
     */
    protected int _countNeighbours(int row, int col) {
        return _countNeighbours(row, col, false);
    }

    /**
     * Сделать один ход игры, т.е. рассчитать, кто умер, а кто родился. Изменяет внутренний массив <i>cells</i>.
     */
    protected void makeEvolution() {
        ArrayList<Integer> marked_row = new ArrayList<>();
        ArrayList<Integer> marked_cols = new ArrayList<>();
        ArrayList<Integer> marked_dead_row = new ArrayList<>();
        ArrayList<Integer> marked_dead_col = new ArrayList<>();
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                int n = _countNeighbours(i, j);
                // Мертва
                if(cells[i][j] == 0) {
                    if(n == 3) {
                        marked_cols.add(j);
                        marked_row.add(i);
                    }
                } else {
                    if(n != 2 && n != 3) {
                        marked_dead_col.add(j);
                        marked_dead_row.add(i);
                    }
                }
            }
        }
        for(int dead_row : marked_dead_row) {
            for(int dead_col : marked_dead_col) {
                cells[dead_row][dead_col] = 0;
            }
        }
        for(int alive_row : marked_row) {
            for(int alive_col : marked_cols) {
                cells[alive_row][alive_col] = 1;
            }
        }
    }

}
