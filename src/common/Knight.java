package project.src.common;

import java.util.*;
import java.lang.Math;

public class Knight implements Figure {
	private boolean isWhite;
    private Field position;
    private String type;
    private int numOfMoves;

	public Knight(boolean isWhite, String typeStr) {
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
