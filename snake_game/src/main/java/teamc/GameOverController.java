package teamc;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameOverController {
    @FXML
    private Label scoreLabel;

    public void initialize() {
        scoreLabel.setText("Score: " + App.finalScore);
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}