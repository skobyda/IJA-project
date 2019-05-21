/**
 * Chess: Trieda reprezentujuca hry sach
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package common;

import game.Board;
import javafx.util.Pair;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Chess implements Game {
    protected Board board;
    protected int size;
    protected Stack<Field> movedFrom;
    protected Stack<Field> movedTo;
    protected Stack<Field> redoMoveTo;
    protected Stack<Figure> redoMoveWho;
    protected int turnNum;
    protected String lastMove;
    protected LinkedList<Figure> gamePlayFigures;
    protected LinkedList<Field> gamePlayFields;
    protected ArrayList<String> moves;
    protected int annotationIndex = 0;
    protected boolean shortAnnotation = true;

    /**
     * konstruktor hry sach - vytvori hru
     * @param board doska na ktorej sa bude hrat
     */
    public Chess(Board board) {
        this.board = board;
        this.size = board.getSize();
        this.movedFrom = new Stack<Field>();
        this.movedTo = new Stack<Field>();
        this.redoMoveTo = new Stack<Field>();
        this.redoMoveWho = new Stack<Figure>();
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

    /**
     * vrati dosku o jeden stav spat
     * @return bool
     */
    public boolean redo() {
        if (redoMoveTo.empty() || redoMoveWho.empty())
            return false;

        Field field = redoMoveTo.pop();
        Figure figure = redoMoveWho.pop();

        if (!figure.canMove(field) || !move(figure, field)) {
            redoMoveTo.clear();
            redoMoveWho.clear();
            return false;
        }

        return true;
    }

    @Override
    public boolean undo() {
        if (turnNum == 1)
            return false;

        this.turnNum--;
        Field field1 = movedTo.pop();
        Field field2 = movedFrom.pop();

        redoMoveTo.push(field1);
        redoMoveWho.push(field1.get());

        field1.undo();
        field2.undo();

        return true;
    }

    public boolean gameOver() {
        if (isMat())
            return true;

        int kingCount = 0;
        // Find position of King
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                Field field = board.getField(i, j);

                if (field.isEmpty())
                    continue;

                Figure figure = field.get();

                if (figure.getClass().getSimpleName().equals("King")) {
                    kingCount++;
                }
            }
        }

        if (kingCount != 2)
            return true;

        return false;
    }

    // Used for calculation of Check or Mat
    /**
     * pouziva sa na zistenie informacie ci nastal Sach alebo Mat
     * @return Figure
     */
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

    /**
     * zistenie ci je pozicia ohrozena
     * @param threatenedPosition policko pre ktore sa to zistuje
     * @return bool
     */
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

    /**
     * zistenie ci ide o mat
     * @return bool
     */
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

    /**
     * zistenie ci ide o sach
     * @return bool
     */
    private boolean isCheck() {
        // Find position of King
        Figure king = getKing();

        // TODO delete later
        if (king == null)
            return false;

        return isPositionThreatened(king.getPosition());
    }

    /**
     * /TODO
     * @param figure /TODO
     * @param field /TODO
     * @param capturing /TODO
     */
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
        int row = 8 + 1 - position[1];

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

    /**
     * /TODO
     * @param moves /TODO
     * @return bool
     */
    public boolean checkNotation(List<String> moves) {
        for(String move : moves) {
            String[] strs = move.split("\\.");

            if (strs == null || strs.length != 2)
                return false;

            strs[1] = strs[1].substring(1);

            String[] annotations = strs[1].split(" ");
            if (annotations.length != 1 && annotations.length != 2)
                return false;

            String patternString1 = "[KDVSJ]?x?[a-h][1-8][KDVSJ]?[+#]?";
            String patternString2 = "[KDVSJ]?x?[a-h][1-8][a-h][1-8][KDVSJ]?[+#]?";

            Pattern pattern1 = Pattern.compile(patternString1);
            Pattern pattern2 = Pattern.compile(patternString2);

            for (String annotation : annotations) {
                Matcher matcher1 = pattern1.matcher(annotation);
                Matcher matcher2 = pattern2.matcher(annotation);
                if (!matcher1.matches() && !matcher2.matches())
                    return false;
                this.shortAnnotation = matcher1.matches();
                this.moves.add(annotation);
            }

        }

        return true;
    }


    /**
     * hranie hry - parsovanie vstupu na pohyby
     */
    public void playGame() {
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

        int col;
        int row;
        if (shortAnnotation) {
            int colDist = 0;
            int rowDist = 0;
            // If we have to distinguish figures
            if ((annotation.charAt(0) >= '1' && annotation.charAt(0) <= '8') ||
                ((annotation.charAt(0) >= 'a' && annotation.charAt(0) <= 'h') &&
                (annotation.charAt(1) >= 'a' && annotation.charAt(0) <= 'h'))) {
                if (annotation.charAt(0) >= '1' && annotation.charAt(0) <= '8')
                    rowDist = 8 + 1 - ((int)annotation.charAt(0) - '0');
                else
                    colDist = ((int)annotation.charAt(0) - 'a' + 1);

                col = ((int)annotation.charAt(1) - 'a' + 1);
                row = 8 + 1 - ((int)annotation.charAt(2) - '0');
            } else {
                col = ((int)annotation.charAt(0) - 'a' + 1);
                row = 8 + 1 - ((int)annotation.charAt(1) - '0');
            }
            Field moveTo = board.getField(col, row);

            // Find which figure can move to destinatio
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
        } else {
            col = ((int)annotation.charAt(0) - 'a' + 1);
            row = ((int)annotation.charAt(1) - '0');
            Field moveTo = board.getField(col, row);

            col = ((int)annotation.charAt(2) - 'a' + 1);
            row = ((int)annotation.charAt(3) - '0');
            Field moveFrom = board.getField(col, row);

            // TODO moveFrom.isEmpty

            move(moveFrom.get(), moveTo);
        }
    }

    /**
     * vrati pocet vykonanych pohybov
     * @return int
     */
    public int getMovesNum() {
        return moves.size();
    }

    /**
     * jeden pohyb na sachovnici
     * @param figure figurka ktorou sa bude hybat
     * @param field policko na ktore sa bude hybat
     * @return bool
     */
    @Override
    public boolean move(Figure figure, Field field) {
        if (gameOver())
            return false;

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
