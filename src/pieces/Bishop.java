package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Bishop extends Piece {

    public Bishop(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * Board.TILESIZE;
        this.yPos = row * Board.TILESIZE;
        this.isWhite = isWhite;
        this.name = "Bishop"; // Название фигуры

        // Получение нужного изображения
        this.sprite = sheet.getSubimage(2 * sheetScale, isWhite ? 0 : sheetScale, sheetScale,sheetScale).getScaledInstance(Board.TILESIZE, Board.TILESIZE, BufferedImage.SCALE_SMOOTH);
    }

    // Проверка возможности перемещения слона
    @Override
    public boolean isValidMovement (int col, int row) {
        return Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    // Проверка столкновений при перемещении слона
    @Override
    public boolean moveCollidesWithPiece(int col, int row) {
        // Вверх влево
        if (this.col > col && this.row > row) {
            for (int i = 1; i < Math.abs(this.col - col); i++) {
                if (board.getPiece(this.col - i, this.row - i) != null) {
                    return true;
                }
            }
        }
        // Вверх право
        if (this.col < col && this.row > row) {
            for (int i = 1; i < Math.abs(this.col - col); i++) {
                if (board.getPiece(this.col + i, this.row - i) != null) {
                    return true;
                }
            }
        }
        // Вниз влево
        if (this.col > col && this.row < row) {
            for (int i = 1; i < Math.abs(this.col - col); i++) {
                if (board.getPiece(this.col - i, this.row + i) != null) {
                    return true;
                }
            }
        }
        // Вниз право
        if (this.col < col && this.row < row) {
            for (int i = 1; i < Math.abs(this.col - col); i++) {
                if (board.getPiece(this.col + i, this.row + i) != null) {
                    return true;
                }
            }
        }

        return false;
    }
}