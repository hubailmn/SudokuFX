package sa.edu.uqu.cs4108.sudokufx.view.game;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import sa.edu.uqu.cs4108.sudokufx.controller.SceneController;
import sa.edu.uqu.cs4108.sudokufx.view.AbstractView;

public class DifficultyMenuView extends AbstractView {

    public DifficultyMenuView(SceneController nav) {
        super();
        Label selectLabel = new Label("Select Difficulty");
        selectLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: #ecf0f1; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 0);");
        getChildren().add(selectLabel);

        String[] levels = {"Flash", "Easy", "Medium", "Hard", "Expert"};
        for (String level : levels) {
            Button b = new Button(level);
            styleButton(b);
            b.setOnAction(e -> nav.startGame(level));
            getChildren().add(b);
        }

        Button backBtn = new Button("Back");
        styleButton(backBtn);
        backBtn.setOnAction(e -> nav.showMainMenu());
        getChildren().add(backBtn);

        this.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), this);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}