package week10;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameGUI extends JFrame {
    private Color color;


    public GameGUI() {
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        color = Color.GREEN;
        JPanel panel = new JPanel();
    }

    public static void main(String[] args) {
        GameOfLife game = new GameOfLife(5, 5);
        Pair<Integer, Integer>[] init_cells = new Pair[]{new Pair(2, 3), new Pair(2, 4), new Pair(1, 3), new Pair(1, 4)};
        game.initialize(init_cells);
        game.makeEvolution();
        System.out.println();
    }
}
