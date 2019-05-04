package ija.ija2018.homework2.common;

import ija.ija2018.homework2.game.Board;
import java.util.*;

public class Checkers implements Game {
    protected Board board;
    protected int size;
    protected Stack<Field> movedFrom;
    protected Stack<Field> movedTo;

    public Checkers(Board board) {
        this.board = board;
        this.size = board.getSize(); 
        this.movedFrom = new Stack<Field>();
        this.movedTo = new Stack<Field>();

        for (int i = 1; i <= size; i=i+2) {
            Man whiteMan = new Man(true);
            whiteMan.setPosition(board.getField(i, 2));
            board.getField(i, 1).put(whiteMan);

            Man blackMan = new Man(false);
            blackMan.setPosition(board.getField(i, size - 1));
            board.getField(i, size - 1).put(blackMan);
        }
        for (int i = 2; i <= size; i=i+2) {
            Man whiteMan = new Man(true);
            whiteMan.setPosition(board.getField(i, 2));
            board.getField(i, 2).put(whiteMan);

            Man blackMan = new Man(false);
            blackMan.setPosition(board.getField(i, size));
            board.getField(i, size).put(blackMan);
        }
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
            movedTo.push(figure.getPosition());
            return true;
        } else {
            movedFrom.pop();
            return false;
        }
    }
}
