package weather.app;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class HomeController {
    @FXML
    private Text temperature;

    @FXML
    private void initialize() {

    }

    @FXML
    private void switchToDetailed() throws IOException {
        App.setRoot("detailed");
    }

    @FXML
    private void switchToMap() throws IOException {
        App.setRoot("map");
    }
}
