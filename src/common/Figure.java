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
     * rozhranie metody isWhite() - vrati informaciu o tom ci je figurka biela alebo cierna
     * @return bool
     */
    public boolean isWhite();

    /**
     * rozhranie metody getType() - vrati typ figurky, napr: whiteKing
     * @return string
     */
	public String getType();

    /**
     * rozhranie metody getState() - stav policka {obsahuje figurku?}
     * @return string
     */
	public String getState();

    /**
     * rozhranie metody getPosition() - vrati poziciu policka
     * @return Field
     */
    public Field getPosition();
    
	/**
     * rozhranie metody decNumOfMoves() - dekrementuje pocet pohybov
     */
    public void decNumOfMoves();
    /**
     * rozhranie metody setPosition() - nastavi policku poziciu
     * @param field - policko pre ktore bude pozicia nastavena
     */
    public void setPosition(Field field);

    /**
     * rozhranie metody canMove() - zistenie ci sa da na dane policko pohnut
     * @param moveTo policko kam sa figurka bude hybat
     * @return bool
     */
    public boolean canMove(Field moveTo);

    /**
     * rozhranie metody move() - presunutie figurky na policko
     * @param moveTo policko kam sa figurka bude posuvat
     * @return bool
     */
    public boolean move(Field moveTo);

}
