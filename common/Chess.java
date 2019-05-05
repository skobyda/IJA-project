package ija.ija2018.homework2.common;

import ija.ija2018.homework2.game.Board;
import java.util.*;

public class Chess implements Game {
    protected Board board;
    protected int size;
    protected Stack<Field> movedFrom;
    protected Stack<Field> movedTo;
    protected boolean whiteTurn;

    public Chess(Board board) {
        this.board = board;
        this.size = board.getSize();
        this.movedFrom = new Stack<Field>();
        this.movedTo = new Stack<Field>();
        this.whiteTurn = true;

        for(int i = 1; i <= size; i++) {
            Pawn whitePawn = new Pawn(true, "whitePawn");
            whitePawn.setPosition(board.getField(i, 2));
            board.getField(i, 2).put(whitePawn);

            Pawn blackPawn = new Pawn(false, "blackPawn");
            blackPawn.setPosition(board.getField(i, 7));
            board.getField(i, 7).put(blackPawn);
        }

        Rook whiteRook1 = new Rook(true, "whiteRook");
        whiteRook1.setPosition(board.getField(1, 1));
        board.getField(1, 1).put(whiteRook1);
        Rook whiteRook2 = new Rook(true, "whiteRook");
        whiteRook2.setPosition(board.getField(8, 1));
        board.getField(8, 1).put(whiteRook2);

        Rook blackRook1 = new Rook(false, "blackRook");
        blackRook1.setPosition(board.getField(8, 8));
        board.getField(8, 8).put(blackRook1);
        Rook blackRook2 = new Rook(false, "blackRook");
        blackRook2.setPosition(board.getField(1, 8));
        board.getField(1, 8).put(blackRook2);
    }

    @Override
    public void undo() {
        Field field1 = movedTo.pop();
        Field field2 = movedFrom.pop();

        field1.undo();
        field2.undo();
    }

    @Override
    public boolean move(Figure figure, Field field) {
        // move of incorrect color
        if (figure.isWhite() != whiteTurn)
            return false;

        // store old position
        movedFrom.push(figure.getPosition());
        if (figure.move(field)) {
            // store new position
            movedTo.push(field);
            this.whiteTurn = !whiteTurn;
            return true;
        } else {
            movedFrom.pop();
            return false;
        }
    }
}
