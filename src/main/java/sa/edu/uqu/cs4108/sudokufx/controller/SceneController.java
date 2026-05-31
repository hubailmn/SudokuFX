package sa.edu.uqu.cs4108.sudokufx.controller;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import sa.edu.uqu.cs4108.sudokufx.data.Difficulty;
import sa.edu.uqu.cs4108.sudokufx.view.MainMenuView;
import sa.edu.uqu.cs4108.sudokufx.view.StatsView;
import sa.edu.uqu.cs4108.sudokufx.view.game.DifficultyMenuView;
import sa.edu.uqu.cs4108.sudokufx.view.game.GameView;
import sa.edu.uqu.cs4108.sudokufx.view.help.AboutView;

public class SceneController {
    private final StackPane root;

    public SceneController(StackPane root) {
        this.root = root;
        this.root.setStyle("-fx-background-color: linear-gradient(to bottom right, #2c3e50, #000000); -fx-padding: 50;");
        showMainMenu();
    }

    public void showMainMenu() {
        MainMenuView view = new MainMenuView(this);
        switchTo(view);
    }

    public void showDifficultyMenu() {
        DifficultyMenuView view = new DifficultyMenuView(this);
        switchTo(view);
    }

    public void showStatsMenu() {
        StatsView view = new StatsView(this);
        switchTo(view);
    }

    public void showAboutMenu() {
        AboutView view = new AboutView(this);
        switchTo(view);
    }

    public void startGame(String difficulty) {
        GameView view = new GameView(this, Difficulty.fromString(difficulty));
        switchTo(view);
    }

    private void switchTo(Region view) {
        if (root.getChildren().isEmpty()) {
            root.getChildren().add(view);
            return;
        }

        Node currentView = root.getChildren().getLast();
        root.getChildren().add(view);

        FadeTransition out = new FadeTransition(Duration.millis(250), currentView);
        out.setToValue(0);
        out.setOnFinished(e -> root.getChildren().remove(currentView));
        out.play();
    }


}