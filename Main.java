package ija.ija2018.homework2;

import javafx.application.Application;
// import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.*;

import ija.ija2018.homework2.gui.FieldGUI;
import ija.ija2018.homework2.gui.FigureGUI;
import ija.ija2018.homework2.common.Field;
import ija.ija2018.homework2.common.Figure;
import ija.ija2018.homework2.common.Game;
import ija.ija2018.homework2.game.Board;

public class Main extends Application {

    Stage window;

    private Group fieldGroup = new Group();
    private Group figureGroup = new Group();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Michal's and Simon's Chess");

        Board board = new Board(8);
        Game game = GameFactory.createChessGame(board);

        spreadFigures(board, game);

        StackPane layout = new StackPane();
        layout.getChildren().addAll(fieldGroup, figureGroup);
        Scene scene = new Scene(layout, 400, 400);

        window.setScene(scene);
        window.show();
    }

    public void spreadFigures(Board board, Game game) {
        figureGroup.getChildren().clear();

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                FieldGUI field = new FieldGUI((col + row) % 2 == 0, row, col, 50);
                fieldGroup.getChildren().add(field);

                Figure figureBackend = board.getField(col + 1, row + 1).get();
                if (figureBackend != null) {
                    FigureGUI figure = new FigureGUI(figureBackend, game, board, 50);

                    figure.setOnMouseReleased(e -> {
                        int newX = (int) e.getSceneX() / 50 + 1;
                        int newY = (int) e.getSceneY() / 50 + 1;

                        if (newX < 1)
                            newX = 1;
                        else if (newX > 8)
                            newX = 8;

                        if (newY < 1)
                            newY = 1;
                        else if (newY > 8)
                            newY = 8;

                        Field newField = board.getField(newX, newY);
                        game.move(figureBackend, newField);

                        spreadFigures(board, game);
                    });

                    figureGroup.getChildren().add(figure);
                }
            }
        }
    }
}
