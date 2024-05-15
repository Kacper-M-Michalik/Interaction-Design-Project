package weather.app;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class DetailedController {
    @FXML
    private Text clock;

    @FXML
    private void initialize() throws IOException {

    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
}