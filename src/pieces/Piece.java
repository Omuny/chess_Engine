package pieces;

import main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {

    public int col, row; // Строка и столбец
    public int xPos, yPos; // X и Y позиции

    public boolean isWhite; // Цвет фигуры
    public String name; // Назавание
    public int value; // Значение
    Image sprite; // Спрайт конкретной фигуры
    Board board;

    // Загрузка изображения фигур
    BufferedImage sheet;
    {
        try {
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("pieces.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected int sheetScale = sheet.getWidth() / 6; // Масштаб

    public Piece(Board board) {
        this.board = board;
    }

    // Проверка возможности перемещения
    public boolean isValidMovement(int col, int row) {
        return true;
    }

    // Проверка столкновений фигур
    public boolean moveCollidesWithPiece(int col, int row) {
        return false;
    }

    // Отрисовка фигур
    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos,null);
    }
}