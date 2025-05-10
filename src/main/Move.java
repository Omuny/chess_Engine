package main;

import pieces.Piece;

public class Move {

    int oldCol; // Старое значение столбца
    int oldRow; // Старое значение строки
    int newCol; // Новое значение столбца
    int newRow; // Новое значение строки

    Piece piece; // Фигура
    Piece capture;

    public Move(Board board, Piece piece, int newCol, int newRow) {
        this.oldCol = piece.col;
        this.oldRow = piece.row;
        this.newCol = newCol;
        this.newRow = newRow;

        this.piece = piece;
        this.capture = board.getPiece(newCol, newRow);
    }
}