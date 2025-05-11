package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Rook extends Piece {

    public Rook(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.TILESIZE;
        this.yPos = row * board.TILESIZE;
        this.isWhite = isWhite;
        this.name = "Rook"; // Название фигуры

        // Получение нужного изображения
        this.sprite = sheet.getSubimage(4 * sheetScale, isWhite ? 0 : sheetScale, sheetScale,sheetScale).getScaledInstance(Board.TILESIZE, Board.TILESIZE, BufferedImage.SCALE_SMOOTH);
    }

    // Проверка возможности перемещения ладьи
    @Override
    public boolean isValidMovement (int col, int row) {
        return this.col == col || this.row == row;
    }

    // Проверка столкновений при перемещении ладьи
    @Override
    public boolean moveCollidesWithPiece(int col, int row) {
        // Влево
        if (this.col > col) {
            for (int c = this.col - 1; c > col; c--) {
                if (board.getPiece(c, this.row) != null) {
                    return true;
                }
            }
        }
        // Вправо
        if (this.col < col) {
            for (int c = this.col + 1; c < col; c++) {
                if (board.getPiece(c, this.row) != null) {
                    return true;
                }
            }
        }
        // Вверх
        if (this.row > row) {
            for (int r = this.row - 1; r > row; r--) {
                if (board.getPiece(this.col, r) != null) {
                    return true;
                }
            }
        }
        // Вниз
        if (this.row < row) {
            for (int r = this.row + 1; r < row; r++) {
                if (board.getPiece(this.col, r) != null) {
                    return true;
                }
            }
        }

        return false;
    }
}