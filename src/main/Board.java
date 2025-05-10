package main;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    public static final int TILESIZE = 60; // Размер плитки

    int cols = 8; // столбцы
    int rows = 8; // строки

    public Board() {
        this.setPreferredSize(new Dimension(cols * TILESIZE, rows * TILESIZE)); // Размер окна
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Окрашивание клеток в цвета
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                g2d.setColor((c + r) % 2 == 0 ? new Color(227, 198, 181) : new Color(157, 105, 53));
                g2d.fillRect(c * TILESIZE, r * TILESIZE, TILESIZE, TILESIZE);
            }
    }
}