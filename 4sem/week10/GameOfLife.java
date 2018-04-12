package week10;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Игра "Жизнь" Джона Конвоя
 */
public class GameOfLife {

    protected final int width, height;

    protected boolean[][] cells;

    GameOfLife(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new boolean[width][height];
    }

    /**
     * Инициализировать игру, забив определенные ячейки живыми клетками
     * @param living_cells ArrayList с Pair</>: места, куда закинуть клетки (сначала строка, потом столбец)
     */
    public void initialize(ArrayList<Pair<Integer ,Integer>> living_cells) {
        for(Pair<Integer, Integer> pair : living_cells) {
            cells[pair.getKey()][pair.getValue()] = true;
        }
    }

}
