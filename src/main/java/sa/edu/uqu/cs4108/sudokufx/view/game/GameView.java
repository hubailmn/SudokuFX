package sa.edu.uqu.cs4108.sudokufx.view.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sa.edu.uqu.cs4108.sudokufx.controller.SceneController;
import sa.edu.uqu.cs4108.sudokufx.data.Difficulty;
import sa.edu.uqu.cs4108.sudokufx.util.SudokuGenerator;
import sa.edu.uqu.cs4108.sudokufx.view.AbstractView;

public class GameView extends AbstractView {

    private static final int SCORE_PER_CORRECT = 10;
    private final TextField[][] cells = new TextField[9][9];
    private int[][] solution;

    private int elapsedSeconds = 0;
    private int score = 0;
    private int multiplier = 1;

    private final Label timerLabel = new Label("Time: 00:00");
    private final Label scoreLabel = new Label("Score: 0");
    private final Label multiplierLabel = new Label("Multiplier: x1");
    private final Label statusLabel = new Label();

    private Timeline timer;

    public GameView(SceneController nav, Difficulty difficulty) {
        super();

        SudokuGenerator generator = new SudokuGenerator();
        int[][] full = generator.generate();
        this.solution = copyArray(full);
        int[][] puzzle = copyArray(full);
        removeCells(puzzle, difficulty.getCellsToRemove());

        styleHudLabel(timerLabel);
        styleHudLabel(scoreLabel);
        styleHudLabel(multiplierLabel);

        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox hud = new HBox(24, timerLabel, scoreLabel, multiplierLabel);
        hud.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(8));

        // 2. Build UI Grid
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                TextField cell = new TextField();
                cell.setPrefSize(50, 50);
                cell.setAlignment(Pos.CENTER);
                setupCellBehavior(cell, r, c, puzzle[r][c]);
                cells[r][c] = cell;
                grid.add(cell, c, r);
            }
        }
        VBox wrapper = new VBox(12, hud, grid, statusLabel);
        wrapper.setAlignment(Pos.CENTER);
        getChildren().add(wrapper);

        startTimer();
    }

    private void setupCellBehavior(TextField cell, int r, int c, int value) {
        if (value != 0) {
            cell.setText(String.valueOf(value));
            cell.setEditable(false);
            cell.setStyle("-fx-background-color: #ecf0f1; -fx-font-weight: bold;");
            return;
        }

        cell.textProperty().addListener((obs, oldV, newV) -> {
            if (newV == null || newV.isBlank()) {
                clearCellStyle(cell);
                return;
            }

            if (!newV.matches("[1-9]")) {
                cell.setText("");
                return;
            }

            int entered = Integer.parseInt(newV);
            if (entered == solution[r][c]) {
                cell.setEditable(false);
                cell.setStyle("-fx-background-color: #d5f5e3; -fx-font-weight: bold;");
                score += SCORE_PER_CORRECT * multiplier;
                multiplier++;
                statusLabel.setTextFill(Color.web("#2ecc71"));
                statusLabel.setText("Correct! +" + (SCORE_PER_CORRECT * (multiplier - 1)) + " points");
                updateHud();
                checkGameCompleted();
            } else {
                cell.clear();
                cell.setStyle("-fx-background-color: #f9ebea; -fx-border-color: #e74c3c;");
                multiplier = 1;
                statusLabel.setTextFill(Color.web("#e74c3c"));
                statusLabel.setText("Wrong number. Multiplier reset.");
                updateHud();
            }
        });
    }

    private void clearCellStyle(TextField cell) {
        cell.setStyle("");
    }

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            elapsedSeconds++;
            if (elapsedSeconds % 15 == 0 && multiplier > 1) {
                multiplier = Math.max(1, multiplier - 1);
            }
            updateHud();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void updateHud() {
        timerLabel.setText("Time: " + formatTime(elapsedSeconds));
        scoreLabel.setText("Score: " + score);
        multiplierLabel.setText("Multiplier: x" + multiplier);
    }

    private void styleHudLabel(Label label) {
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ecf0f1;");
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void checkGameCompleted() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (cells[r][c].isEditable()) {
                    return;
                }
            }
        }

        stopTimer();
        statusLabel.setTextFill(Color.web("#f1c40f"));
        statusLabel.setText("Puzzle completed! Final score: " + score + " in " + formatTime(elapsedSeconds));
    }

    private int[][] copyArray(int[][] source) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) System.arraycopy(source[i], 0, copy[i], 0, 9);
        return copy;
    }
}