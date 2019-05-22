/**
 * Game: Rozhranie reprezentujuce hru
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package common;

import java.util.*;

public interface Game {

    /**
     * Rozhranie metody - Presunie figurku na zadane policko.
     * @param figure - Figurka ktora ma byt presunuta.
     * @param field - Policko na ktore ma byt figurka presunuta.
     * @return bool - Pohyb je mozny alebo nie.
     */
    public boolean move(Figure figure, Field field);

    /**
     * Rozhranie metody - Vrati informaciu o poslednom vykonanom pohybe {zo zasobnika}.
     * @return string
     */
    public String getLastMove();
    public boolean gameOver();
    /**
     * Rozhranie metody - Vrati hru o jeden pohyb vpred.
     * @return bool
     */
    public boolean redo();

    /**
     * Rozhranie metody - Vrati hru o jeden pohyb spat.
     * @return bool
     */
    public boolean undo();

    /**
     * Rozhranie metody - Kontrola vstupneho suboru.
     * @param moves - Subor s datami.
     * @return bool
     */
    public boolean checkNotation(List<String> moves);

    /**
     * Rozhranie metody - Jeden pohyb v hre.
     */
    public void playGame();

    /**
     * Rozhranie metody - Vrati pocet pohybov hry.
     * @return int - Pocet pohybov.
     */
    public int getMovesNum();
}
