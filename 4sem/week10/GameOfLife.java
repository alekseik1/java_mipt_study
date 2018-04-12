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
    public void initialize(ArrayList<Pair<Integer ,Integer>> living_cells) {
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
            if(row + i == width)
                i = -width + 1;
            else if(row + i < 0)
                i = width - 1;
            for(int j = -1; j < 2; j++) {
                if(col + j == height)
                    j = -height + 1;
                else if(col + j < 0)
                    j = height - 1;
                res += cells[row+i][col+j];
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
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                int n = _countNeighbours(i, j);
                // Мертва
                if(cells[i][j] == 0) {
                    if(n == 3) {
                        cells[i][j] = 1;
                    }
                } else {
                    if(n != 2 && n != 3) {
                        cells[i][j] = 0;
                    }
                }
            }
        }
    }

}
