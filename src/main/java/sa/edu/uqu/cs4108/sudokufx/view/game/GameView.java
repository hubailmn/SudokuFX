package sa.edu.uqu.cs4108.sudokufx.view.game;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sa.edu.uqu.cs4108.sudokufx.controller.SceneController;
import sa.edu.uqu.cs4108.sudokufx.data.Difficulty;
import sa.edu.uqu.cs4108.sudokufx.util.SoundManager;
import sa.edu.uqu.cs4108.sudokufx.util.SudokuGenerator;
import sa.edu.uqu.cs4108.sudokufx.view.AbstractView;

public class GameView extends AbstractView {

    private Difficulty difficulty;
    private static final int DECAY_INTERVAL_SECONDS = 90;

    private final TextField[][] cells = new TextField[9][9];
    private final int[][] solution;

    private int elapsedSeconds = 0;
    private int score = 0;
    private int multiplier;
    private int mistakes = 0;

    private final Label timerLabel = new Label();
    private final Label scoreLabel = new Label();
    private final Label multiplierLabel = new Label();
    private final Label mistakesLabel = new Label();
    private final Label statusLabel = new Label();

    private Timeline timer;

    public GameView(SceneController nav, Difficulty difficulty) {
        super();
        this.difficulty = difficulty;

        SudokuGenerator generator = new SudokuGenerator();
        int[][] full = generator.generate();
        this.solution = copyArray(full);
        int[][] puzzle = copyArray(full);
        removeCells(puzzle, difficulty.getCellsToRemove());

        this.multiplier = difficulty.getMultiplier();

        styleHudLabel(timerLabel);
        styleHudLabel(scoreLabel);
        styleHudLabel(multiplierLabel);
        styleHudLabel(mistakesLabel);
        statusLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        updateHud();

        HBox hud = new HBox(28, timerLabel, scoreLabel, multiplierLabel, mistakesLabel);
        hud.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(8));

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                TextField cell = new TextField();
                cell.setPrefSize(52, 52);
                cell.setAlignment(Pos.CENTER);
                setupCellBehavior(cell, r, c, puzzle[r][c]);
                cells[r][c] = cell;

                double top = (r % 3 == 0 && r != 0) ? 6 : 1;
                double left = (c % 3 == 0 && c != 0) ? 6 : 1;
                GridPane.setMargin(cell, new Insets(top, 1, 1, left));

                grid.add(cell, c, r);
            }
        }

        Button backBtn = new Button("← Back to Menu");
        backBtn.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: #95a5a6;
                -fx-font-size: 13px;
                -fx-border-color: rgba(255,255,255,0.15);
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                -fx-padding: 6 14 6 14;
                """);
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("""
                -fx-background-color: rgba(255,255,255,0.08);
                -fx-text-fill: #ecf0f1;
                -fx-font-size: 13px;
                -fx-border-color: rgba(255,255,255,0.3);
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                -fx-padding: 6 14 6 14;
                """));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: #95a5a6;
                -fx-font-size: 13px;
                -fx-border-color: rgba(255,255,255,0.15);
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                -fx-padding: 6 14 6 14;
                """));
        backBtn.setOnAction(e -> {
            stopTimer();
            nav.showMainMenu();
        });

        VBox wrapper = new VBox(14, hud, grid, statusLabel, backBtn);
        wrapper.setAlignment(Pos.CENTER);
        getChildren().add(wrapper);

        startTimer();
    }

    private void setupCellBehavior(TextField cell, int r, int c, int value) {
        if (value != 0) {
            cell.setText(String.valueOf(value));
            cell.setEditable(false);
            applyPrefilledStyle(cell);

            // Highlight matching numbers on click
            cell.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
                if (isFocused) highlightMatching(cell.getText());
                else clearAllHighlights();
            });
            return;
        }

        applyEmptyStyle(cell);

        cell.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused && !cell.getText().isBlank()) highlightMatching(cell.getText());
            else if (!isFocused) clearAllHighlights();
        });

        cell.textProperty().addListener((obs, oldV, newV) -> {
            if (newV == null || newV.isBlank()) {
                applyEmptyStyle(cell);
                return;
            }

            if (!newV.matches("[1-9]")) {
                cell.setText(oldV);
                return;
            }

            int entered = Integer.parseInt(newV);
            if (entered == solution[r][c]) {
                cell.setEditable(false);

                applyCorrectFlash(cell);
                PauseTransition pause = new PauseTransition(Duration.millis(500));
                pause.setOnFinished(e -> {
                    applyPrefilledStyle(cell);
                    highlightMatching(String.valueOf(entered));
                });
                pause.play();
                highlightMatching(String.valueOf(entered));

                score += entered * multiplier;
                statusLabel.setTextFill(Color.web("#2ecc71"));
                statusLabel.setText("✓ Correct! +" + (entered * multiplier) + " pts");
                updateHud();
                SoundManager.playAccept();
                checkGameCompleted();
            } else {
                applyWrongFlash(cell);
                PauseTransition pause = new PauseTransition(Duration.millis(400));
                pause.setOnFinished(e -> {
                    cell.clear();
                    applyEmptyStyle(cell);
                });
                pause.play();

                mistakes++;
                multiplier = Math.max(1, difficulty.getMultiplier() - (mistakes / 4));
                statusLabel.setTextFill(Color.web("#e74c3c"));
                statusLabel.setText("✗ Wrong! Multiplier reset. Mistakes: " + mistakes);
                SoundManager.playFah();
                updateHud();
            }
        });
    }

    // --- Highlight helpers ---
    private void highlightMatching(String value) {
        if (value == null || value.isBlank()) return;
        clearAllHighlights();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                TextField t = cells[r][c];
                if (value.equals(t.getText())) {
                    applyMatchHighlight(t);
                }
            }
        }
    }

    private void clearAllHighlights() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                TextField t = cells[r][c];
                if (!t.isEditable()) applyPrefilledStyle(t);
                else if (t.getText().isBlank()) applyEmptyStyle(t);
            }
        }
    }


    private void applyPrefilledStyle(TextField cell) {
        cell.setStyle("""
                -fx-background-color: rgba(255,255,255,0.12);
                -fx-text-fill: #ecf0f1;
                -fx-font-weight: bold;
                -fx-font-size: 18px;
                -fx-border-color: rgba(255,255,255,0.1);
                -fx-border-radius: 4;
                -fx-background-radius: 4;
                """);
    }

    private void applyEmptyStyle(TextField cell) {
        cell.setStyle("""
                -fx-background-color: rgba(255,255,255,0.05);
                -fx-text-fill: #3498db;
                -fx-font-size: 18px;
                -fx-border-color: rgba(255,255,255,0.2);
                -fx-border-radius: 4;
                -fx-background-radius: 4;
                """);
    }

    private void applyCorrectFlash(TextField cell) {
        cell.setStyle("""
                -fx-background-color: rgba(46,204,113,0.25);
                -fx-text-fill: #2ecc71;
                -fx-font-weight: bold;
                -fx-font-size: 18px;
                -fx-border-color: #2ecc71;
                -fx-border-radius: 4;
                -fx-background-radius: 4;
                """);
    }

    private void applyWrongFlash(TextField cell) {
        cell.setStyle("""
                -fx-background-color: rgba(231,76,60,0.2);
                -fx-text-fill: #e74c3c;
                -fx-font-size: 18px;
                -fx-border-color: #e74c3c;
                -fx-border-radius: 4;
                -fx-background-radius: 4;
                """);
    }

    private void applyMatchHighlight(TextField cell) {
        cell.setStyle("""
                -fx-background-color: rgba(241,196,15,0.2);
                -fx-text-fill: #f1c40f;
                -fx-font-weight: bold;
                -fx-font-size: 18px;
                -fx-border-color: #f1c40f;
                -fx-border-radius: 4;
                -fx-background-radius: 4;
                """);
    }

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            elapsedSeconds++;
            if (elapsedSeconds % DECAY_INTERVAL_SECONDS == 0 && multiplier > 1) {
                multiplier = Math.max(1, multiplier - 1);
            }
            updateHud();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void stopTimer() {
        if (timer != null) timer.stop();
    }

    private void updateHud() {
        timerLabel.setText("⏱ " + formatTime(elapsedSeconds));
        scoreLabel.setText("★ " + score);
        multiplierLabel.setText("× " + multiplier);
        mistakesLabel.setText("✗ " + mistakes);
    }

    private void styleHudLabel(Label label) {
        label.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: #ecf0f1;");
    }

    private String formatTime(int totalSeconds) {
        return String.format("%02d:%02d", totalSeconds / 60, totalSeconds % 60);
    }

    private void checkGameCompleted() {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (cells[r][c].isEditable()) return;

        stopTimer();
        statusLabel.setTextFill(Color.web("#f1c40f"));
        statusLabel.setText("🎉 Puzzle complete! Score: " + score + " | Time: " + formatTime(elapsedSeconds) + " | Mistakes: " + mistakes);
    }

    private void removeCells(int[][] board, int count) {
        java.util.Random rng = new java.util.Random();
        int removed = 0;
        while (removed < count) {
            int r = rng.nextInt(9);
            int c = rng.nextInt(9);
            if (board[r][c] != 0) {
                board[r][c] = 0;
                removed++;
            }
        }
    }

    private int[][] copyArray(int[][] source) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) System.arraycopy(source[i], 0, copy[i], 0, 9);
        return copy;
    }
}