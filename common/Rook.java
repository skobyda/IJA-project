package ija.ija2018.homework2.common;

public class Rook implements Figure {
	private boolean isWhite;
    private Field position;

	public Rook(boolean isWhite) {
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
        Field field = position;
        if (position == null || position.equals(moveTo))
            return false;

        boolean emptiness = true;

        while (field != null && emptiness && !field.equals(moveTo)) {
            field = field.nextField(Field.Direction.L);
            if (field != null) {
                emptiness = field.isEmpty();
                if (field.equals(moveTo)) {
                    position.remove(this);
                    this.position = moveTo;
                    return moveTo.put(this);
                }
            }
        }

        emptiness = true;
        field = position;
        while (field != null && emptiness && !field.equals(moveTo)) {
            field = field.nextField(Field.Direction.R);
            if (field != null) {
                emptiness = field.isEmpty();
                if (field.equals(moveTo)) {
                    position.remove(this);
                    this.position = moveTo;
                    return moveTo.put(this);
                }
            }
        }

        emptiness = true;
        field = position;
        while (field != null && emptiness && !field.equals(moveTo)) {
            field = field.nextField(Field.Direction.U);
            if (field != null) {
                emptiness = field.isEmpty();
                if (field.equals(moveTo)) {
                    position.remove(this);
                    this.position = moveTo;
                    return moveTo.put(this);
                }
            }
        }

        emptiness = true;
        field = position;
        while (field != null && emptiness && !field.equals(moveTo)) {
            field = field.nextField(Field.Direction.D);
            if (field != null) {
                emptiness = field.isEmpty();
                if (field.equals(moveTo)) {
                    position.remove(this);
                    this.position = moveTo;
                    return moveTo.put(this);
                }
            }
        }

        return false;
    }
}
