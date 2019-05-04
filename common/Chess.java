package ija.ija2018.homework2.common;

import ija.ija2018.homework2.game.Board;
import java.util.*;

public class Chess implements Game {
    protected Board board;
    protected int size;
    protected Stack<Field> movedFrom;
    protected Stack<Field> movedTo;

    public Chess(Board board) {
        this.board = board;
        this.size = board.getSize(); 
        this.movedFrom = new Stack<Field>();
        this.movedTo = new Stack<Field>();

        for(int i = 1; i <= size; i++) {
            Pawn whitePawn = new Pawn(true);
            whitePawn.setPosition(board.getField(i, 2));
            board.getField(i, 2).put(whitePawn);

            Pawn blackPawn = new Pawn(false);
            blackPawn.setPosition(board.getField(i, 7));
            board.getField(i, 7).put(blackPawn);
        }

        Rook whiteRook1 = new Rook(true);
        whiteRook1.setPosition(board.getField(1, 1));
        board.getField(1, 1).put(whiteRook1);
        Rook whiteRook2 = new Rook(true);
        whiteRook2.setPosition(board.getField(8, 1));
        board.getField(8, 1).put(whiteRook2);

        Rook blackRook1 = new Rook(false);
        blackRook1.setPosition(board.getField(8, 8));
        board.getField(8, 8).put(blackRook1);
        Rook blackRook2 = new Rook(false);
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
        // store old position
        movedFrom.push(figure.getPosition());
        if (figure.move(field)) {
            // store new position
            movedTo.push(field);
            return true;
        } else {
            movedFrom.pop();
            return false;
        }
    }
}
