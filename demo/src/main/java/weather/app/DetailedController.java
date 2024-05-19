package weather.app;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DetailedController {
    @FXML
    private VBox screen;
    @FXML
    private Text clock;
    @FXML
    private ComboBox<String> searchBar;
    @FXML
    private CheckBox favouriteBox;
    private SearchBar sb;
    @FXML
    private Button button_current;
    @FXML
    private Button button_next;
    @FXML
    private Button button_3;
    @FXML
    private Button button_4;
    @FXML
    private Button button_5;
    @FXML
    private Button button_6;
    @FXML
    private Button button_7;
    @FXML
    private Button button_8;
    @FXML
    private Button button_9;
    @FXML
    private Button button_10;
    @FXML
    private Button button_11;
    @FXML
    private Button button_12;
    @FXML
    private Button button_13;
    @FXML
    private Button button_14;
    @FXML
    private ScrollBar scroll_bar;


    @FXML
    private void initialize() throws IOException {
        labelButtons();
        // setScrollBar();

        sb = new SearchBar(searchBar, favouriteBox);
        screen.requestFocus();
    }

    @FXML
    private void onLocationSwitchRequest(){
        boolean switchedLocation = sb.requestLocationSwitch();
        /*
        if (switchedLocation){
            update();
        }
        */
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
    private void switchToMap() throws IOException {
        App.setRoot("map");
    }

    @FXML
    private void labelButtons(){
        Format f = new SimpleDateFormat("EEEE");
        String dayOfWeek = f.format(new Date());
        button_current.setText(dayOfWeek);

        if (dayOfWeek.equals("Monday")) {
            button_next.setText("Tuesday");
            button_3.setText("Wednesday");
            button_4.setText("Thursday");
            button_5.setText("Friday");
            button_6.setText("Saturday");
            button_7.setText("Sunday");
            button_8.setText("Monday");
            button_9.setText("Tuesday");
            button_10.setText("Wednesday");
            button_11.setText("Thursday");
            button_12.setText("Friday");
            button_13.setText("Saturday");
            button_14.setText("Sunday");
        }
        if (dayOfWeek.equals("Tuesday")) {
            button_next.setText("Wednesday");
            button_3.setText("Thursday");
            button_4.setText("Friday");
            button_5.setText("Saturday");
            button_6.setText("Sunday");
            button_7.setText("Monday");
            button_8.setText("Tuesday");
            button_9.setText("Wednesday");
            button_10.setText("Thursday");
            button_11.setText("Friday");
            button_12.setText("Saturday");
            button_13.setText("Sunday");
            button_14.setText("Monday");
        }
        if (dayOfWeek.equals("Wednesday")) {
            button_next.setText("Thursday");
            button_3.setText("Friday");
            button_4.setText("Saturday");
            button_5.setText("Sunday");
            button_6.setText("Monday");
            button_7.setText("Tuesday");
            button_8.setText("Wednesday");
            button_9.setText("Thursday");
            button_10.setText("Friday");
            button_11.setText("Saturday");
            button_12.setText("Sunday");
            button_13.setText("Monday");
            button_14.setText("Tuesday");
        }
        if (dayOfWeek.equals("Thursday")) {
            button_next.setText("Friday");
            button_3.setText("Saturday");
            button_4.setText("Sunday");
            button_5.setText("Monday");
            button_6.setText("Tuesday");
            button_7.setText("Wednesday");
            button_8.setText("Thursday");
            button_9.setText("Friday");
            button_10.setText("Saturday");
            button_11.setText("Sunday");
            button_12.setText("Monday");
            button_13.setText("Tuesday");
            button_14.setText("Wednesday");
        }
        if (dayOfWeek.equals("Friday")) {
            button_next.setText("Saturday");
            button_3.setText("Sunday");
            button_4.setText("Monday");
            button_5.setText("Tuesday");
            button_6.setText("Wednesday");
            button_7.setText("Thursday");
            button_8.setText("Friday");
            button_9.setText("Saturday");
            button_10.setText("Sunday");
            button_11.setText("Monday");
            button_12.setText("Tuesday");
            button_13.setText("Wednesday");
            button_14.setText("Thursday");
        }
        if (dayOfWeek.equals("Saturday")) {
            button_next.setText("Sunday");
            button_3.setText("Monday");
            button_4.setText("Tuesday");
            button_5.setText("Wednesday");
            button_6.setText("Thursday");
            button_7.setText("Friday");
            button_8.setText("Saturday");
            button_9.setText("Sunday");
            button_10.setText("Monday");
            button_11.setText("Tuesday");
            button_12.setText("Wednesday");
            button_13.setText("Thursday");
            button_14.setText("Friday");
        }
        if (dayOfWeek.equals("Sunday")) {
            button_next.setText("Monday");
            button_3.setText("Tuesday");
            button_4.setText("Wednesday");
            button_5.setText("Thursday");
            button_6.setText("Friday");
            button_7.setText("Saturday");
            button_8.setText("Sunday");
            button_9.setText("Monday");
            button_10.setText("Tuesday");
            button_11.setText("Wednesday");
            button_12.setText("Thursday");
            button_13.setText("Friday");
            button_14.setText("Saturday");
        }

    }

    @FXML
    private void setScrollBar() {

    }
}