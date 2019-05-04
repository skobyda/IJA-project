package ija.ija2018.homework2.game;

import ija.ija2018.homework2.common.BoardField;
import ija.ija2018.homework2.common.Field;

public class Board {
	private int size;
    private BoardField[][] field;

	public Board(int size) {
        this.size = size;
        this.field = new BoardField[size+2][size+2];
        for (int i = 0; i < size+2; i++) {
            this.field[i] = new BoardField[size+2];
            for (int j = 0; j < size+2; j++) {
                if (i == 0 || j == 0 || i == size+1 || j == size+1)
                    this.field[i][j] = null;
                else
                    this.field[i][j] = new BoardField(i, j);
            }
        }
        for (int i = 1; i < size + 1; i++) {
            for (int j = 1; j < size + 1; j++) {
                field[i][j].addNextField(Field.Direction.D, field[i-1][j]);
                field[i][j].addNextField(Field.Direction.LU, field[i-1][j+1]);
                field[i][j].addNextField(Field.Direction.R, field[i][j+1]);
                field[i][j].addNextField(Field.Direction.RU, field[i+1][j+1]);
                field[i][j].addNextField(Field.Direction.U, field[i+1][j]);
                field[i][j].addNextField(Field.Direction.RD, field[i+1][j-1]);
                field[i][j].addNextField(Field.Direction.L, field[i][j-1]);
                field[i][j].addNextField(Field.Direction.LD, field[i-1][j-1]);
            }
        }
    }

	public Field getField(int col, int row) {
        return field[row][col];
    }

	public int getSize() {
        return size;
    }
}
