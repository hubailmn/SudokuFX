package sa.edu.uqu.cs4108.sudokufx.view.game;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sa.edu.uqu.cs4108.sudokufx.controller.SceneController;
import sa.edu.uqu.cs4108.sudokufx.data.Difficulty;
import sa.edu.uqu.cs4108.sudokufx.util.SudokuGenerator;
import sa.edu.uqu.cs4108.sudokufx.view.AbstractView;

public class GameView extends AbstractView {
    private final TextField[][] cells = new TextField[9][9];
    private int[][] solution;

    public GameView(SceneController nav, String difficultyName) {
        super();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        // 1. Generate Board Logic
        SudokuGenerator generator = new SudokuGenerator();
        this.solution = generator.generate(); // Full solution
        int[][] puzzle = copyArray(solution);
        generator.removeCells(Difficulty.fromString(difficultyName).getCellsToRemove());

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
        getChildren().add(grid);
    }

    private void setupCellBehavior(TextField cell, int r, int c, int value) {
        if (value != 0) {
            cell.setText(String.valueOf(value));
            cell.setEditable(false);
            cell.setStyle("-fx-background-color: #ecf0f1; -fx-font-weight: bold;");
        } else {
            cell.textProperty().addListener((obs, oldV, newV) -> {
                if (!newV.matches("[1-9]")) cell.setText("");
                // Add validation logic here against 'solution[r][c]'
            });
        }
    }

    private int[][] copyArray(int[][] source) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) System.arraycopy(source[i], 0, copy[i], 0, 9);
        return copy;
    }
}