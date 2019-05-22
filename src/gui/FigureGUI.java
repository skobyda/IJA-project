/**
 * FigureGUI: frontend trieda figurky
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

package gui;

import javafx.scene.shape.Ellipse;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import common.Figure;
import common.Field;
import common.Game;
import game.Board;

public class FigureGUI extends ImageView {
    private Figure figureBackend;
    private Game gameBackend;
    private Board boardBackend;
    private Image image;
    private int size;
    private double mouseX, mouseY;
    private double diffX, diffY;

    /**
     * Konstruktor grafickeho rozhrania pre figurku.
     * @param figureArg - Samotna figurka.
     * @param gameArg - Hra v ktorej sa figurka nachadza.
     * @param boardArg - Doska na ktorej sa figurka nachadza.
     * @param sizeArg - Velkost figurky v pixeloch.
     */
    public FigureGUI(Figure figureArg, Game gameArg, Board boardArg, int sizeArg) {
        this.figureBackend = figureArg;
        this.gameBackend = gameArg;
        this.boardBackend = boardArg;
        this.size = sizeArg;
        this.image = new Image(figureArg.getType() + ".png");

        setImage(image);
        setFitWidth(size);
        setFitHeight(size);

        int col = figureArg.getPosition().getCol();
        int row = figureArg.getPosition().getRow();

        relocate(row * size, col * size );

        setOnMousePressed(e -> {
            toFront();

            mouseX = e.getSceneX();
            mouseY = e.getSceneY();

            int oldX = figureBackend.getPosition().getRow() * size;
            int oldY = figureBackend.getPosition().getCol() * size;

            diffX = e.getSceneX() - oldX;
            diffY = e.getSceneY() - oldY;
        });

        setOnMouseDragged(e -> {
            double newX = e.getSceneX() - diffX;
            double newY = e.getSceneY() - diffY;

            if (newX < 50)
                newX = 50;
            else if (newX > 400)
                newX = 400;

            if (newY < 50)
                newY = 50;
            else if (newY > 400)
                newY = 400;

            relocate(newX , newY );
        });

        /* setOnMouseReleased(e -> {
            int newX = (int) e.getSceneX() / size + 1;
            int newY = (int) e.getSceneY() / size + 1;

            Field field = boardBackend.getField(newX, newY);
            gameBackend.move(figureBackend, field);

            refreshLocation();
        });
        */
    }

    /**
     * Obnovenie pozicie figurky.
     */
    public void refreshLocation() {
        int row = figureBackend.getPosition().getRow();
        int col = figureBackend.getPosition().getCol();

        relocate(row * size , col * size );
    }
}
