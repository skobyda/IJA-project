/* authors: Simon Kobyda, Michal Zelena (xkobyd00, xzelen24)
 */

package common;

import java.util.*;

public interface Game {
    public boolean move(Figure figure, Field field);
    public String getLastMove();
    public boolean gameOver();
    public boolean redo();
    public boolean undo();
    public boolean checkNotation(List<String> moves);
    public void playGame();
    public int getMovesNum();
}
