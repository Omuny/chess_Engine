package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Pawn extends Piece {

    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.TILESIZE;
        this.yPos = row * board.TILESIZE;
        this.isWhite = isWhite;
        this.name = "Pawn"; // Название фигуры

        // Получение нужного изображения
        this.sprite = sheet.getSubimage(5 * sheetScale, isWhite ? 0 : sheetScale, sheetScale,sheetScale).getScaledInstance(Board.TILESIZE, Board.TILESIZE, BufferedImage.SCALE_SMOOTH);
    }

    // Проверка возможности перемещения пешки
    @Override
    public boolean isValidMovement (int col, int row) {
        int colorIndex = isWhite ? 1 : -1;

        // Последующие ходы пешки
        if (this.col == col && row == this.row - colorIndex && board.getPiece(col, row) == null) {
            return true;
        }

        // Первый ход пешки
        if (isFirstMove && this.col == col && row == this.row - colorIndex * 2 && board.getPiece(col, row) == null && board.getPiece(col, row + colorIndex) == null) {
            return true;
        }

        // Съесть слева
        if (col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row) != null ) {
            return true;
        }

        // Съесть справа
        if (col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col, row) != null ) {
            return true;
        }

        // Проход пешки влево
        if (board.getTileNum(col, row) == board.enPassantTile && col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null) {
            return  true;
        }

        // Проход пешки вправо
        if (board.getTileNum(col, row) == board.enPassantTile && col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null) {
            return  true;
        }

        return false;
    }
}