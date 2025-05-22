package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Board extends JPanel {

    public final String fenStartingPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"; // Стартовая позиция
    public final String fenTest = "r3k2r/8/8/8/2Pp4/8/8/R3K2R b KQkq c3 0 1"; // тестовая позиция
    public final String fen2Bishops = "4k3/8/8/8/8/8/8/2BBK3 w - - 0 1"; //  позиция с двумя слонами у белых

    public static final int TILESIZE = 75; // Размер плитки

    static final int COLS = 8; // столбцы
    static final int ROWS = 8; // строки

    ArrayList<Piece> pieceList = new ArrayList<>(); // Лист фигур

    public Piece selectedPiece; // Выбранная фигура

    Input input = new Input(this);

    public CheckScanner checkScanner = new CheckScanner(this);

    public int enPassantTile = -1; // Проходная клетка

    private boolean isWhiteMove = true; // Ход белых
    private boolean isGameOver = false; // Конец игры

    public Board() {
        this.setPreferredSize(new Dimension(COLS * TILESIZE, ROWS * TILESIZE)); // Размер окна

        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        // Устанавливаем позицию по желанию
        loadPositionFromFEN(fenStartingPosition); // Добавление фигур на доску
    }

    // Получение фигуры в определенном столбце и строке
    public Piece getPiece(int col, int row) {
       for (Piece piece : pieceList) {
           if (piece.col == col && piece.row == row) {
               return piece;
           }
       }
       return null;
    }

    // Перемещение фигуры
    public void makeMove(Move move) {
        if (move.piece.name.equals("Pawn")) {
            movePawn(move);
        } else {
            enPassantTile = -1;
        }
        if (move.piece.name.equals("King")) {
            moveKing(move);
        }

        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPos = move.newCol * TILESIZE;
        move.piece.yPos = move.newRow * TILESIZE;

        move.piece.isFirstMove = false; // Первый ход прошел

        capture(move.capture);

        isWhiteMove = !isWhiteMove; // Смена хода

        updateGameState(); // Проверка шаха, мата, пата
    }

    // Перемещение короля
    private void moveKing(Move move) {
        if (Math.abs(move.piece.col - move.newCol) == 2) {
            Piece rook;
            if (move.piece.col < move.newCol) {
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
            } else {
                rook = getPiece(0, move.piece.row);
                rook.col = 3;
            }
            rook.xPos = rook.col * TILESIZE;
        }
    }

    // Перемещение пешки
    private void movePawn(Move move) {
        // Проход пешки
        int colorIndex = move.piece.isWhite ? 1 : -1;

        if (getTileNum(move.newCol, move.newRow) == enPassantTile) {
            move.capture = getPiece(move.newCol, move.newRow + colorIndex);
        }

        if (Math.abs(move.piece.row - move.newRow) == 2) {
            enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
        } else {
            enPassantTile = -1;
        }

        // Превращение пешки
        colorIndex = move.piece.isWhite ? 0 : 7;
        if (move.newRow == colorIndex) {
            promotePawn(move);
        }
    }

    // Превращение пешки
    private void promotePawn(Move move) {
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }

    // Удаление захваченной фигуры из списка
    public void capture(Piece piece) {
        pieceList.remove(piece);
    }

    // Проверка возможности перемещения
    public boolean isValidMove(Move move) {
        // Проверка конца игры
        if (isGameOver) {
            return false;
        }

        // Проверка очереди хода
        if (move.piece.isWhite != isWhiteMove) {
            return false;
        }

        // Проверка цвета
        if (sameTeam(move.piece, move.capture)) {
            return false;
        }

        // Проверка доступных для перемещения фигуры клеток
        if (!move.piece.isValidMovement(move.newCol, move.newRow)) {
            return false;
        }

        // Проверка столкновений фигур
        if (move.piece.moveCollidesWithPiece(move.newCol, move.newRow)) {
            return false;
        }

        // Проверка шаха
        if (checkScanner.isKingChecked(move)) {
            return false;
        }

        return true;
    }

    // Проверка цвета
    public boolean sameTeam(Piece p1, Piece p2) {
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }

    // Получение номера клетки
    public int getTileNum(int col, int row) {
        return row * ROWS + col;
    }

    // Получение короля
    Piece findKing(boolean isWhite) {
        for (Piece piece : pieceList) {
            if (isWhite == piece.isWhite && piece.name.equals("King")) {
                return piece;
            }
        }
        return null;
    }

    // Добавление фигур начальной и произвольной позиции
    public void loadPositionFromFEN(String fenString) {
        pieceList.clear(); // Очистка доски
        String[] parts = fenString.split(" ");

        // Установка фигур
        String position = parts[0];
        int row = 0;
        int col = 0;
        for (int i = 0; i < position.length(); i++) {
            char ch = position.charAt(i);
            if (ch == '/') {
                row++;
                col = 0;
            } else if (Character.isDigit(ch)) {
                col += Character.getNumericValue(ch);
            } else {
                boolean isWhite = Character.isUpperCase(ch);
                char pieceChar = Character.toLowerCase(ch);

                switch (pieceChar) {
                    case 'r':
                        pieceList.add(new Rook(this, col, row, isWhite));
                        break;
                    case 'n':
                        pieceList.add(new Knight(this, col, row, isWhite));
                        break;
                    case 'b':
                        pieceList.add(new Bishop(this, col, row, isWhite));
                        break;
                    case 'q':
                        pieceList.add(new Queen(this, col, row, isWhite));
                        break;
                    case 'k':
                        pieceList.add(new King(this, col, row, isWhite));
                        break;
                    case 'p':
                        pieceList.add(new Pawn(this, col, row, isWhite));
                        break;
                }
                col++;
            }
        }

        // Цвет, ходящего первым
        isWhiteMove = parts[1].equals("w");

        // Возможность рокировки в позиции
        Piece bqr = getPiece(0, 0);
        if (bqr instanceof Rook) {
            bqr.isFirstMove = parts[2].contains("q");
        }
        Piece bkr = getPiece(7, 0);
        if (bkr instanceof Rook) {
            bkr.isFirstMove = parts[2].contains("k");
        }
        Piece wqr = getPiece(0, 7);
        if (wqr instanceof Rook) {
            wqr.isFirstMove = parts[2].contains("Q");
        }
        Piece wkr = getPiece(7, 7);
        if (wkr instanceof Rook) {
            wkr.isFirstMove = parts[2].contains("K");
        }

        // Возможность съесть проходящую пешку
        if (parts[3].equals("-")) {
            enPassantTile = -1;
        } else {
            enPassantTile = (7 - (parts[3].charAt(1) - '1')) * 8 + (parts[3].charAt(0) - 'a');
        }
    }

    // Проверка конца игры и шаха
    private void updateGameState() {
        Piece king = findKing(isWhiteMove);
        if (checkScanner.isGameOver(king)) {
            if (checkScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
                System.out.println(isWhiteMove ? "Черные выиграли" : "Белые выиграли");
            } else {
                System.out.println("Пат");
            }
            isGameOver = true;
        } else if (insuffientMaterial(true) && insuffientMaterial(false)) {
            System.out.println("Ничья");
            isGameOver = true;
        }
    }

    // Проверка оставшихся фигур на доске
    private boolean insuffientMaterial(boolean isWhite) {
        ArrayList<String> names = pieceList.stream()
                .filter(p -> p.isWhite == isWhite)
                .map(p -> p.name)
                .collect(Collectors.toCollection(ArrayList::new));
        if (names.contains("Queen") || names.contains("Rook") || names.contains("Pawn")) {
            return false;
        }
        return names.size() < 3;
    }

    // Отрисовка Компонентов
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Окрашивание клеток доски в цвета
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                g2d.setColor((c + r) % 2 == 0 ? new Color(227, 198, 181) : new Color(157, 105, 53));
                g2d.fillRect(c * TILESIZE, r * TILESIZE, TILESIZE, TILESIZE);
            }
        }

        // Подсветка доступных клеток
        if (selectedPiece != null) {
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    if (isValidMove(new Move(this, selectedPiece, c, r))) {
                        g2d.setColor(new Color(68, 180, 57, 190));
                        g2d.fillRect(c * TILESIZE, r * TILESIZE, TILESIZE, TILESIZE);
                    }
                }
            }
        }

        // Отрисовка фигур
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
    }
}