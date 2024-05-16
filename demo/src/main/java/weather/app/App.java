package weather.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;



    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"), 400, 720);
        stage.setScene(scene);
        stage.show();

        LocationSearchResult[] Results = WeatherAndLocationManager.SearchLocations("London");
        WeatherAndLocationManager.LoadWeatherData(Results[0]);
        WeatherAndLocationManager.CurrentData.GetPrecipitationProbabilities();
        System.out.println(WeatherAndLocationManager.CurrentData.JSON.getString("latitude"));
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}