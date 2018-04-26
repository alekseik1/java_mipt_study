package week10;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameLogic implements Serializable {
    static final long serialVersionUID = 1L;
    private int LIFE_SIZE_WIDTH;
    private int LIFE_SIZE_HEIGHT;
    boolean[][] lifeGeneration;
    boolean[][] nextGeneration;
    private volatile boolean startGUINextGeneration = false; // fixed the problem in 64-bit JVM
    Random random = new Random();

    int getWidth() {
        return LIFE_SIZE_WIDTH;
    }

    int getHeight() {
        return LIFE_SIZE_HEIGHT;
    }

    GameLogic(int width, int height) {
        LIFE_SIZE_WIDTH = width;
        LIFE_SIZE_HEIGHT = height;
        lifeGeneration = new boolean[LIFE_SIZE_WIDTH][LIFE_SIZE_HEIGHT];
        nextGeneration = new boolean[LIFE_SIZE_WIDTH][LIFE_SIZE_HEIGHT];
    }

    // count the number of neighbors
     private int countNeighbors(int x, int y) {
        int count = 0;
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                int nX = x + dx;
                int nY = y + dy;
                nX = (nX < 0) ? LIFE_SIZE_WIDTH - 1 : nX;
                nY = (nY < 0) ? LIFE_SIZE_HEIGHT - 1 : nY;
                nX = (nX > LIFE_SIZE_WIDTH - 1) ? 0 : nX;
                nY = (nY > LIFE_SIZE_HEIGHT - 1) ? 0 : nY;
                count += (lifeGeneration[nX][nY]) ? 1 : 0;
            }
        }
        if (lifeGeneration[x][y]) { count--; }
        return count;
    }

    // the main process of life
    public void processOfLife() {
        for (int x = 0; x < LIFE_SIZE_WIDTH; x++) {
            for (int y = 0; y < LIFE_SIZE_HEIGHT; y++) {
                int count = countNeighbors(x, y);
                nextGeneration[x][y] = lifeGeneration[x][y];
                // if are 3 live neighbors around empty cells - the cell becomes alive
                nextGeneration[x][y] = (count == 3) || nextGeneration[x][y];
                // if cell has less than 2 or greater than 3 neighbors - it will be die
                nextGeneration[x][y] = ((count >= 2) && (count <= 3)) && nextGeneration[x][y];
            }
        }
        for (int x = 0; x < LIFE_SIZE_WIDTH; x++) {
            System.arraycopy(nextGeneration[x], 0, lifeGeneration[x], 0, LIFE_SIZE_HEIGHT);
        }
    }

    void resizeGame(int newWidth, int newHeight) {
        LIFE_SIZE_HEIGHT = newHeight;
        LIFE_SIZE_WIDTH = newWidth;
        lifeGeneration = new boolean[newWidth][newHeight];
        nextGeneration = new boolean[newWidth][newHeight];
        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                lifeGeneration[x][y] = random.nextBoolean();
            }
        }
    }

    void fillRandomly() {
        for (int x = 0; x < LIFE_SIZE_WIDTH; x++) {
            for (int y = 0; y < LIFE_SIZE_HEIGHT; y++) {
                lifeGeneration[x][y] = random.nextBoolean();
            }
        }
    }

    void putNewCells(int x, int y) {
        int MAGIC_CONST = 2;
        for(int i = -MAGIC_CONST; i < MAGIC_CONST; i++) {
            for(int j = -MAGIC_CONST; j < MAGIC_CONST; j++) {
                lifeGeneration[x + i][y + j] = true;
            }
        }
    }

    void toggleState(){
        startGUINextGeneration = !startGUINextGeneration;
    }

    boolean getState(){
        return !startGUINextGeneration;
    }
}
