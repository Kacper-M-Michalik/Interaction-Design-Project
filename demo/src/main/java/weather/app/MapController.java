package weather.app;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class MapController {
    @FXML
    private Pane root;

    @FXML
    private void initialize() throws IOException {
        ElevationController elevationController = new ElevationController();
        elevationController.start(root);
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void switchToDetailed() throws IOException {
        App.setRoot("detailed");
    }
}