package teamc;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;

public class SettingsController {
    @FXML private ComboBox<GameSettings.Difficulty> difficultyCombo;
    @FXML private ColorPicker headColorPicker;
    @FXML private ColorPicker bodyColorPicker;

    @FXML
    public void initialize() {
        difficultyCombo.getItems().setAll(GameSettings.Difficulty.values());
        difficultyCombo.setValue(GameSettings.Difficulty.valueOf(
            getCurrentDifficultyName(GameSettings.GAME_SPEED)
        ));
        headColorPicker.setValue(GameSettings.HEAD_COLOR);
        bodyColorPicker.setValue(GameSettings.BODY_COLOR);
    }

    @FXML
    private void saveSettings() {
        GameSettings.GAME_SPEED = difficultyCombo.getValue().interval;
        GameSettings.HEAD_COLOR = headColorPicker.getValue();
        GameSettings.BODY_COLOR = bodyColorPicker.getValue();
    }

    private String getCurrentDifficultyName(long currentSpeed) {
        for (GameSettings.Difficulty d : GameSettings.Difficulty.values()) {
            if (d.interval == currentSpeed) return d.name();
        }
        return "MEDIUM";
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
