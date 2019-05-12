package ija.ija2018.homework2.common;

public interface Figure {
	public String getType();
	public String getState();
    public Field getPosition();
    public void setPosition(Field field);
    public boolean canMove(Field moveTo);
    public boolean move(Field moveTo);

}
