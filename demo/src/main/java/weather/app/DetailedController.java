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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private ButtonBar button_bar;
    @FXML
    private ScrollBar scroll_bar;
    @FXML
    private ScrollPane scroll_pane;


    @FXML
    private void initialize() throws IOException {
        labelButtons();
        //setScrollBar();
        setScrollPane();

        sb = new SearchBar(searchBar, favouriteBox);
        screen.requestFocus();
    }

    @FXML
    private void onLocationSwitchRequest(){
        boolean switchedLocation = sb.requestLocationSwitch();
        if (switchedLocation){
            //update();
            screen.requestFocus();
        }
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
        String dayOfWeek = (f.format(new Date())).substring(0,3);
        button_current.setText(dayOfWeek);

        if (dayOfWeek.equals("Mon")) {
            button_next.setText("Tue");
            button_3.setText("Wed");
            button_4.setText("Thu");
            button_5.setText("Fri");
            button_6.setText("Sat");
            button_7.setText("Sun");
            button_8.setText("Mon");
            button_9.setText("Tue");
            button_10.setText("Wed");
            button_11.setText("Thu");
            button_12.setText("Fri");
            button_13.setText("Sat");
            button_14.setText("Sun");
        }
        if (dayOfWeek.equals("Tue")) {
            button_next.setText("Wed");
            button_3.setText("Thu");
            button_4.setText("Fri");
            button_5.setText("Sat");
            button_6.setText("Sun");
            button_7.setText("Mon");
            button_8.setText("Tue");
            button_9.setText("Wed");
            button_10.setText("Thu");
            button_11.setText("Fri");
            button_12.setText("Sat");
            button_13.setText("Sun");
            button_14.setText("Mon");
        }
        if (dayOfWeek.equals("Wed")) {
            button_next.setText("Thu");
            button_3.setText("Fri");
            button_4.setText("Sat");
            button_5.setText("Sun");
            button_6.setText("Mon");
            button_7.setText("Tue");
            button_8.setText("Wed");
            button_9.setText("Thu");
            button_10.setText("Fri");
            button_11.setText("Sat");
            button_12.setText("Sun");
            button_13.setText("Mon");
            button_14.setText("Tue");
        }
        if (dayOfWeek.equals("Thu")) {
            button_next.setText("Fri");
            button_3.setText("Sat");
            button_4.setText("Sun");
            button_5.setText("Mon");
            button_6.setText("Tue");
            button_7.setText("Wed");
            button_8.setText("Thu");
            button_9.setText("Fri");
            button_10.setText("Sat");
            button_11.setText("Sun");
            button_12.setText("Mon");
            button_13.setText("Tue");
            button_14.setText("Wed");
        }
        if (dayOfWeek.equals("Fri")) {
            button_next.setText("Sat");
            button_3.setText("Sun");
            button_4.setText("Mon");
            button_5.setText("Tue");
            button_6.setText("Wed");
            button_7.setText("Thu");
            button_8.setText("Fri");
            button_9.setText("Sat");
            button_10.setText("Sun");
            button_11.setText("Mon");
            button_12.setText("Tue");
            button_13.setText("Wed");
            button_14.setText("Thu");
        }
        if (dayOfWeek.equals("Sat")) {
            button_next.setText("Sun");
            button_3.setText("Mon");
            button_4.setText("Tue");
            button_5.setText("Wed");
            button_6.setText("Thu");
            button_7.setText("Fri");
            button_8.setText("Sat");
            button_9.setText("Sun");
            button_10.setText("Mon");
            button_11.setText("Tue");
            button_12.setText("Wed");
            button_13.setText("Thu");
            button_14.setText("Fri");
        }
        if (dayOfWeek.equals("Sun")) {
            button_next.setText("Mon");
            button_3.setText("Tue");
            button_4.setText("Wed");
            button_5.setText("Thu");
            button_6.setText("Fri");
            button_7.setText("Sat");
            button_8.setText("Sun");
            button_9.setText("Mon");
            button_10.setText("Tue");
            button_11.setText("Wed");
            button_12.setText("Thu");
            button_13.setText("Fri");
            button_14.setText("Sat");
        }

    }

    @FXML
    // want scroll bar to just scroll button bar, not whole screen
    private void setScrollBar() {
        scroll_bar.setLayoutX(button_bar.getWidth()-scroll_bar.getWidth());

        // add listener to scroll bar
        // x-coordinate of vbox is changed as value of scroll bar is changed
        scroll_bar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                button_bar.setLayoutX(-new_val.doubleValue());
            }
        });
    }

    // try making a scroll pane that just includes the button bar, and use scroll bar in a similar way to video
    // at the moment vertical scroll bar appears but can only scroll up/down with mouse :/
    @FXML
    private void setScrollPane() {
        scroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // add listener to scroll pane
        // x-coordinate of button bar is changed as value of scroll pane is changed
        scroll_pane.hvalueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                scroll_pane.setLayoutX(-new_val.doubleValue());
            }
        });
    }
}