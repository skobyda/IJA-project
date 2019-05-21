/**
 * Field: Rozhranie reprezentujuce policko sachovnice
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package common;

/**
 * rozhranie policka dosky
 */
public interface Field {

    /**
     * enumeracia okolitych policok voci jednemu policku dosky
     */
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

    /**
     * rozhranie metody undo() - vratenie tahu o jeden spat
     */
	public void undo();

    /**
     * rozhranie metody getCol(), - ziskanie pozicie stlpca
     * @return int - cislo stlpca
     */
    public int getCol();

    /**
     * rozhranie metody getRow(), - ziskanie pozicie riadku
     * @return int - cislo riadku
     */
    public int getRow();

    /**
     * rozhranie metody getPosition() - ziskanie pozicie policka
     * @return int[] - pole s dvomi prvkami
     */
	public int[] getPosition();

    /**
     * rozhranie metody put() - polozenie figurky na policko
     * @param figure figurka ktora bude polozena
     * @return bool
     */
	public boolean put(Figure figure);

    /**
     * rozhranie metody get() - ziskanie figurky z policka
     * @return Figure - vrati objekt figurky
     */
	public Figure get();

    /**
     * rozhranie metody isEmpty() - zistenie ci je policko prazdne
     * @return bool
     */
	public boolean isEmpty();

    /**
     * rozhranie metody remove() - odstranenie figurky z policka
     * @param figure - figurka ktora bude odstranena
     * @return bool
     */
	public boolean remove(Figure figure);

    /**
     * rozhranie metody addNextField() - pridanie policka ktore je vedla daneho policka
     * @param dirs smer pre ktory sa to bude pridavat
     * @param field policko ktore sa bude pridavat
     */
    public void addNextField(Field.Direction dirs, Field field);

    /**
     * rozhranie metody nextField() - ziskanie policka ktore je v zadanom smere
     * @param dirs smer ktory ma byt prehladany
     * @return Field
     */
    public Field nextField(Field.Direction dirs);
}
