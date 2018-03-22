package week7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.concurrent.*;
import java.util.concurrent.Executors;

public class SimpleGUI extends JFrame {
    private Color color;
    private float t = 0;

    public SimpleGUI() {
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        color = Color.RED;
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        JButton button = new JButton("Run!");
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.BLUE;
                SimpleGUI.this.repaint();
                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                Runnable task = () -> {
                    t+= 1;
                    SimpleGUI.this.repaint();
                };
                executor.scheduleAtFixedRate(task, 0, 1000/25, TimeUnit.MILLISECONDS);
            }
        });
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                color = Color.GREEN;
                SimpleGUI.this.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                color = Color.BLACK;
                SimpleGUI.this.repaint();
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
        SimpleGUI gui = new SimpleGUI();
        gui.setVisible(true);
    }
}
