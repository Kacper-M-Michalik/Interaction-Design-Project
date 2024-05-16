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
    public ComboBox<String> searchBar;
    @FXML
    private Text temperature;

    @FXML
    private void initialize() {

    }

    @FXML void updateSearchItems(){
        ObservableList<String> items = searchBar.getItems();
        if (searchBar.getValue().isEmpty()){
            updateSearchItemsDefault(items);
        }
        else{
            updateSearchItemsOnSearch(items);
        }
    }

    @FXML
    private void updateSearchItemsDefault(ObservableList<String> items){
        items.setAll(UserProfile.getFavouritesString());
        items.addAll(UserProfile.getRecentsString());
    }

    @FXML
    private void updateSearchItemsOnSearch(ObservableList<String> items){
        String searchValue = searchBar.getValue();
        LocationSearchResult[] possibleLocations = WeatherAndLocationManager.SearchLocations(searchValue);
        List<String> stringLocations = new ArrayList<>();
        for (LocationSearchResult l: possibleLocations){
            stringLocations.add(l.toString());
            if (stringLocations.size() >= 8){
                break;
            }
        }
        items.setAll(stringLocations);
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
