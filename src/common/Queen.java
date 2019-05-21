/**
 * Queen: Trieda reprezentujuca figurku kralovnej
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package common;

import java.util.*;

public class Queen implements Figure {
	private boolean isWhite;
    private Field position;
    private String type;
    private boolean firstMove;
    private int numOfMoves;

    /**
     * [konstruktor pre vytvorenie kralovnej]
     * @param isWhite [farba figurky]
     * @param typeStr [typ figurky]
     */
	public Queen(boolean isWhite, String typeStr) {
        this.isWhite = isWhite;
        this.type = typeStr;
        this.numOfMoves = 0;
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
     * @return blackQueen/whiteQueen
     */
	public String getType() {
        return type;
    }

    /**
     * vrati farbu a poziciu fig�rky Kralovnej
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
     * vrati poziciu figurky Kralovnej
     * @return Field- policko kde sa figurka Kralovnej nachadza
     */
    @Override
    public Field getPosition() {
        return position;
    }

    /**
     * dekremenntuje pocet pohybov
     */
    public void decNumOfMoves() {
        this.numOfMoves--;
    }

    /**
     * zmeni poziciu figurky kralovnej
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
        directions.add(Field.Direction.RU);
        directions.add(Field.Direction.RD);
        directions.add(Field.Direction.LU);
        directions.add(Field.Direction.LD);

        // Try to find the destination in certain direction
        for (Field.Direction dir : directions) {
            Field field = position;

            // Queen is not distance limited, so "Explore" this direction
            while (field != null && !field.equals(moveTo)) {
                field = field.nextField(dir);

                // Out of board
                if (field == null)
                    break;

                // Successfully found the destination field
                if (field.equals(moveTo))
                    return true;

                // Figure is in the way
                if (!field.isEmpty())
                    break;
            }
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
            this.numOfMoves++;
            return moveTo.put(this);
        }

        return false;
    }
}
