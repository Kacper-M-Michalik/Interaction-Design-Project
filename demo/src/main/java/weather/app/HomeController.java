package weather.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;

public class HomeController {
    @FXML
    private Text temperature, rainfall, visibility, snowfall, snowDepth, freezingHeight, apparentTemp;

    void update() {
        temperature.setText(String.format("%s°", WeatherAndLocationManager.CurrentData.GetTemperatures()[0]));
        rainfall.setText(String.format("%smm", WeatherAndLocationManager.CurrentData.GetPrecipitation()[0]));
        visibility.setText(String.format("%sm", WeatherAndLocationManager.CurrentData.GetVisibilities()[0]));
        snowfall.setText(String.format("%scm", WeatherAndLocationManager.CurrentData.GetSnowfalls()[0]));
        snowDepth.setText(String.format("%sm", WeatherAndLocationManager.CurrentData.GetSnowdepths()[0]));
        freezingHeight.setText(String.format("%sm", WeatherAndLocationManager.CurrentData.GetFreezingHeights()[0]));
        apparentTemp.setText(String.format("%s°", WeatherAndLocationManager.CurrentData.GetApparentTemps()[0]));
    }
    @FXML
    private void initialize() {
        update();
    }
    public ComboBox<String> searchBar;

    @FXML
    private void updateSearchItemsDefault(){
        ObservableList<String> items = searchBar.getItems();
        items.setAll(UserProfile.getFavouritesString());
        items.addAll(UserProfile.getRecentsString());
    }

    @FXML
    private void updateSearchItemsOnSearch(){
        ObservableList<String> items = searchBar.getItems();
        String searchValue = searchBar.getValue();
        LocationSearchResult[] possibleLocations = WeatherAndLocationManager.SearchLocations(searchValue);
        List<String> stringLocations = new ArrayList<>();


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
