/**
 * Knight: Trieda reprezentujuca figurku kona
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package ija.ija2018.homework2.common;

import java.util.*;
import java.lang.Math;

public class Knight implements Figure {
	private boolean isWhite;
    private Field position;
    private String type;

    /**
     * [konstruktor pre vytvorenie kona]
     * @param isWhite [farba Kona]
     * @param typeStr [typ figurky]
     */
	public Knight(boolean isWhite, String typeStr) {
        this.isWhite = isWhite;
        this.type = typeStr;
    }

    /**
     * vrati farbu figurky
     * @return true/false
     */
	public boolean isWhite() {
        return isWhite;
    }

    /**
     * vrati typ figurky
     * @return blackKnight/whiteKnight
     */
	public String getType() {
        return type;
    }

    /**
     * vrati farbu a poziciu figúrky Kona
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
     * vrati poziciu figurky Kona
     * @return Field- policko kde sa figurka Kon nachadza
     */
    @Override
    public Field getPosition() {
        return position;
    }

    /**
     * zmeni poziciu figurky Kona
     */
    @Override
    public void setPosition(Field field) {
        this.position = field;
    }

    /**
     * vrati informaciu o tom, ci je mozne sa na zadane policko pohnut - v ceste mozu stat ziadne figurky
     * @return true/false
     */
    public boolean canMove(Field moveTo) {
        // Same position figure is currently on
        if (position == null || position.equals(moveTo))
            return false;

        // Do not capture piece of same color
        if (moveTo.get() != null && moveTo.get().isWhite() == isWhite)
            return false;

        int[] destPos = moveTo.getPosition();
        int[] currPos = position.getPosition();

        if ((Math.abs(destPos[0] - currPos[0]) == 1 && Math.abs(destPos[1] - currPos[1]) == 2) ||
            (Math.abs(destPos[0] - currPos[0]) == 2 && Math.abs(destPos[1] - currPos[1]) == 1)) {
            return true;
        }

        return false;
    }

    /**
     * posunie figurku na zadane policko
     * @return ture/false
     */
    @Override
    public boolean move(Field moveTo) {
        if (canMove(moveTo)) {
            position.remove(this);
            this.position = moveTo;
            return moveTo.put(this);
        }

        return false;
    }
}
