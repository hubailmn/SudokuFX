package sa.edu.uqu.cs4108.sudokufx;

import javafx.application.Application;
import sa.edu.uqu.cs4108.sudokufx.data.DatabaseConnection;

public class Launcher {

    public static void main(String[] args) {
        new DatabaseConnection();
        Application.launch(SudokuApp.class, args);
    }
}
