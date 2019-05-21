/**
 * Board: Trieda reprezentujuca dosku(hracie pole)
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package game;

import common.BoardField;
import common.Field;

public class Board {
	private int size;
    private BoardField[][] field;

    /**
     * konstruktor pre vytvorenie dosky
     * @param size velkost pola(size X size)
     */
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
                field[i][j].addNextField(Field.Direction.U, field[i+1][j]);
                field[i][j].addNextField(Field.Direction.L, field[i][j-1]);
                field[i][j].addNextField(Field.Direction.R, field[i][j+1]);
                field[i][j].addNextField(Field.Direction.LU, field[i+1][j-1]);
                field[i][j].addNextField(Field.Direction.LD, field[i-1][j-1]);
                field[i][j].addNextField(Field.Direction.RU, field[i+1][j+1]);
                field[i][j].addNextField(Field.Direction.RD, field[i-1][j+1]);
            }
        }
    }

    /**
     * vrati policko leziace na stlpci s cislom col a riadku s cislom row
     * @param col cislo stlpca
     * @param row cislo riadku
     * @return Field - policka na pozadovanom riadku a stlpci
     */
	public Field getField(int col, int row) {
        return field[row][col];
    }

    /**
     * vrati velkost dosky
     * @return velkost dosky
     */
	public int getSize() {
        return size;
    }
}
