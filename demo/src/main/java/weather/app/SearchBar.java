package weather.app;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class SearchBar {
    private final ComboBox<String> searchBar;
    private final CheckBox favouriteBox;


    public SearchBar(ComboBox<String> searchBar, CheckBox favouriteBox){
        this.searchBar = searchBar;
        this.favouriteBox = favouriteBox;
        String currentLocation = UserProfile.getCurrentLocation();
        if (currentLocation.equalsIgnoreCase("current location")){
            searchBar.setPromptText("Current Location - Cambridge GB");
        }
        else {
            searchBar.setPromptText(UserProfile.getCurrentLocation());
        }
        updateFavourites();
        showFavouriteAndRecentLocations();
    }

    private void showFavouriteAndRecentLocations(){
        Platform.runLater(() -> {
            searchBar.getItems().setAll("Current Location");
            searchBar.getItems().addAll(UserProfile.getFavourites());
            searchBar.getItems().addAll(UserProfile.getRecents());
        });
    }

    public boolean requestLocationSwitch(){
        String searchValue = searchBar.getValue();
        if (searchValue == null || searchValue.isEmpty() || searchValue.equalsIgnoreCase(UserProfile.getCurrentLocation()) || searchValue.equalsIgnoreCase("no location found")){
            Platform.runLater(() -> {
                searchBar.setValue("");
                searchBar.hide();
            });
            showFavouriteAndRecentLocations();
            return false;
        }
        searchValue = searchValue.replaceAll(", ", " ");
        if (searchValue.equalsIgnoreCase("current location")){
            goToCurrentLocation();
            return true;
        }
        if (handleLongLat(searchValue)){
            return true;
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
        if (!locationChanged) {
            Platform.runLater(() -> searchBar.setPromptText(UserProfile.getCurrentLocation()));
            if (possibleLocations.length > 0) {
                updateSearchValues(possibleLocations);
            } else {
                Platform.runLater(() -> searchBar.getItems().setAll("No location found"));
            }
            searchBar.show();
        }
        return locationChanged;
    }

    private void goToCurrentLocation(){
        LocationSearchResult newLocation = UserProfile.getCurrentLatLong();
        changeLocation(newLocation);
        updateFavourites();
    }

    private boolean handleLongLat(String val){
        String[] words = val.split(" ");
        if (words.length == 2){
            try {
                float lat = Float.parseFloat(words[0]);
                float lon = Float.parseFloat(words[1]);
                if (Math.abs(lat) > 90 || Math.abs(lon) > 180) {
                    // Invalid latitude or longitude
                    return false;
                }
                LocationSearchResult newLocation = new LocationSearchResult(Float.toString(lat), Float.toString(lon), lat, lon);
                changeLocation(newLocation);
                return true;
            }
            catch (NumberFormatException e){
                return false;
            }
        }
        return false;
    }

    private void changeLocation(LocationSearchResult newLocation){
        WeatherAndLocationManager.LoadWeatherData(newLocation);
        UserProfile.setCurrentLocation(newLocation);
        favouriteBox.setSelected(UserProfile.isFavourite());
        Platform.runLater(() -> {
            searchBar.setPromptText(newLocation.toString());
            searchBar.setValue("");
            searchBar.hide();
            if (App.CurrentUpdatable != null) 
            {
                App.CurrentUpdatable.LocationUpdated();
            }
        });
        showFavouriteAndRecentLocations();
    }

    private void updateSearchValues(LocationSearchResult[] possibleLocations){
        List<String> stringLocations = new ArrayList<>();
        for (LocationSearchResult l: possibleLocations){
            stringLocations.add(l.toString());
            if (stringLocations.size() >= 8){
                break;
            }
        }
        Platform.runLater(() -> searchBar.getItems().setAll(stringLocations));
    }

    public void updateFavourites(){
        if (favouriteBox.isSelected()){
            boolean success = UserProfile.addToFavourites() || UserProfile.getCurrentLocation().equalsIgnoreCase("current location");
            favouriteBox.setSelected(success);
        }
        else{
            if (UserProfile.getCurrentLocation().equalsIgnoreCase("current location")){
                favouriteBox.setSelected(true);
            }
            else {
                UserProfile.removeFromFavourites(UserProfile.getCurrentLocation());
            }
        }
        showFavouriteAndRecentLocations();
    }

}
