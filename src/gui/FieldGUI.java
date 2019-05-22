/**
 * BoardField: Trieda reprezentujuca policko dosky
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package gui;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class FieldGUI extends Rectangle {
    protected int col;
    protected int row;
    protected int size;

    /**
     * Graficke rozhranie jedneho policka hry.
     * @param white - Farba policka.
     * @param row - Riadok na ktorom sa policko nachadza.
     * @param column - Stlpec na ktorom sa policko nachadza.
     * @param size - Velkost policka v pixeloch.
     */
    public FieldGUI(boolean white, int row, int column, int size) {
        this.col = column;
        this.row = row;
        this.size = size;

        setWidth(size);
        setHeight(size);

        relocate(col * size, row * size);

        setFill(white ? Color.valueOf("#fff") : Color.valueOf("#666"));
    }

}
