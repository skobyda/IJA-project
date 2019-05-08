package ija.ija2018.homework2;

import javafx.application.Application;
// import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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
    private Button button;

    private Board board = new Board(8);
    private Game game = GameFactory.createChessGame(board);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Michal's and Simon's Chess");

        spreadFigures();

        button = new Button();
        button.setText("Undo");
        button.setOnAction(e -> this.handle(e));

        StackPane layout = new StackPane();
        layout.getChildren().addAll(fieldGroup, figureGroup);

        AnchorPane tmp = new AnchorPane();
        tmp.setTopAnchor(layout, 0.0);
        tmp.setBottomAnchor(button, 0.0);
        tmp.getChildren().addAll(layout, button);

        Scene scene = new Scene(tmp, 400, 450);

        window.setScene(scene);
        window.show();
    }

    public void spreadFigures() {
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

                        spreadFigures();
                    });

                    figureGroup.getChildren().add(figure);
                }
            }
        }
    }

    public void handle(ActionEvent event) {
        if(event.getSource() == button) {
            game.undo();
            spreadFigures();
        }
    }
}
