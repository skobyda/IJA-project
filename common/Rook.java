package ija.ija2018.homework2.common;

import java.util.*;

public class Rook implements Figure {
	private boolean isWhite;
    private Field position;
    private String type;

	public Rook(boolean isWhite, String typeStr) {
        this.isWhite = isWhite;
        this.type = typeStr;
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

        return "V[" + color + "]" + pos[0] + ":" + pos[1];
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
                if (field.equals(moveTo)) {
                    position.remove(this);
                    this.position = moveTo;
                    return moveTo.put(this);
                }

                // Figure is in the way
                if (!field.isEmpty())
                    break;
            }
        }

        return false;
    }
}
