/* authors: Simon Kobyda, Michal Zelena (xkobyd00, xzelen24)
 */

package gui;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class FieldGUI extends Rectangle {
    protected int col;
    protected int row;
    protected int size;

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
