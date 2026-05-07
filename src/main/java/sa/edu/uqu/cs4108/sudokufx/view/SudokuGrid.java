package sa.edu.uqu.cs4108.sudokufx.view;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SudokuGrid extends GridPane {
    private TextField[][] cells = new TextField[9][9];

    public SudokuGrid() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(2); // Gap between cells
        this.setVgap(2);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cell = new TextField();
                cell.setPrefSize(50, 50);
                cell.setAlignment(Pos.CENTER);

                // Add styling for the 3x3 sub-grids (optional but looks professional)
                applyStyle(cell, row, col);

                cells[row][col] = cell;
                this.add(cell, col, row);
            }
        }
    }

    private void applyStyle(TextField cell, int row, int col) {
        String style = "-fx-font-size: 20; -fx-border-color: gray;";
        if ((row + 1) % 3 == 0 && row < 8) style += "-fx-border-width: 0 0 3 0;";
        if ((col + 1) % 3 == 0 && col < 8) style += "-fx-border-width: 0 3 0 0;";
        cell.setStyle(style);
    }
}