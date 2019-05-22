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
 * Rozhranie policka dosky.
 */
public interface Field {

    /**
     * Enumeracia okolitych policok voci jednemu policku dosky.
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
     * Rozhranie metody - Vratenie tahu o jeden spat.
     */
	public void undo();

    /**
     * Rozhranie metody - Ziskanie pozicie stlpca.
     * @return int - Cislo stlpca.
     */
    public int getCol();

    /**
     * Rozhranie metody - Ziskanie pozicie riadku.
     * @return int - Cislo riadku.
     */
    public int getRow();

    /**
     * Rozhranie metody - Ziskanie pozicie policka.
     * @return int[] - Pole s dvomi prvkami.
     */
	public int[] getPosition();

    /**
     * Rozhranie metody - polozenie figurky na policko.
     * @param figure - Figurka ktora bude polozena.
     * @return bool
     */
	public boolean put(Figure figure);

    /**
     * Rozhranie metody - Ziskanie figurky z policka.
     * @return Figure - Vrati objekt figurky.
     */
	public Figure get();

    /**
     * Rozhranie metody - Zistenie ci je policko prazdne.
     * @return bool
     */
	public boolean isEmpty();

    /**
     * Rozhranie metody - Odstranenie figurky z policka.
     * @param figure - Figurka ktora bude odstranena.
     * @return bool
     */
	public boolean remove(Figure figure);

    /**
     * Rozhranie metody - Pridanie policka ktore je vedla daneho policka.
     * @param dirs - Smer pre ktory sa to bude pridavat.
     * @param field - Policko ktore sa bude pridavat.
     */
    public void addNextField(Field.Direction dirs, Field field);

    /**
     * Rozhranie metody - Ziskanie policka ktore je v zadanom smere.
     * @param dirs - Smer ktory ma byt prehladany.
     * @return Field
     */
    public Field nextField(Field.Direction dirs);
}
