package sa.edu.uqu.cs4108.sudokufx.view;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import sa.edu.uqu.cs4108.sudokufx.util.SoundManager;

public abstract class AbstractView extends VBox {

    public AbstractView() {
        setSpacing(20);
        setAlignment(Pos.CENTER);

        setStyle("-fx-background-color: linear-gradient(to bottom right, #2c3e50, #000000); -fx-padding: 50;");

        this.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), this);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    public void styleButton(Button btn) {
        btn.setMinWidth(220);
        btn.setStyle("""
                -fx-background-color: rgba(255, 255, 255, 0.1);
                -fx-text-fill: white;
                -fx-font-size: 18px;
                -fx-background-radius: 15;
                -fx-border-color: rgba(255,255,255,0.3);
                -fx-border-radius: 15;
                """);

        btn.setOnMouseClicked(e -> {
            SoundManager.playAccept();
        });


        btn.setOnMouseEntered(e -> {
            btn.setStyle("""
                    -fx-background-color: #3498db;
                    -fx-font-size: 18px;
                    -fx-text-fill: white;
                    -fx-background-radius: 15;
                    """);
            ScaleTransition st = new ScaleTransition(Duration.millis(200), btn);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
            SoundManager.playHover();
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle("""
                    -fx-background-color: rgba(255, 255, 255, 0.1);
                    -fx-text-fill: white;
                    -fx-font-size: 18px;
                    -fx-background-radius: 15;
                    -fx-border-color: rgba(255,255,255,0.3);
                    -fx-border-radius: 15;
                    """);
            ScaleTransition st = new ScaleTransition(Duration.millis(200), btn);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }

}
