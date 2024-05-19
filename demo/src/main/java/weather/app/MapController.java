package weather.app;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MapController {
    @FXML
    private VBox screen;
    @FXML
    private Pane root;
    @FXML
    private ComboBox<String> searchBar;
    @FXML
    private CheckBox favouriteBox;
    private SearchBar sb;

    @FXML
    private void initialize() throws IOException {
        ElevationController elevationController = new ElevationController();
        elevationController.start(root);
        sb = new SearchBar(searchBar, favouriteBox);
        screen.requestFocus();
    }

    @FXML
    private void onLocationSwitchRequest(){
        boolean switchedLocation = sb.requestLocationSwitch();
        if (switchedLocation){
            //update();
            screen.requestFocus();
        }
    }

    @FXML
    private void updateFavourites(){
        sb.updateFavourites();
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