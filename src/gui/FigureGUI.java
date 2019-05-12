package project.src.gui;

import javafx.scene.shape.Ellipse;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import project.src.common.Figure;
import project.src.common.Field;
import project.src.common.Game;
import project.src.game.Board;

public class FigureGUI extends ImageView {
    private Figure figureBackend;
    private Game gameBackend;
    private Board boardBackend;
    private Image image;
    private int size;

    // TODO
    private double mouseX, mouseY;
    private double diffX, diffY;

    public FigureGUI(Figure figureArg, Game gameArg, Board boardArg, int sizeArg) {
        this.figureBackend = figureArg;
        this.gameBackend = gameArg;
        this.boardBackend = boardArg;
        this.size = sizeArg;
        this.image = new Image("project.src/lib/" + figureArg.getType() + ".png");

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

    public void refreshLocation() {
        int row = figureBackend.getPosition().getRow();
        int col = figureBackend.getPosition().getCol();

        relocate(row * size , col * size );
    }
}
