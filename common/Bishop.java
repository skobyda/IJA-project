package ija.ija2018.homework2.common;

public class Bishop implements Figure {
	private boolean isWhite;
    private Field position;
    private String type;

	public Bishop(boolean isWhite, String typeStr) {
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
        Field field = position;
        if (position == null || position.equals(moveTo))
            return false;

        // Do not capture piece of same color
        if (moveTo.get() != null && moveTo.get().isWhite() == isWhite)
            return false;

        boolean emptiness = true;

        while (field != null && emptiness && !field.equals(moveTo)) {
            field = field.nextField(Field.Direction.LU);
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
            field = field.nextField(Field.Direction.LD);
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
            field = field.nextField(Field.Direction.RU);
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
            field = field.nextField(Field.Direction.RD);
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
