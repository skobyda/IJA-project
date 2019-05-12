/**
 * FieldGUI: Trieda reprezentujuca grafiku policka
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package ija.ija2018.homework2.gui;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class FieldGUI extends Rectangle {
    protected int col;
    protected int row;
    protected int size;

    /**
     * konstruktor grafickeho rozhrania policka
     * @param white - farba policka
     * @param row - riadok policka
     * @param column - stlpec policka
     * @param size - velkost dosky
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
