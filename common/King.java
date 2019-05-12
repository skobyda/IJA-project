/**
 * King: Trieda reprezentujuca figurku krala
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */


package ija.ija2018.homework2.common;

import java.util.*;

public class King implements Figure {
	private boolean isWhite;
    private Field position;
    private String type;

    /**
     * konstruktor pre vytvorenie Krala
     * @param isWhite farba Krala
     * @param typeStr typ figurky
     */
	public King(boolean isWhite, String typeStr) {
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
     * @return blackKing/whiteKing
     */
	public String getType() {
        return type;
    }

    /**
     * vrati farbu a poziciu figúrky
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
     * vrati poziciu figurky Krala
     * @return Field- policko kde sa figurka Krala nachadza
     */
    @Override
    public Field getPosition() {
        return position;
    }

    /**
     * zmeni poziciu figurky Krala
     */
    @Override
    public void setPosition(Field field) {
        this.position = field;
    }

    /**
     * vrati informaciu o tom, ci je mozne sa na zadane policko pohnut - v ceste nesmu stat ziadne figurky
     * @return true/false
     */
    public boolean canMove(Field moveTo) {
        // Figure did not move
        if (position == null || position.equals(moveTo))
            return false;

        // Do not capture piece of same color
        if (moveTo.get() != null && moveTo.get().isWhite() == isWhite)
            return false;

        // List of allowed directions for this figure
        List<Field.Direction> directions = new ArrayList<Field.Direction>();
        directions.add(Field.Direction.U);
        directions.add(Field.Direction.D);
        directions.add(Field.Direction.L);
        directions.add(Field.Direction.R);
        directions.add(Field.Direction.LU);
        directions.add(Field.Direction.LD);
        directions.add(Field.Direction.RU);
        directions.add(Field.Direction.RD);

        // Try to find the destination in certain direction
        for (Field.Direction dir : directions) {
            Field field = position.nextField(dir);

            // Successfully found the destination field
            if (field != null && field.equals(moveTo))
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
