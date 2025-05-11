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
}