package weather.app;

import java.io.IOException;
import javafx.fxml.FXML;

public class DetailedController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("home");
    }
}