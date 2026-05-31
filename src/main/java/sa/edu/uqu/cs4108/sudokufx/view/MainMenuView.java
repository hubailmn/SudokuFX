package sa.edu.uqu.cs4108.sudokufx.view;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import sa.edu.uqu.cs4108.sudokufx.controller.SceneController;


public class MainMenuView extends AbstractView {

    private Label title;
    private Label subtitle;

    private Button playButton;
    private Button statsButton;
    private Button aboutButton;

    public MainMenuView(SceneController nav) {
        title = new Label("SudokuFX");
        title.setStyle("-fx-font-size: 48px; -fx-text-fill: #ecf0f1; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 0);");

        subtitle = new Label("Jamoum University");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7; -fx-letter-spacing: 2px;");

        playButton = new Button("Play");
        statsButton = new Button("Statistics");
        aboutButton = new Button("About");

        styleButton(playButton);
        styleButton(statsButton);
        styleButton(aboutButton);

        playButton.setOnAction(e -> nav.showDifficultyMenu());
        statsButton.setOnAction(e -> nav.showStatsMenu());
        aboutButton.setOnAction(e -> nav.showAboutMenu());

        getChildren().addAll(
                title, subtitle,
                playButton, statsButton, aboutButton);

        this.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), this);
        fadeIn.setToValue(1);
        fadeIn.play();
    }


}
