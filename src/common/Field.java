package project.src.common;

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
    public int getCol();
    public int getRow();
	public int[] getPosition();
	public boolean put(Figure figure);
	public Figure get();
	public boolean isEmpty();
	public boolean remove(Figure figure);
    public void addNextField(Field.Direction dirs, Field field);
    public Field nextField(Field.Direction dirs);
}
