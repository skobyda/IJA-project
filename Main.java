package ija.ija2018.homework2;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
// import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.*;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.*;

import ija.ija2018.homework2.gui.FieldGUI;
import ija.ija2018.homework2.gui.FieldLabelGUI;
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
    private File gameFile = null;

    // Toolbar
    HBox toolbar;
    private Button buttonBack;
    ChoiceBox<String> choiceBox;
    Button buttonStart;
    Button buttonLoadGame;

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

        // Toolbar -> Back Button
        buttonBack = new Button("Back");
        buttonBack.setOnAction(e -> this.handle(e));
        buttonBack.setPrefSize(100, 20);

        // Toolbar -> Automatic game Button
        choiceBox = new ChoiceBox<String>(
            FXCollections.observableArrayList("Manual", "Automatic")
        );
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.setOnAction(e -> {
            String value = (String) choiceBox.getValue();
            if (value.equals("Automatic")) {
                buttonStart.setDisable(false);
                buttonLoadGame.setDisable(false);
            } else {
                buttonStart.setDisable(true);
                buttonLoadGame.setDisable(true);
            }
        });

        // Toolbar -> Start button
        buttonStart = new Button("Start");
        buttonStart.setOnAction(e -> {
            playGame(gameFile);
        });
        buttonStart.setDisable(true);

        // Toolbar -> Load game Button
        buttonLoadGame = new Button("Select File");
        buttonLoadGame.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            gameFile = fileChooser.showOpenDialog(primaryStage);
            buttonStart.setDisable(false);
        });
        buttonLoadGame.setDisable(true);

        // Toolbar
        toolbar = new HBox();
        toolbar.setPadding(new Insets(15, 12, 15, 12));
        toolbar.setSpacing(10);
        toolbar.setStyle("-fx-background-color: #336699;");
        toolbar.getChildren().addAll(choiceBox, buttonLoadGame, buttonStart, buttonBack);

        // Board
        StackPane playGround = new StackPane();
        playGround.getChildren().addAll(fieldGroup, figureGroup);
        playGround.setAlignment(fieldGroup, Pos.TOP_RIGHT);
        playGround.setAlignment(figureGroup, Pos.TOP_RIGHT);

        // Move History
        ListView<String> list = new ListView<String>();
        list.setItems(moveHistory);

        // App layout
        BorderPane tmp = new BorderPane();
        tmp.setLeft(playGround);
        tmp.setTop(toolbar);
        tmp.setRight(list);

        Scene scene = new Scene(tmp, 800, 510);

        window.setScene(scene);

        window.show();
    }

    public void playGame(File file) {
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
                // next automated Move
                game.playGame();
                moveHistory.add(0, game.getLastMove());
                // refresh GUI
                spreadFigures();
            }
        }));
        animation.setCycleCount(game.getMovesNum());
        animation.play();
    }

    public void spreadFigures() {
        figureGroup.getChildren().clear();

        for (int col = 0; col <= 8; col++) {
            for (int row = 0; row <= 8; row++) {
                if (col > 0 && row < 8) {
                    FieldGUI field = new FieldGUI((col + row) % 2 == 0, row, col, 50);
                    fieldGroup.getChildren().add(field);

                    Figure figureBackend = board.getField(col, row + 1).get();

                    if (figureBackend != null) {
                        FigureGUI figure = new FigureGUI(figureBackend, game, board, 50);

                        figure.setOnMouseReleased(e -> {
                            int newX = (int) e.getSceneX() / 50;
                            int newY = (int) e.getSceneY() / 50;

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
                } else {
                    FieldLabelGUI label = new FieldLabelGUI((col + row) % 2 == 0, row, col, 50);

                    fieldGroup.getChildren().add(label);
                }
            }
        }
    }

    public void handle(ActionEvent event) {
        if(event.getSource() == buttonBack) {
            game.undo();
            spreadFigures();
        }
    }
}
