package week10;

import javafx.util.Pair;
/**
 * Игра "Жизнь" Джона Конвоя
 */
public class GameOfLife {

    protected final int width, height;

    protected char[][] cells_before, cells_after;

    protected final boolean isCircled;

    /**
     * Создает игру
     * @param width Длина
     * @param height Высота
     * @param isCircled Закольцевать ли (т.е. для граничных клеток проверять также те клетки,
     *                  которые идут с противоположного конца)
     */
    GameOfLife(int width, int height, boolean isCircled) {
        this.width = width;
        this.height = height;
        this.isCircled = isCircled;
        cells_before = new char[width][height];
        cells_after = new char[width][height];
    }

    /**
     * Инициализировать игру, забив определенные ячейки живыми клетками
     * @param living_cells ArrayList с Pair</>: места, куда закинуть клетки (сначала строка, потом столбец)
     */
    public void initialize(Pair<Integer ,Integer>[] living_cells) {
        for(Pair<Integer, Integer> pair : living_cells) {
            cells_before[pair.getKey()][pair.getValue()] = 1;
        }
    }

    /**
     * Считает число соседей для конкретной клетки.
     * @param row Строка, на котором находится клетка
     * @param col Столбец, на котором находится клетка
     * @return Число соседей
     */
    protected char _countNeighbours(int row, int col) {
        char res = 0;
        for (int i = -1; i < 2; i++) {
            if (!isCircled && (row + i == width || row + i < 0))
                continue;
            for (int j = -1; j < 2; j++) {
                if (!isCircled && (col + j == height || col + j < 0))
                    continue;
                if (i == row && j == col)
                    continue;
                res += cells_before[(row + i + width) % width][(col + j + height) % height];
            }
        }
        return res;
    }

    /**
     * Сделать один ход игры, т.е. рассчитать, кто умер, а кто родился. Изменяет внутренний массив <i>cells</i>.
     */
    protected void makeEvolution() {
        cells_after = cells_before;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                char n = _countNeighbours(i, j);
                // Мертва
                if(cells_before[i][j] == 0) {
                    if (n == 3) {
                        cells_after[i][j] = 1;
                    }
                } else {
                    if(n != 2 && n != 3) {
                        cells_after[i][j] = 0;
                    }
                }
            }
        }
        cells_before = cells_after;
    }

}
