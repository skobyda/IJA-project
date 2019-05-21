/**
 * BoardField: Trieda reprezentujuca policko dosky
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package ija.ija2018.homework2.common;

import java.util.*; 

public class BoardField implements Field {
	protected int col;
	protected int row;
	protected boolean hasDisk;
	protected Figure disk;
	protected Field Dfield;
	protected Field Lfield;
	protected Field LDfield;
	protected Field LUfield;
	protected Field Rfield;
	protected Field RDfield;
	protected Field RUfield;
	protected Field Ufield;
    protected Stack<Figure> history;

    /**
     * konstruktor policka sachovnice
     * @param col stlpec kde sa policko nachadza
     * @param row riadok kde sa policko nachadza
     */
	public BoardField(int col, int row) {
        this.col = col;
        this.row = row;
        this.history = new Stack<Figure>();
    }

    /**
     * vrati poziciu stlpca policka
     * @return int
     */
	public int getCol() {
        return col;
    }

    /**
     * vrati poziciu riadku policka
     * @return int
     */
	public int getRow() {
        return row;
    }

    /**
     * vrrati poziciu riadku a stlpca ako pole
     * @return int
     */
	public int[] getPosition() {
        int[] position = {row, col};
        return position;
    }

    /**
     * vratenie stavu dosky o jeden spat
     */
	public void undo() {
        Figure figure = history.pop();
        this.disk = figure;
        if (figure != null) {
            this.hasDisk = true;
            figure.setPosition(this);
            figure.decNumOfMoves();
        } else {
            this.hasDisk = false;
        }
    }

    /**
     * polozi na policko dosky figurku
     * @param newdisk figurka ktora bude polozena
     * @return bool
     */
	public boolean put(Figure newdisk) {
        if (hasDisk) {
            if (disk.isWhite() == newdisk.isWhite())
                return false;
            else
                disk.setPosition(null);
        }

        history.push(disk);
        this.disk = newdisk;
        this.hasDisk = true;
        disk.setPosition(this);
        return true;
    }

    /**
     * vrati figurku z policka
     * @return Figure
     */
	public Figure get() {
        if (!hasDisk)
            return null;

        return disk;
    }

    /**
     * zisti ci je policko prazdne alebo plne
     * @return bool
     */
	public boolean isEmpty() {
        return !hasDisk;
    }

    /**
     * odstrani z policka figurku
     * @param disk - figurka ktora bude odstranena
     * @return bool
     */
	public boolean remove(Figure disk) {
        if (this.disk.equals(disk)) {
            this.disk = null;
            this.hasDisk = false;
            history.push(disk);
            return true;
        }
        return false;
    }

    /**
     * vytvori okolie policka
     * @param dirs smer okolia v ktorom okolie je vytvarane
     * @param field policko ktore sa nachadza v danom okoli
     */
    public void addNextField(Field.Direction dirs, Field field) {
        switch(dirs) {
            case D:
                this.Dfield = field;
                break;
            case L:
                this.Lfield = field;
                break;
            case LD:
                this.LDfield = field;
                break;
            case LU:
                this.LUfield = field;
                break;
            case R:
                this.Rfield = field;
                break;
            case RD:
                this.RDfield = field;
                break;
            case RU:
                this.RUfield = field;
                break;
            case U:
                this.Ufield = field;
                break;
        }
    }

    /**
     * vrati policko zo smeru
     * @param dirs smer v ktorom je dane policko
     * @return Field
     */
    public Field nextField(Field.Direction dirs) {
        switch(dirs) {
            case D:
                return Dfield;
            case L:
                return Lfield;
            case LD:
                return LDfield;
            case LU:
                return LUfield;
            case R:
                return Rfield;
            case RD:
                return RDfield;
            case RU:
                return RUfield;
            case U:
                return Ufield;
        }

        return null;
    }
}
