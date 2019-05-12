package ija.ija2018.homework2.common;

import ija.ija2018.homework2.game.Board;
import javafx.util.Pair;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Chess implements Game {
    protected Board board;
    protected int size;
    protected Stack<Field> movedFrom;
    protected Stack<Field> movedTo;
    protected int turnNum;
    protected String lastMove;
    protected LinkedList<Figure> gamePlayFigures;
    protected LinkedList<Field> gamePlayFields;
    protected ArrayList<String> moves;
    protected int annotationIndex = 0;

    public Chess(Board board) {
        this.board = board;
        this.size = board.getSize();
        this.movedFrom = new Stack<Field>();
        this.movedTo = new Stack<Field>();
        this.turnNum = 1;
        this.gamePlayFigures = new LinkedList<Figure>();
        this.gamePlayFields = new LinkedList<Field>();
        this.moves = new ArrayList<String>();

        for(int i = 1; i <= size; i++) {
            Pawn whitePawn = new Pawn(true, "whitePawn");
            whitePawn.setPosition(board.getField(i, 7));
            board.getField(i, 7).put(whitePawn);

            Pawn blackPawn = new Pawn(false, "blackPawn");
            blackPawn.setPosition(board.getField(i, 2));
            board.getField(i, 2).put(blackPawn);
        }

        King whiteKing1 = new King(true, "whiteKing");
        whiteKing1.setPosition(board.getField(5, 8));
        board.getField(5, 8).put(whiteKing1);

        King blackKing1 = new King(false, "blackKing");
        blackKing1.setPosition(board.getField(5, 1));
        board.getField(5, 1).put(blackKing1);

        Queen whiteQueen1 = new Queen(true, "whiteQueen");
        whiteQueen1.setPosition(board.getField(4, 8));
        board.getField(4, 8).put(whiteQueen1);

        Queen blackQueen1 = new Queen(false, "blackQueen");
        blackQueen1.setPosition(board.getField(4, 1));
        board.getField(4, 1).put(blackQueen1);

        Knight whiteKnight1 = new Knight(true, "whiteKnight");
        whiteKnight1.setPosition(board.getField(2, 8));
        board.getField(2, 8).put(whiteKnight1);
        Knight whiteKnight3 = new Knight(true, "whiteKnight");
        whiteKnight3.setPosition(board.getField(7, 8));
        board.getField(7, 8).put(whiteKnight3);

        Knight blackKnight1 = new Knight(false, "blackKnight");
        blackKnight1.setPosition(board.getField(7, 1));
        board.getField(7, 1).put(blackKnight1);
        Knight blackKnight2 = new Knight(false, "blackKnight");
        blackKnight2.setPosition(board.getField(2, 1));
        board.getField(2, 1).put(blackKnight2);

        Bishop whiteBishop1 = new Bishop(true, "whiteBishop");
        whiteBishop1.setPosition(board.getField(3, 8));
        board.getField(3, 8).put(whiteBishop1);
        Bishop whiteBishop2 = new Bishop(true, "whiteBishop");
        whiteBishop2.setPosition(board.getField(6, 8));
        board.getField(6, 8).put(whiteBishop2);

        Bishop blackBishop1 = new Bishop(false, "blackBishop");
        blackBishop1.setPosition(board.getField(3, 1));
        board.getField(6, 1).put(blackBishop1);
        Bishop blackBishop2 = new Bishop(false, "blackBishop");
        blackBishop2.setPosition(board.getField(6, 1));
        board.getField(3, 1).put(blackBishop2);

        Rook whiteRook1 = new Rook(true, "whiteRook");
        whiteRook1.setPosition(board.getField(1, 8));
        board.getField(1, 8).put(whiteRook1);
        Rook whiteRook2 = new Rook(true, "whiteRook");
        whiteRook2.setPosition(board.getField(8, 8));
        board.getField(8, 8).put(whiteRook2);

        Rook blackRook1 = new Rook(false, "blackRook");
        blackRook1.setPosition(board.getField(8, 1));
        board.getField(8, 1).put(blackRook1);
        Rook blackRook2 = new Rook(false, "blackRook");
        blackRook2.setPosition(board.getField(1, 1));
        board.getField(1, 1).put(blackRook2);
    }

    @Override
    public void undo() {
        if (turnNum == 1)
            return;

        this.turnNum--;
        Field field1 = movedTo.pop();
        Field field2 = movedFrom.pop();

        field1.undo();
        field2.undo();
    }

    // Used for calculation of Check or Mat
    private Figure getKing() {
        // Find position of King
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                Field field = board.getField(i, j);

                if (field.isEmpty())
                    continue;

                Figure figure = field.get();

                if (figure.isWhite() != (turnNum % 2 == 1))
                    continue;

                if (figure.getClass().getSimpleName().equals("King")) {
                    return figure;
                }
            }
        }

        return null;
    }

    private boolean isPositionThreatened(Field threatenedPosition) {
        // Iterate over every enemy figure and see if any threatens given field
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                Field field = board.getField(i, j);

                if (field.isEmpty())
                    continue;

                Figure figure = field.get();

                if (figure.isWhite() == (turnNum % 2 == 1))
                    continue;

                if (figure.canMove(threatenedPosition))
                    return true;
            }
        }

        return false;
    }

    private boolean isMat() {
        if (!isCheck())
            return false;

        // Find position of King
        Figure king = getKing();

        // TODO delete later
        if (king == null)
            return false;

        Field kingPosition = king.getPosition();

        // Find every position where king can move
        for (Field.Direction dir : Field.Direction.values()) {
            Field nextPosition = kingPosition.nextField(dir);

            if (nextPosition != null && king.canMove(nextPosition)) {
                if (!isPositionThreatened(nextPosition))
                    return false;
            }
        }

        return true;
    }

    private boolean isCheck() {
        // Find position of King
        Figure king = getKing();

        // TODO delete later
        if (king == null)
            return false;

        return isPositionThreatened(king.getPosition());
    }

    private void updateLastMove(Figure figure, Field field, boolean capturing) {
        String newVal;

        String type = figure.getClass().getSimpleName();
        switch(type) {
            case "King":
                newVal = "K";
                break;
            case "Queen":
                newVal = "D";
                break;
            case "Rook":
                newVal = "B";
                break;
            case "Bishop":
                newVal = "S";
                break;
            case "Knight":
                newVal = "J";
                break;
            default:
                newVal = "";
        }

        if (capturing)
            newVal += "x";

        int[] position = field.getPosition();
        char col = (char)(position[0] + 96);
        int row = position[1];

        newVal += col;
        newVal += row;

        if (isMat())
            newVal += "#";
        else if (isCheck())
            newVal += "+";

        this.lastMove = newVal;
    }

    @Override
    public String getLastMove() {
        return lastMove;
    }

    public boolean checkNotation(List<String> moves) {
        for(String move : moves) {
            String[] strs = move.split("\\.");

            if (strs == null || strs.length != 2)
                return false;

            strs[1] = strs[1].substring(1);

            String[] annotations = strs[1].split(" ");
            if (annotations.length != 1 && annotations.length != 2)
                return false;

            String patternString = "[KDVSJ]?x?[a-h][1-8][KDVSJ]?[+#]?";

            Pattern pattern = Pattern.compile(patternString);

            for (String annotation : annotations) {
                Matcher matcher = pattern.matcher(annotation);
                if(!matcher.matches())
                    return false;
                this.moves.add(annotation);
            }

        }

        return true;
    }

    public void playGame() {
        System.out.println("CAV");
        String move = moves.get(0);
        moves.remove(move);

        String annotation = move;
        annotation = annotation.replace("x", "");
        annotation = annotation.replace("+", "");
        annotation = annotation.replace("#", "");

        String type;
        char c = annotation.charAt(0);
        switch(c) {
            case 'K':
                type = "King";
                annotation = annotation.substring(1);
                break;
            case 'D':
                type = "Queen";
                annotation = annotation.substring(1);
                break;
            case 'V':
                type = "Rook";
                annotation = annotation.substring(1);
                break;
            case 'S':
                type = "Bishop";
                annotation = annotation.substring(1);
                break;
            case 'J':
                type = "Knight";
                annotation = annotation.substring(1);
                break;
            default:
                type = "Pawn";
        }

        int col = ((int)annotation.charAt(0) - 'a' + 1);
        int row = ((int)annotation.charAt(1) - '0');

        Field moveTo = board.getField(col, row);

        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                Field field = board.getField(i, j);

                if (field.isEmpty())
                    continue;

                Figure figure = field.get();

                if (figure.isWhite() == (annotationIndex % 2 == 1))
                    continue;

                if (figure.getClass().getSimpleName().equals(type)) {
                    if (figure.canMove(moveTo))
                        move(figure, moveTo);
                }
            }
        }
        annotationIndex++;
    }

    public int getMovesNum() {
        return moves.size();
    }

    @Override
    public boolean move(Figure figure, Field field) {
        boolean capturing = !field.isEmpty();

        // Uneven turns are for White, even for black
        if (figure.isWhite() != (turnNum % 2 == 1))
            return false;

        // store old position
        movedFrom.push(figure.getPosition());
        if (figure.move(field)) {
            // store new position
            movedTo.push(field);
            this.turnNum++;
            updateLastMove(figure, field, capturing);
            return true;
        } else {
            movedFrom.pop();
            return false;
        }
    }
}
