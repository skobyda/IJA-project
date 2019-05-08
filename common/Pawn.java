package ija.ija2018.homework2.common;

import java.util.*;

public class Pawn implements Figure {
	private boolean isWhite;
    private Field position;
    private String type;
    private boolean firstMove;

	public Pawn(boolean isWhite, String typeStr) {
        this.isWhite = isWhite;
        this.type = typeStr;
        this.firstMove = true;
    }

	public boolean isWhite() {
        return isWhite;
    }

	public String getType() {
        return type;
    }

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

    @Override
    public Field getPosition() {
        return position;
    }

    @Override
    public void setPosition(Field field) {
        this.position = field;
    }

    @Override
    public boolean move(Field moveTo) {
        // Figure did not move
        if (position == null || position.equals(moveTo))
            return false;

        // Do not capture piece of same color
        if (moveTo.get() != null && moveTo.get().isWhite() == isWhite)
            return false;

        Field field = position;
        int i = 0;
        // Pawn's reach can be to for its first move
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
            if (field.equals(moveTo)) {
                position.remove(this);
                this.position = moveTo;
                this.firstMove = false;
                return moveTo.put(this);
            }

            // Figure is in the way
            if (!field.isEmpty())
                break;
        }

        return false;
    }
}
