package ija.ija2018.homework2.common;

import java.util.*;

public interface Game {
    public boolean move(Figure figure, Field field);
    public String getLastMove();
    public void undo();
    public boolean checkNotation(List<String> moves);
    public void playGame();
    public int getMovesNum();
}
