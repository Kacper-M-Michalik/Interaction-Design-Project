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
        searchBar.setPromptText(UserProfile.getCurrentLocation());
        showFavouriteAndRecentLocations();
    }

    private void showFavouriteAndRecentLocations(){
        Platform.runLater(() -> {
            searchBar.getItems().setAll(UserProfile.getFavourites());
            searchBar.getItems().addAll(UserProfile.getRecents());
        });
    }

    public boolean requestLocationSwitch(){
        String searchValue = searchBar.getValue();
        if (searchValue == null || searchValue.isEmpty() || searchValue.equals(UserProfile.getCurrentLocation())){
            Platform.runLater(() -> searchBar.setValue(""));
            showFavouriteAndRecentLocations();
            return false;
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
            if (possibleLocations.length > 0) {
                updateSearchValues(possibleLocations);
            } else {
                showFavouriteAndRecentLocations();
            }
            searchBar.show();
        }
        return locationChanged;
    }

    private void changeLocation(LocationSearchResult newLocation){
        WeatherAndLocationManager.LoadWeatherData(newLocation);
        UserProfile.setCurrentLocation(newLocation);
        favouriteBox.setSelected(UserProfile.isFavourite());
        Platform.runLater(() -> {
            searchBar.setPromptText(newLocation.toString());
            searchBar.setValue("");
            searchBar.hide();
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
            boolean success = UserProfile.addToFavourites();
            favouriteBox.setSelected(success);
        }
        else{
            UserProfile.removeFromFavourites(UserProfile.getCurrentLocation());
        }
        showFavouriteAndRecentLocations();
    }

}
