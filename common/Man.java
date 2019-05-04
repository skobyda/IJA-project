package ija.ija2018.homework2.common;

public class Man implements Figure {
	private boolean isWhite;
    private Field position;

	public Man(boolean isWhite) {
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

        while (field != null && emptiness && !field.equals(moveTo)) {
            field = field.nextField(Field.Direction.LU);
            if (field != null) {
                emptiness = field.isEmpty();
                if (field.equals(moveTo)) {
                    position.remove(this);
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
                    return moveTo.put(this);
                }
            }
        }

        return false;
    }
}
