/**
 * Chess: Main - Spusta samotnu aplikaciu a tvori zaklad uzivatelskeho rozhrania.
 * @author Simon Kobyda, xkobyd00
 * @author Michal Zelenak, xzelen24
 * Project: Chess
 * University: Brno University of Technology
 * Course: IJA
 */

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
// import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import gui.*;
import common.*;
import game.*;

public class Main extends Application {

    Stage window;

    private Group fieldGroup = new Group();
    private Group figureGroup = new Group();
    private ObservableList<String> moveHistory = FXCollections.observableArrayList("START");

    // Toolbar
    private HBox toolbar;
    private Button buttonBack;
    private Button buttonForward;
    private Button buttonReset;
    private ChoiceBox<String> choiceBox;
    private Button buttonStart;
    private Button buttonLoadGame;
    private Button buttonSaveGame;
    private TextField delayInput;

    private ListView<String> list;

    private Board board = new Board(8);
    private Game game = GameFactory.createChessGame(board);

    private int moveCount = 0;
    private int currentMove = 0;

    /**
     * Metoda hlavnej casti programu.
     * @param args Argumenty programu(zadane pri spusteni).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Zaciatok hry.
     * @param primaryStage - Stav na zaciatku hry.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Michal's and Simon's Chess");

        spreadFigures();

        // Toolbar -> Reset Button
        buttonReset = new Button("Reset");
        buttonReset.setOnAction(e -> {
            this.board = new Board(8);
            this.game = GameFactory.createChessGame(board);
            this.moveHistory.clear();
            moveHistory.add(0, "START");
            this.currentMove = 0;
            this.moveCount = 0;
            spreadFigures();
        });
        buttonReset.setMaxSize(100, 20);

        // Toolbar -> Delay input
        delayInput = new TextField();
        delayInput.setText("Delay (ms)");
        delayInput.setMaxSize(100, 20);

        // Toolbar -> Forward Button
        buttonForward = new Button("Forward");
        buttonForward.setOnAction(e -> {
            if (game.redo()) {
                // moveHistory.remove(0);
                spreadFigures();
                this.currentMove++;
                list.getSelectionModel().select(moveCount - currentMove);
            } else {
                if (game.playGame()) {
                    moveHistory.add(0, game.getLastMove());
                    this.currentMove++;
                }
                // refresh GUI
                spreadFigures();
            }
        });
        buttonForward.setMaxSize(100, 20);

        // Toolbar -> Back Button
        buttonBack = new Button("Back");
        buttonBack.setOnAction(e -> {
            if (game.undo()) {
                spreadFigures();
                this.currentMove--;
                list.getSelectionModel().select(moveCount - currentMove);
            }
        });
        buttonBack.setMaxSize(100, 20);

        // Toolbar -> Play/Replay game Button
        choiceBox = new ChoiceBox<String>(
            FXCollections.observableArrayList("Play", "Replay")
        );
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.setOnAction(e -> {
            String value = (String) choiceBox.getValue();
            if (value.equals("Replay")) {
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
            playGame();
        });
        buttonStart.setDisable(true);

        // Toolbar -> Load game Button
        buttonLoadGame = new Button("Select File");
        buttonLoadGame.setOnAction(e -> {
            this.board = new Board(8);
            this.game = GameFactory.createChessGame(board);
            this.moveHistory.clear();
            moveHistory.add(0, "START");
            this.currentMove = 0;
            this.moveCount = 0;

            spreadFigures();
            FileChooser fileChooser = new FileChooser();
            File gameFile = fileChooser.showOpenDialog(primaryStage);
            parseFile(gameFile);
            buttonStart.setDisable(false);
        });
        buttonLoadGame.setDisable(true);

        // Toolbar -> Save game Button
        buttonSaveGame = new Button("Save Game");
        buttonSaveGame.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                String fileContent = "";
                // We want to skip last value "START"
                for (int i = 0; i < moveHistory.size() - 1; i++) {
                    String move = moveHistory.get(moveHistory.size() - i - 2);
                    if ((i % 2) == 0)
                        fileContent += ((i / 2) + 1) + ". " + move + " ";
                    else
                        fileContent += move + "\n";
                }
                saveTextToFile(fileContent, file);
            }
        });

        // Toolbar
        toolbar = new HBox();
        toolbar.setPadding(new Insets(15, 12, 15, 12));
        toolbar.setSpacing(10);
        toolbar.setStyle("-fx-background-color: #E6E6FA;");
        toolbar.getChildren().addAll(choiceBox, buttonLoadGame, delayInput, buttonStart, buttonBack, buttonForward, buttonReset, buttonSaveGame);

        // Board
        StackPane playGround = new StackPane();
        playGround.getChildren().addAll(fieldGroup, figureGroup);
        playGround.setAlignment(fieldGroup, Pos.TOP_RIGHT);
        playGround.setAlignment(figureGroup, Pos.TOP_RIGHT);

        // Move History
        list = new ListView<String>();
        list.setItems(moveHistory);
        list.setOnMouseClicked(e -> {
                String item = list.getSelectionModel().getSelectedItem();
                int desiredMove = moveCount - moveHistory.indexOf(item);

                if (currentMove > desiredMove) {
                    while (currentMove != desiredMove && game.undo())
                        this.currentMove--;
                } else if (currentMove < desiredMove) {
                    while (currentMove != desiredMove && game.redo())
                        this.currentMove++;
                }
                spreadFigures();
        });

        // App layout
        BorderPane tmp = new BorderPane();
        tmp.setLeft(playGround);
        tmp.setTop(toolbar);
        tmp.setRight(list);

        Scene scene = new Scene(tmp, 680, 510);

        window.setScene(scene);

        window.show();
    }

    /**
     * Ulozi anotaciu do suboru.
     * @param content Obsah suboru.
     * @param file Subor na vystup.
     */
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException e) {
			e.printStackTrace();
        }
    }

    /**
     * Rozdeluje subor do datovej struktury.
     * @param file Subor so vstupom.
     */
    public void parseFile(File file) {
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
    }

    /**
     * Vykonanie jedneho pohybu hry.
     */
    public void playGame() {
        int delay;
		try {
            delay = Integer.parseInt(delayInput.getText());
		} catch (NumberFormatException e) {
            delay = 500;
            delayInput.setText("500");
		}
        Timeline animation = new Timeline(new KeyFrame(Duration.seconds(delay / 1000.0), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // next automated Move
                game.playGame();
                moveHistory.add(0, game.getLastMove());
                // refresh GUI
                spreadFigures();
            }
        }));
        this.currentMove = game.getMovesNum();
        this.moveCount = game.getMovesNum();
        animation.setCycleCount(game.getMovesNum());
        animation.play();
    }

    /**
     * Rozmiestnenie figuriek na zaciatku hry.
     */
    public void spreadFigures() {
        figureGroup.getChildren().clear();

        for (int col = 0; col <= 8; col++) {
            for (int row = 0; row <= 8; row++) {
                if (col > 0 && row < 8) {
                    FieldGUI field = new FieldGUI((col + row) % 2 == 1, row, col, 50);
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
                            if (game.move(figureBackend, newField) == true) {
                                this.currentMove++;
                                this.moveCount = currentMove;
                                while (moveHistory.size() != moveCount) {
                                    moveHistory.remove(0);
                                }
                                moveHistory.add(0, game.getLastMove());
                            }

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
            moveHistory.remove(0);
            game.undo();
            spreadFigures();
        }
    }
}
