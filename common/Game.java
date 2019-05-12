/**
 * Game: Rozhranie reprezentujuce hru
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package ija.ija2018.homework2.common;

import java.util.*;

public interface Game {

    /**
     * rozhranie metody move() - presunie figurku na zadane policko
     * @param figure figurka ktora ma byt presunuta
     * @param field policko na ktore ma byt figurka presunuta
     * @return bool - pohyb je mozny alebo nie
     */
    public boolean move(Figure figure, Field field);

    /**
     * rozhranie metody getLastMove() - vrati informaciu o poslednom vykonanom pohybe {zo zasobnika}
     * @return string
     */
    public String getLastMove();

    /**
     * rozhranie metody undo() - vrati hru o jeden pohyb spat
     * @return bool
     */
    public boolean undo();

    /**
     * rozhranie metody checkNotation -
     * @param moves //TODO
     * @return bool
     */
    public boolean checkNotation(List<String> moves);

    /**
     * //TODO
     */
    public void playGame();

    /**
     * //TODO
     * @return //TODO
     */
    public int getMovesNum();
}
