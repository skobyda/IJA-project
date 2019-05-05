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
        Field field = position;
        if (position == null || position.equals(moveTo))
            return false;

        // Do not capture piece of same color
        if (moveTo.get() != null && moveTo.get().isWhite() == isWhite)
            return false;

        boolean emptiness = true;
        int i;

        field = position;
        i = 0;
        while (((firstMove && i < 2) || i < 1) &&
               field != null &&
               emptiness &&
               !field.equals(moveTo)) {
            i++;

            if (isWhite())
                field = field.nextField(Field.Direction.U);
            else
                field = field.nextField(Field.Direction.D);

            if (field != null) {
                emptiness = field.isEmpty();
                if (field.equals(moveTo)) {
                    position.remove(this);
                    this.position = moveTo;
                    moveTo.put(this);
                    this.firstMove = false;
                    return true;
                }
            }
        }

        return false;
    }
}
