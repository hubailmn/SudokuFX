package sa.edu.uqu.cs4108.sudokufx.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sa.edu.uqu.cs4108.sudokufx.controller.SceneController;

public class AboutView extends AbstractView {

    public AboutView(SceneController nav) {
        super();
        Label selectLabel = new Label("About the puzzle");
        selectLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: #ecf0f1; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 0);");
        getChildren().add(selectLabel);

        Label description = new Label(
                "This project was developed by students at Jamoum University College, \n" +
                        "Umm Al-Qura University, as part of the CS4108 course."
        );
        description.setStyle("-fx-font-size: 16px; -fx-text-fill: #bdc3c7; -fx-text-alignment: center;");
        description.setWrapText(true);
        description.setMaxWidth(600);

        Label howToPlayTitle = new Label("How to Play");
        howToPlayTitle.setStyle("-fx-font-size: 24px; -fx-text-fill: #3498db; -fx-font-weight: bold; -fx-padding: 20 0 0 0;");

        Label instructions = new Label(
                "1. Fill each 9x9 grid so that every row, column, and 3x3 box contains the digits 1-9.\n" +
                        "2. No number can repeat within the same row, column, or 3x3 subgrid.\n" +
                        "3. Correct entries increase your score based on the current multiplier.\n" +
                        "4. Making mistakes will decrease your score multiplier.\n" +
                        "5. Complete the puzzle as fast as possible to maintain a high multiplier!"
        );
        instructions.setStyle("-fx-font-size: 14px; -fx-text-fill: #ecf0f1; -fx-line-spacing: 5px;");
        instructions.setWrapText(true);
        instructions.setMaxWidth(550);

        Button backBtn = new javafx.scene.control.Button("Back to Menu");
        styleButton(backBtn, 200);
        backBtn.setOnAction(e -> nav.showMainMenu());

        getChildren().addAll(description, howToPlayTitle, instructions, backBtn);

    }
}
