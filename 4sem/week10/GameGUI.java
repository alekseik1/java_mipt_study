package week10;

import com.google.gson.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameGUI {

    private final String NAME_OF_GAME = "Conway's Game of Life";
    private final int START_LOCATION = 100;
    private int LIFE_SIZE_WIDTH = 100;
    private int LIFE_SIZE_HEIGHT = 50;

    private int showDelay = 100;
    private String BACKUP_FILENAME = "game-backup.back";
    private String BACKUP_DIRECTORY = "backups";

    private final int POINT_RADIUS = 5;
    private final int BTN_PANEL_HEIGHT = 48;

    private Canvas canvasPanel;

    private GameLogic game = new GameLogic(LIFE_SIZE_WIDTH, LIFE_SIZE_HEIGHT);
    ScheduledExecutorService gameService;

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
        canvasPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.putNewCells(e.getX()/POINT_RADIUS, e.getY()/POINT_RADIUS);
                canvasPanel.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        JButton fillButton = new JButton("Fill");
        fillButton.addActionListener(new FillButtonListener());

        JButton stepButton = new JButton("Step");
        stepButton.addActionListener((ActionEvent e) -> {
            game.processOfLife();
            canvasPanel.repaint();
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((ActionEvent e) -> {
            dumpToFile(BACKUP_FILENAME);
        });

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener((ActionEvent e) -> {
            try {
                readFromFile(BACKUP_FILENAME);
                canvasPanel.repaint();
            } catch(IOException exc) {
                exc.printStackTrace();
                System.exit(-1);
            }
        });

        final JButton startGUIButton = new JButton("Play");
        startGUIButton.addActionListener(e -> {
            game.toggleState();
            startGame();
            startGUIButton.setText(game.getState()? "Stop" : "Play");
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(fillButton);
        btnPanel.add(stepButton);
        btnPanel.add(startGUIButton);
        btnPanel.add(saveButton);
        btnPanel.add(loadButton);

        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, btnPanel);
        frame.setVisible(true);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                LIFE_SIZE_WIDTH = frame.getWidth();
                LIFE_SIZE_HEIGHT = frame.getHeight();
                game.toggleState();
                stopGame();
                game.resizeGame(LIFE_SIZE_WIDTH, LIFE_SIZE_HEIGHT);
                startGame();
                canvasPanel.repaint();
                game.toggleState();
                startGUIButton.setText(game.getState()? "Stop" : "Play");
            }
        });
    }

    // randomly fill cells
    public class FillButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            game.fillRandomly();
            canvasPanel.repaint();
        }
    }

    void stopGame(){
        if(gameService != null) gameService.shutdownNow();
    }

    void startGame() {
        // endless loop of life
        gameService = Executors.newScheduledThreadPool(1);
        Runnable run_gui = () -> {
            if (game.getState()) {
                game.processOfLife();
                canvasPanel.repaint();
            }
        };
        gameService.scheduleAtFixedRate(run_gui, 0, showDelay, TimeUnit.MILLISECONDS);
    }

    // paint on the canvas
    public class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int x = 0; x < game.getWidth(); x++) {
                for (int y = 0; y < game.getHeight(); y++) {
                    if (game.lifeGeneration[x][y]) {
                        g.fillOval(x*POINT_RADIUS, y*POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
                    }
                }
            }
        }
    }

    private boolean createDumpFolder(String path) {
        File file = new File(path);
        try {
            return file.mkdirs();
        } catch(SecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void dumpToFile(String filename) {
        // Check if backup folder is available
        File file = new File(BACKUP_DIRECTORY);
        try {
            if(!(file.isDirectory() && file.canWrite())) {
                createDumpFolder(BACKUP_DIRECTORY);
            }
        } catch(SecurityException e) {
            e.printStackTrace();
        }
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(BACKUP_DIRECTORY + File.separator + BACKUP_FILENAME)
            );
            out.writeObject(game);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Читает массив из файла. Может бросать {@link IOException}
     * @param filename Путь до файла
     * @throws IOException
     */
    public void readFromFile(String filename) throws IOException {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(BACKUP_DIRECTORY + File.separator + filename));
            game = (GameLogic) in.readObject();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}