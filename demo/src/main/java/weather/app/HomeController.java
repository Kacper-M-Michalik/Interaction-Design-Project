package weather.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;
import javafx.stage.Popup;

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
    private void popupTemp() {
        createPopup("Temperature", "Degree of hotness or coldness, in Celsius");
    }

    @FXML
    private void popupRainfall() {
        createPopup("Rainfall", "The quantity of water that is precipitated in a specified area and time interval");
    }
    @FXML
    private void popupVisibility() {
        createPopup("Visibility", "The distance at which an object or light can be clearly discerned");
    }
    @FXML
    private void popupSnowFall() {
        createPopup("Snowfall", "The amount of snow that falls in a given period");
    }
    @FXML
    private void popupSnowDepth() {
        createPopup("Snow Depth", "How deep the total snow level is");
    }
    @FXML
    private void popupFreezingHeight() {
        createPopup("Freezing Height", "The altitude in which the temperature is at 0 degrees celsius");
    }

    @FXML
    private void popupFeels() {
        createPopup("Apparent Temperature", "What the temperature feels like taking into account the relative humidity");
    }

    private void createPopup(String feature, String message){
        Alert alert = new Alert(Alert.AlertType.NONE, "", ButtonType.OK);
        alert.setTitle(feature);
        alert.setContentText(message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
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

    @FXML
    private void setCurrentDay() {

    }
}
