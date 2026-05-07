package sa.edu.uqu.cs4108.sudokufx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sa.edu.uqu.cs4108.sudokufx.controller.SceneController;

public class SudokuApp extends Application {

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();

        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("SudokuFX");
        stage.setScene(scene);
        stage.show();

        SceneController controller = new SceneController(root);
        controller.showMainMenu();
    }
}