package main;

import pieces.Piece;

public class CheckScanner {

    Board board;

    public CheckScanner(Board board) {
        this.board = board;
    }

    // Проверка шаха королю
    public boolean isKingChecked(Move move) {
        Piece king = board.findKing(move.piece.isWhite);
        assert king != null;

        int kingCol = king.col;
        int kingRow = king.row;

        if (board.selectedPiece != null && board.selectedPiece.name.equals("King")) {
            kingCol = move.newCol;
            kingRow = move.newRow;
        }

                // Ладья
        return  hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 0, 1) || // Вверх
                hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 1, 0) || // Вправо
                hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 0, -1) || // Вниз
                hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, -1, 0) || // Влево
                // Слон
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, -1) || // Вверх влево
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, -1) ||// Вверх право
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, 1) || // Вниз вправо
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, 1) || // Вниз влево

                hitByKnight(move.newCol, move.newRow, king, kingCol, kingRow) || // Конь
                hitByPawn(move.newCol, move.newRow, king, kingCol, kingRow) || // Пешка
                hitByKing(king, kingCol, kingRow); // Король
    }

    // Нападение ладьёй или ферзем по прямой
    private boolean hitByRook(int col, int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal) {
        for(int i = 1; i < 8; i++) {
            if (kingCol + (i * colVal) == col && kingRow + (i * rowVal) == row) {
                break;
            }

            Piece piece = board.getPiece(kingCol + (i * colVal), kingRow + (i * rowVal));
            if (piece != null && piece != board.selectedPiece) {
                if (!board.sameTeam(piece, king) && (piece.name.equals("Rook") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    // Нападение слоном или ферзем по прямой
    private boolean hitByBishop(int col, int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal) {
        for(int i = 1; i < 8; i++) {
            if (kingCol - (i * colVal) == col && kingRow - (i * rowVal) == row) {
                break;
            }

            Piece piece = board.getPiece(kingCol - (i * colVal), kingRow - (i * rowVal));
            if (piece != null && piece != board.selectedPiece) {
                if (!board.sameTeam(piece, king) && (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    // Нападение конём
    private boolean hitByKnight(int col, int row, Piece king, int kingCol, int kingRow) {
        return  checkKnight(board.getPiece(kingCol - 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow - 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow - 1), king, col, row);
    }

    // Проверка коня
    private boolean checkKnight(Piece p, Piece k, int col, int row) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("Knight") && !(p.col == col && p.row == row);
    }

    // Нападение на короля
    private boolean hitByKing(Piece king, int kingСol, int kingRow) {
        return  checkKing(board.getPiece(kingСol - 1, kingRow - 1), king) ||
                checkKing(board.getPiece(kingСol + 1, kingRow - 1), king) ||
                checkKing(board.getPiece(kingСol, kingRow - 1), king) ||
                checkKing(board.getPiece(kingСol - 1, kingRow), king) ||
                checkKing(board.getPiece(kingСol + 1, kingRow), king) ||
                checkKing(board.getPiece(kingСol - 1, kingRow + 1), king) ||
                checkKing(board.getPiece(kingСol + 1, kingRow + 1), king) ||
                checkKing(board.getPiece(kingСol, kingRow + 1), king);
    }

    // Проверка короля
    private boolean checkKing(Piece p, Piece k) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("King");
    }

    // Нападение пешкой
    private boolean hitByPawn(int col, int row, Piece king, int kingCol, int kingRow) {
        int colorVal = king.isWhite ? -1 : 1;
        return  checkPawn(board.getPiece(kingCol + 1, kingRow +colorVal), king, col, row) ||
                checkPawn(board.getPiece(kingCol - 1, kingRow +colorVal), king, col, row);
    }

    // Проверка пешки
    private boolean checkPawn(Piece p, Piece k, int col, int row) {
        return p != null && !board.sameTeam(p, k) && p.name.equals(("Pawn")) && !(p.col == col && p.row == row);
    }

    // Конец игры
    public boolean isGameOver(Piece king) {
        for (Piece piece : board.pieceList) {
            if (board.sameTeam(piece, king)) {
                board.selectedPiece = piece == king ? king : null;
                for (int row = 0; row < board.ROWS; row++) {
                    for (int col = 0; col < board.COLS; col++) {
                        Move move = new Move(board, piece, col, row);
                        if (board.isValidMove(move)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}