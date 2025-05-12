package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame();

        frame.setTitle("Chess");
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(750, 750)); // Минимальный размер окна
        frame.setLocationRelativeTo(null); // Центрирование окна
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Board board = new Board();
        frame.add(board);
    }
}