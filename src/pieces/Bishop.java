package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Bishop extends Piece {

    public Bishop(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.TILESIZE;
        this.yPos = row * board.TILESIZE;
        this.isWhite = isWhite;
        this.name = "Bishop"; // Название фигуры

        // Получение нужного изображения
        this.sprite = sheet.getSubimage(2 * sheetScale, isWhite ? 0 : sheetScale, sheetScale,sheetScale).getScaledInstance(Board.TILESIZE, Board.TILESIZE, BufferedImage.SCALE_SMOOTH);

    }
}