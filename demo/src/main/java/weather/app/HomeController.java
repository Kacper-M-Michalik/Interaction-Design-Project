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
    private Text temperature;

    @FXML
    private void initialize() {

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
