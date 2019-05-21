/**
 * Rook: Trieda reprezentujuca figurku veze
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package common;

import java.util.*;

public class Rook implements Figure {
	private boolean isWhite;
    private Field position;
    private String type;
    private int numOfMoves;

    /**
     * konstruktor pre vytvorenie
     * @param isWhite farba Veze
     * @param typeStr typ figurky(veza)
     */
	public Rook(boolean isWhite, String typeStr) {
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
     * @return blackRook/whiteRook
     */
	public String getType() {
        return type;
    }

    /**
     * vrati farbu a poziciu fig�rky Veze
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
     * vrati poziciu figurky Veze
     * @return Field- policko kde sa figurka Veze nachadza
     */
    @Override
    public Field getPosition() {
        return position;
    }

    /**
     * dekrementuje pocet pohybov
     */
    public void decNumOfMoves() {
        this.numOfMoves--;
    }

    /**
     * zmeni poziciu figurky Veze
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

        // Try to find the destination in certain direction
        for (Field.Direction dir : directions) {
            Field field = position;

            // Rook is not distance limited, so "Explore" this direction
            while (field != null && !field.equals(moveTo)) {
                field = field.nextField(dir);

                // Out of board
                if (field == null) {
                    break;
                }

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
