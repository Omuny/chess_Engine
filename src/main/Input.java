package main;

import pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Input extends MouseAdapter {

    Board board;

    public Input (Board board) {
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        // Получение строки и столбца при нажатии
        int col = e.getX() / board.TILESIZE;
        int row = e.getY() / board.TILESIZE;

        // Выбор текущей фигуры
        Piece pieceXY = board.getPiece(col, row);
        if(pieceXY != null) {
            board.selectedPiece = pieceXY;
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {

        // Перемещение фигуры при зажатой фигуре
        if (board.selectedPiece != null) {
            board.selectedPiece.xPos = e.getX() - board.TILESIZE / 2;
            board.selectedPiece.yPos = e.getY() - board.TILESIZE / 2;

            board.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        // Получение строки и столбца при отпускании
        int col = e.getX() / board.TILESIZE;
        int row = e.getY() / board.TILESIZE;

        // Перемещение фигуры
        if (board.selectedPiece != null) {
            Move move = new Move(board, board.selectedPiece, col, row);

            if (board.isValidMove(move)) {
                board.makeMove(move);
            } else {
                board.selectedPiece.xPos = board.selectedPiece.col * board.TILESIZE;
                board.selectedPiece.yPos = board.selectedPiece.row * board.TILESIZE;
            }
        }

        // Убираем захваченную фигуру и перерисовываем
        board.selectedPiece = null;
        board.repaint();
    }
}