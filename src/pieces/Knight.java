package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Knight extends Piece {

    public Knight(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * Board.TILESIZE;
        this.yPos = row * Board.TILESIZE;
        this.isWhite = isWhite;
        this.name = "Knight"; // Название фигуры

        // Получение нужного изображения
        this.sprite = sheet.getSubimage(3 * sheetScale, isWhite ? 0 : sheetScale, sheetScale,sheetScale).getScaledInstance(Board.TILESIZE, Board.TILESIZE, BufferedImage.SCALE_SMOOTH);
    }

    // Проверка возможности перемещения коня
    @Override
    public boolean isValidMovement (int col, int row) {
        return Math.abs(col - this.col) * Math.abs(row - this.row) == 2;
    }
}