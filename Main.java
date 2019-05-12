package ija.ija2018.homework2;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
// import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
    private ObservableList<String> moveHistory = FXCollections.observableArrayList();
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

        ListView<String> list = new ListView<String>();
        list.setItems(moveHistory);

        BorderPane tmp = new BorderPane();
        tmp.setLeft(layout);
        tmp.setBottom(button);
        tmp.setRight(list);
        // tmp.getChildren().addAll(layout, button);

        Scene scene = new Scene(tmp, 600, 450);

        window.setScene(scene);

        window.show();

        playGame("ija/ija2018/homework2/testInput");
    }

    public void playGame(String file) {
        List<String> moves = new ArrayList<String>();
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
                moves.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        game.checkNotation(moves);

        Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.playGame();
                moveHistory.add(0, game.getLastMove());
                spreadFigures();
            }
        }));
        animation.setCycleCount(game.getMovesNum());
        animation.play();
    }

    public void spreadFigures() {
        figureGroup.getChildren().clear();

        for (int col = 1; col <= 8; col++) {
            for (int row = 1; row <= 8; row++) {
                FieldGUI field = new FieldGUI((col + row) % 2 == 0, row, col, 50);
                fieldGroup.getChildren().add(field);

                Figure figureBackend = board.getField(col, row).get();
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
                        if (game.move(figureBackend, newField) == true)
                            moveHistory.add(0, game.getLastMove());

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
