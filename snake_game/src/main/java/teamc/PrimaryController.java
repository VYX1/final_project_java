package teamc;

import java.io.IOException;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private void switchToSettings() throws IOException {
        App.setRoot("settings");
    }

    public void startGame(ActionEvent event) {
        Board gameBoard = new Board();
        
        Parent gameRoot = gameBoard.getView();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(gameRoot);
    }


}
