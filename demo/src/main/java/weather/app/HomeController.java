package weather.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.input.InputMethodRequests;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;

public class HomeController {
    @FXML
    private ComboBox<String> searchBar;
    @FXML
    private CheckBox favouriteBox;
    @FXML
    private Text temperature, rainfall, visibility, snowfall, snowDepth, freezingHeight, apparentTemp;

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
        showFavouriteAndRecentLocations();
    }

    private void showFavouriteAndRecentLocations(){
        searchBar.getItems().setAll(UserProfile.getFavourites());
        searchBar.getItems().addAll(UserProfile.getRecents());
    }

    @FXML
    private void onLocationSwitchRequest(){
        String searchValue = searchBar.getValue();
        if (searchValue.equals(UserProfile.getCurrentLocation())){
            return;
        }
        if (searchValue.isEmpty()){
            showFavouriteAndRecentLocations();
            return;
        }
        LocationSearchResult[] possibleLocations = WeatherAndLocationManager.SearchLocations(searchValue);
        boolean locationChanged = false;
        for (LocationSearchResult l: possibleLocations){
            if (l.toString().equalsIgnoreCase(searchValue)){
                locationChanged = true;
                changeLocation(l);
                break;
            }
        }
        if (!locationChanged){
            if (possibleLocations.length > 0){
                updateSearchValues(possibleLocations);
            }
            else {
                showFavouriteAndRecentLocations();
            }
            searchBar.show();
        }

    }

    private void changeLocation(LocationSearchResult newLocation){
        WeatherAndLocationManager.LoadWeatherData(newLocation);
        update();
        UserProfile.updateRecents(newLocation.toString());
        UserProfile.setCurrentLocation(newLocation);
        favouriteBox.setSelected(UserProfile.isFavourite());
        searchBar.setPromptText(newLocation.toString());
    }

    private void updateSearchValues(LocationSearchResult[] possibleLocations){
        List<String> stringLocations = new ArrayList<>();
        for (LocationSearchResult l: possibleLocations){
            stringLocations.add(l.toString());
            if (stringLocations.size() >= 8){
                break;
            }
        }
        searchBar.getItems().setAll(stringLocations);
    }

    @FXML
    private void updateFavourites(){
        if (favouriteBox.isSelected()){
            UserProfile.addToFavourites(UserProfile.getCurrentLocation());
        }
        else{
            UserProfile.removeFromFavourites(UserProfile.getCurrentLocation());
        }
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
