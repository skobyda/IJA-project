/**
 * Pawn: Trieda reprezentujuca figurku Pesiaka
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package ija.ija2018.homework2.common;

import java.util.*;

public class Pawn implements Figure {
	private boolean isWhite;
    private Field position;
    private String type;
    private boolean firstMove;

    /**
     * konstruktor pre vytvorenie pesiaka
     * @param isWhite farba pesiaka
     * @param typeStr typ figurky(pesiak)
     */
	public Pawn(boolean isWhite, String typeStr) {
        this.isWhite = isWhite;
        this.type = typeStr;
        this.firstMove = true;
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
     * @return blackPawn/whitePawn
     */
	public String getType() {
        return type;
    }

    /**
     * vrati farbu a poziciu figúrky Pesiaka
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

        return "P[" + color + "]" + pos[0] + ":" + pos[1];
    }

    /**
     * vrati poziciu figurky Pesiaka
     * @return Field- policko kde sa figurka Pesiaka nachadza
     */
    @Override
    public Field getPosition() {
        return position;
    }

    /**
     * zmeni poziciu figurky Pesiaka
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

        Figure figureToCapture = moveTo.get();
        // There is a figure on destination Field, so we want to capture it
        if (figureToCapture != null) {
            // Do not capture piece of same color
            if (moveTo.get().isWhite() == isWhite)
                return false;

            Field field1 = position.nextField(isWhite ? Field.Direction.LD : Field.Direction.LU);
            Field field2 = position.nextField(isWhite ? Field.Direction.RD : Field.Direction.RU);
            if ((field1 != null && field1.equals(moveTo)) || (field2 != null && field2.equals(moveTo)))
                return true;

            return false;
        }

        // There is no figure to capture
        Field field = position;
        int i = 0;
        // Pawn's reach can be 2 if its first move
        while (((firstMove && i < 2) || i < 1) &&
               field != null &&
               !field.equals(moveTo)) {
            i++;
            field = field.nextField(isWhite ? Field.Direction.D : Field.Direction.U);

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
            this.firstMove = false;
            return moveTo.put(this);
        }

        return false;
    }
}
