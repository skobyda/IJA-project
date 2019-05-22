/**
 * Figure: Rozhranie reprezentujuce figurku
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package common;

public interface Figure {

    /**
     * Rozhranie metody - Vrati informaciu o tom ci je figurka biela alebo cierna.
     * @return bool
     */
    public boolean isWhite();

    /**
     * Rozhranie metody - Vrati typ figurky, napr: whiteKing.
     * @return string
     */
	public String getType();

    /**
     * Rozhranie metody - Stav policka {obsahuje figurku?}.
     * @return string
     */
	public String getState();

    /**
     * Rozhranie metody - Vrati poziciu policka.
     * @return Field
     */
    public Field getPosition();
    
	/**
     * Rozhranie metody - Dekrementuje pocet pohybov.
     */
    public void decNumOfMoves();
    /**
     * Rozhranie metody - Nastavi policku poziciu.
     * @param field - Policko pre ktore bude pozicia nastavena.
     */
    public void setPosition(Field field);

    /**
     * Rozhranie metody - Zistenie ci sa da na dane policko pohnut.
     * @param moveTo - Policko kam sa figurka bude hybat.
     * @return bool
     */
    public boolean canMove(Field moveTo);

    /**
     * Rozhranie metody - Presunutie figurky na policko.
     * @param moveTo - Policko kam sa figurka bude posuvat.
     * @return bool
     */
    public boolean move(Field moveTo);

}
