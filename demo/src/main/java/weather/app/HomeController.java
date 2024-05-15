package weather.app;

import java.io.IOException;
import javafx.fxml.FXML;

public class HomeController {

    @FXML
    private void switchToDetailed() throws IOException {
        App.setRoot("detailed");
    }
}
