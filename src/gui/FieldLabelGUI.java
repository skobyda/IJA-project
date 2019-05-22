/**
 * BoardField: Trieda reprezentujuca policko dosky
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package gui;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FieldLabelGUI extends StackPane {
    protected int col;
    protected int row;
    protected int size;


    /**
     * Graficke rozhranie pre popisovace riadkov a stlpcov
     * @param white
     * @param row - riadok ktory je popisovany.
     * @param column - stlpec ktory je popisovany.
     * @param size - Velkost popisovaca v pixeloch.
     */
    public FieldLabelGUI(boolean white, int row, int column, int size) {
        this.col = column;
        this.row = row;
        this.size = size;

        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(50);
        rectangle.setHeight(50);
        rectangle.setFill(Color.TRANSPARENT);

        // calculate label value (1-8 or a-b)
        String labelText = "";
        if (column == 0 && row < 8)
            labelText = String.valueOf((char)('8' - row));
        else if (row == 8 && column != 0)
            labelText = String.valueOf((char)('A' + column - 1));

        Text text = new Text (labelText);

        getChildren().addAll(rectangle, text);

        relocate(col * size, row * size);
    }

}
