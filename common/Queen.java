package ija.ija2018.homework2.common;

import java.util.*;

public class Queen implements Figure {
	private boolean isWhite;
    private Field position;
    private String type;
    private boolean firstMove;
    private int numOfMoves;

	public Queen(boolean isWhite, String typeStr) {
        this.isWhite = isWhite;
        this.type = typeStr;
        this.numOfMoves = 0;
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

    public void decNumOfMoves() {
        this.numOfMoves--;
    }

    @Override
    public void setPosition(Field field) {
        this.position = field;
    }

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
