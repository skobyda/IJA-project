package ija.ija2018.homework2.common;

public interface Field {
    public static enum Direction {
        D,
        L,
        LD,
        LU,
        R,
        RD,
        RU,
        U
    }

	public void undo();
	public int[] getPosition();
	public boolean put(Figure figure);
	public Figure get();
	public boolean isEmpty();
	public boolean remove(Figure figure);
    public void addNextField(Field.Direction dirs, Field field);
    public Field nextField(Field.Direction dirs);
}
