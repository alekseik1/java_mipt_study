package week10;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameGUI {

    private final String NAME_OF_GAME = "Conway's Game of Life";
    private final int START_LOCATION = 100;
    private int LIFE_SIZE_WIDTH = 500;
    private int LIFE_SIZE_HEIGHT = 250;
    private int showDelay = 200;

    private final int POINT_RADIUS = 3;
    private final int BTN_PANEL_HEIGHT = 48;
    private boolean[][] lifeGeneration = new boolean[LIFE_SIZE_WIDTH][LIFE_SIZE_HEIGHT];
    private boolean[][] nextGeneration = new boolean[LIFE_SIZE_WIDTH][LIFE_SIZE_HEIGHT];
    private volatile boolean startGUINextGeneration = false; // fixed the problem in 64-bit JVM
    private Canvas canvasPanel;
    private Random random = new Random();

    public static void main(String[] args) {
        new GameGUI().startGUI();
    }

    private void startGUI() {
        final JFrame frame = new JFrame(NAME_OF_GAME);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int FIELD_SIZE_WIDTH = LIFE_SIZE_WIDTH * POINT_RADIUS + 7;
        int FIELD_SIZE_HEIGHT = LIFE_SIZE_HEIGHT * POINT_RADIUS + 7;
        frame.setSize(FIELD_SIZE_WIDTH, FIELD_SIZE_HEIGHT + BTN_PANEL_HEIGHT);
        frame.setLocation(START_LOCATION, START_LOCATION);
        frame.setResizable(true);

        canvasPanel = new Canvas();
        canvasPanel.setBackground(Color.white);

        JButton fillButton = new JButton("Fill");
        fillButton.addActionListener(new FillButtonListener());

        JButton stepButton = new JButton("Step");
        stepButton.addActionListener((ActionEvent e) -> {
            processOfLife();
            canvasPanel.repaint();
        });

        final JButton startGUIButton = new JButton("Play");
        startGUIButton.addActionListener(e -> {
            startGUINextGeneration = !startGUINextGeneration;
            startGUIButton.setText(startGUINextGeneration? "Stop" : "Play");
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(fillButton);
        btnPanel.add(stepButton);
        btnPanel.add(startGUIButton);

        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, btnPanel);
        frame.setVisible(true);

        // endless loop of life
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        Runnable run_gui = () -> {
            if (startGUINextGeneration) {
                processOfLife();
                canvasPanel.repaint();
            }
        };
        service.scheduleAtFixedRate(run_gui, 0, showDelay, TimeUnit.MILLISECONDS);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                System.out.println("REPAINTER");
                startGUINextGeneration = !startGUINextGeneration;
                LIFE_SIZE_WIDTH = frame.getWidth();
                LIFE_SIZE_HEIGHT = frame.getHeight();
                lifeGeneration = new boolean[LIFE_SIZE_WIDTH][LIFE_SIZE_HEIGHT];
                nextGeneration = new boolean[LIFE_SIZE_WIDTH][LIFE_SIZE_HEIGHT];
                canvasPanel.repaint();
                startGUINextGeneration = !startGUINextGeneration;
            }
        });
    }

    // randomly fill cells
    public class FillButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            for (int x = 0; x < LIFE_SIZE_WIDTH; x++) {
                for (int y = 0; y < LIFE_SIZE_HEIGHT; y++) {
                    lifeGeneration[x][y] = random.nextBoolean();
                }
            }
            canvasPanel.repaint();
        }
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
    private void processOfLife() {
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

    // paint on the canvas
    public class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int x = 0; x < LIFE_SIZE_WIDTH; x++) {
                for (int y = 0; y < LIFE_SIZE_HEIGHT; y++) {
                    if (lifeGeneration[x][y]) {
                        g.fillOval(x*POINT_RADIUS, y*POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
                    }
                }
            }
        }
    }
}