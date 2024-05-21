package weather.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;

public class HomeController {
    @FXML
    private VBox screen;
    @FXML
    private ComboBox<String> searchBar;
    @FXML
    private CheckBox favouriteBox;
    @FXML
    private Text temperature, rainfall, visibility, snowfall, snowDepth, freezingHeight, apparentTemp;
    private SearchBar sb;

    void update() {
        temperature.setText(String.format("%s°", WeatherAndLocationManager.CurrentData.GetCurrentTemperature()));
        rainfall.setText(String.format("%smm", WeatherAndLocationManager.CurrentData.GetCurrentPrecipitation()));
        visibility.setText(String.format("%sm", WeatherAndLocationManager.CurrentData.GetCurrentVisibility()));
        snowfall.setText(String.format("%scm", WeatherAndLocationManager.CurrentData.GetCurrentSnowfall()));
        snowDepth.setText(String.format("%sm", WeatherAndLocationManager.CurrentData.GetCurrentSnowDepth()));
        freezingHeight.setText(String.format("%sm", WeatherAndLocationManager.CurrentData.GetCurrentFreezingHeight()));
        apparentTemp.setText(String.format("%s°", WeatherAndLocationManager.CurrentData.GetCurrentApparentTemp()));
    }

    @FXML
    private void initialize() {
        update();
        sb = new SearchBar(searchBar, favouriteBox);
        screen.requestFocus();
    }

    @FXML
    private void resetFocus() {
        screen.requestFocus();
    }

    @FXML
    private void onLocationSwitchRequest(){
        boolean switchedLocation = sb.requestLocationSwitch();
        if (switchedLocation){
            update();
            screen.requestFocus();
        }
    }

    @FXML
    private void updateFavourites(){
        sb.updateFavourites();
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
