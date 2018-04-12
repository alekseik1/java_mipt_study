package week10;

import javafx.util.Pair;
import week7.SimpleGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameGUI extends JFrame {
    private Color color;
    private float t = 0;

    public GameGUI() {
        GameOfLife game = new GameOfLife(5, 5, true);
        Pair<Integer, Integer>[] init_cells = new Pair[]{new Pair(2, 3), new Pair(2, 4), new Pair(1, 3), new Pair(1, 4)};
        game.initialize(init_cells);

        setSize(450, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        color = Color.RED;
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        JButton button = new JButton("Run!");
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.BLUE;
                GameGUI.this.repaint();
                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                Runnable task = () -> {
                    game.makeEvolution();
                    GameGUI.this.repaint();
                };
                executor.scheduleAtFixedRate(task, 0, 1000, TimeUnit.MILLISECONDS);
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        Line2D lin = new Line2D.Float((float) (100*(2+Math.cos(t/50))), (float) (100*(Math.sin(t/50)+2)), 250, 250);
        g2.draw(lin);
    }

    public static void main(String[] args) {
        GameGUI gui = new GameGUI();
        gui.setVisible(true);
    }
}
