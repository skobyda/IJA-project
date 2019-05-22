/**
 * Knight: Trieda reprezentujuca figurku kona
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package common;

import java.util.*;
import java.lang.Math;

public class Knight implements Figure {
	private boolean isWhite;
    private Field position;
    private String type;
    private int numOfMoves;

    /**
     * Konstruktor pre vytvorenie kona.
     * @param isWhite - Farba Kona.
     * @param typeStr - Typ figurky

     */
	public Knight(boolean isWhite, String typeStr) {
        this.isWhite = isWhite;
        this.type = typeStr;
        this.numOfMoves = 0;
    }

    /**
     * Vrati farbu figurky.
     * @return true/false
     */
	public boolean isWhite() {
        return isWhite;
    }

    /**
     * Vrati typ figurky.
     * @return blackKnight/whiteKnight
     */
	public String getType() {
        return type;
    }

    /**
     * Vrati farbu a poziciu figurky kona.
     * @return V[W/B)pos:pos
     */
    @Override
    public String getState() {
        String color;
        if (isWhite)
            color = "W";
        else
            color = "B";
        int[] pos = position.getPosition();

        return "V[" + color + "]" + pos[0] + ":" + pos[1];
    }

    /**
     * Vrati poziciu figurky kona.
     * @return Field - Policko kde sa figurka kona nachadza.
     */
    @Override
    public Field getPosition() {
        return position;
    }

    /**
     * Deinkrementuje pocet pohybov.
     */
    public void decNumOfMoves() {
        this.numOfMoves--;
    }

    /**
     * Zmeni poziciu figurky kona.
     */
    @Override
    public void setPosition(Field field) {
        this.position = field;
    }

    /**
     * Vrati informaciu o tom, ci je mozne sa na zadane policko pohnut, v ceste mozu stat ziadne figurky.
     * @return true/false
     */
    public boolean canMove(Field moveTo) {
        // Same position figure is currently on
        if (position == null || position.equals(moveTo))
            return false;

        // Do not capture piece of same color
        if (moveTo.get() != null ) {
            if (moveTo.get().isWhite() == isWhite)
                return false;
        }

        int[] destPos = moveTo.getPosition();
        int[] currPos = position.getPosition();

        if ((Math.abs(destPos[0] - currPos[0]) == 1 && Math.abs(destPos[1] - currPos[1]) == 2) ||
            (Math.abs(destPos[0] - currPos[0]) == 2 && Math.abs(destPos[1] - currPos[1]) == 1)) {
            return true;
        }

        return false;
    }

    /**
     * Posunie figurku na zadane policko.
     * @return ture/false
     */
    @Override
    public boolean move(Field moveTo) {
        if (canMove(moveTo)) {
            position.remove(this);
            this.position = moveTo;
            this.numOfMoves++;
            return moveTo.put(this);
        }

        return false;
    }
}
