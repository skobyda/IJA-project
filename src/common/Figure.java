/* authors: Simon Kobyda, Michal Zelena (xkobyd00, xzelen24)
 */

package common;

public interface Figure {
    public boolean isWhite();
	public String getType();
	public String getState();
    public Field getPosition();
    public void decNumOfMoves();
    public void setPosition(Field field);
    public boolean canMove(Field moveTo);
    public boolean move(Field moveTo);

}
