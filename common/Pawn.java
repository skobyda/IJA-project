package ija.ija2018.homework2.common;

import java.util.*;

public class Pawn implements Figure {
	private boolean isWhite;
    private Field position;

	public Pawn(boolean isWhite) {
        this.isWhite = isWhite;
    }

	public boolean isWhite() {
        return isWhite;
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

        boolean emptiness = true;

        field = position;
        while (isWhite && field != null && emptiness && !field.equals(moveTo)) {
            field = field.nextField(Field.Direction.U);
            if (field != null) {
                emptiness = field.isEmpty();
                if (field.equals(moveTo)) {
                    position.remove(this);
                    this.position = moveTo;
                    moveTo.put(this);
                    return true;
                }
            }
        }

        field = position;
        while (!isWhite && field != null && emptiness && !field.equals(moveTo)) {
            field = field.nextField(Field.Direction.D);
            if (field != null) {
                emptiness = field.isEmpty();
                if (field.equals(moveTo)) {
                    position.remove(this);
                    this.position = moveTo;
                    moveTo.put(this);
                    return true;
                }
            }
        }

        return false;
    }
}
