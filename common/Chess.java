package ija.ija2018.homework2.common;

import ija.ija2018.homework2.game.Board;
import java.util.*;

public class Chess implements Game {
    protected Board board;
    protected int size;
    protected Stack<Field> movedFrom;
    protected Stack<Field> movedTo;
    protected int turnNum;

    public Chess(Board board) {
        this.board = board;
        this.size = board.getSize();
        this.movedFrom = new Stack<Field>();
        this.movedTo = new Stack<Field>();
        this.turnNum = 1;

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

        Bishop whiteBishop1 = new Bishop(true, "whiteBishop");
        whiteBishop1.setPosition(board.getField(2, 8));
        board.getField(2, 8).put(whiteBishop1);
        Bishop whiteBishop2 = new Bishop(true, "whiteBishop");
        whiteBishop2.setPosition(board.getField(7, 8));
        board.getField(7, 8).put(whiteBishop2);

        Bishop blackBishop1 = new Bishop(false, "blackBishop");
        blackBishop1.setPosition(board.getField(7, 1));
        board.getField(7, 1).put(blackBishop1);
        Bishop blackBishop2 = new Bishop(false, "blackBishop");
        blackBishop2.setPosition(board.getField(2, 1));
        board.getField(2, 1).put(blackBishop2);

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

    @Override
    public boolean move(Figure figure, Field field) {
        // Uneven turns are for White, even for black
        if (figure.isWhite() != (turnNum % 2 == 1))
            return false;

        // store old position
        movedFrom.push(figure.getPosition());
        if (figure.move(field)) {
            // store new position
            movedTo.push(field);
            this.turnNum++;
            return true;
        } else {
            movedFrom.pop();
            return false;
        }
    }
}
