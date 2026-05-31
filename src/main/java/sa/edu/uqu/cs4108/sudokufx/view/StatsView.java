package sa.edu.uqu.cs4108.sudokufx.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import sa.edu.uqu.cs4108.sudokufx.controller.SceneController;
import sa.edu.uqu.cs4108.sudokufx.data.Difficulty;
import sa.edu.uqu.cs4108.sudokufx.data.modal.ScoreModal;
import sa.edu.uqu.cs4108.sudokufx.data.tables.ScoreTable;

import java.util.Arrays;

public class StatsView extends AbstractView {

    public StatsView(SceneController nav) {
        super();

        Label title = new Label("Statistics");
        title.setStyle("""
                -fx-font-size: 36px;
                -fx-font-weight: bold;
                -fx-text-fill: white;
                """);

        getChildren().add(title);

        Arrays.stream(Difficulty.values()).forEach(difficulty -> {

            ScoreModal score = ScoreTable.getInstance().getHighScores(difficulty);

            VBox card = new VBox(6);
            card.setAlignment(Pos.CENTER_LEFT);
            card.setMaxWidth(300);

            card.setStyle("""
                    -fx-background-color: rgba(255,255,255,0.08);
                    -fx-background-radius: 12;
                    -fx-padding: 15;
                    -fx-border-color: rgba(255,255,255,0.15);
                    -fx-border-radius: 12;
                    """);

            Label diffLabel = new Label(difficulty.name());
            diffLabel.setStyle("""
                    -fx-font-size: 22px;
                    -fx-font-weight: bold;
                    -fx-text-fill: white;
                    """);

            card.getChildren().add(diffLabel);

            if (score.getId() != -1) {

                Label scoreLabel = new Label("Best Score: " + score.getScore());
                Label mistakesLabel = new Label("Mistakes: " + score.getMistakes());
                Label timeLabel = new Label(String.format(
                        "Time: %02d:%02d",
                        score.getTime() / 60,
                        score.getTime() % 60
                ));

                scoreLabel.setStyle("-fx-text-fill: #ecf0f1;");
                mistakesLabel.setStyle("-fx-text-fill: #ecf0f1;");
                timeLabel.setStyle("-fx-text-fill: #ecf0f1;");

                card.getChildren().addAll(
                        scoreLabel,
                        mistakesLabel,
                        timeLabel
                );

            } else {
                Label noRecord = new Label("No records yet");
                noRecord.setStyle("-fx-text-fill: #95a5a6;");

                card.getChildren().add(noRecord);
            }
            getChildren().add(card);
        });

        Button backBtn = new javafx.scene.control.Button("Back to Menu");
        styleButton(backBtn, 200);
        backBtn.setOnAction(e -> nav.showMainMenu());

        getChildren().add(backBtn);
    }
}