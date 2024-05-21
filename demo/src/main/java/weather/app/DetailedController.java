package weather.app;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
    private ButtonBar button_bar;
    @FXML
    private ScrollBar scroll_bar;
    @FXML
    private ScrollPane scroll_pane;

    @FXML
    private HBox temperature;
    @FXML
    private HBox rainfall;
    @FXML
    private HBox visibility;
    @FXML
    private HBox snowfall;
    @FXML
    private HBox snowdepth;
    @FXML
    private HBox apparent_temp;
    @FXML
    private HBox freezing_level;

    private DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private DateTimeFormatter resultFormatter = DateTimeFormatter.ofPattern("HH:mm");


    @FXML
    private void initialize() throws IOException {
        labelButtons();
        //setScrollBar();
        setScrollPane();

        sb = new SearchBar(searchBar, favouriteBox);
        screen.requestFocus();

        createTemperatures();
        createRainfall();
        createVisibility();
        createSnowfall();
        createSnowDepths();
        createFreezingLevels();
        createApparentTemps();
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
            screen.requestFocus();
            updateData();
        }
    }

    // day 1 update
    @FXML
    private void updateData() {
        temperature.getChildren().clear();
        rainfall.getChildren().clear();
        visibility.getChildren().clear();
        snowfall.getChildren().clear();
        snowdepth.getChildren().clear();
        freezing_level.getChildren().clear();
        apparent_temp.getChildren().clear();

        createTemperatures();
        createRainfall();
        createVisibility();
        createSnowfall();
        createSnowDepths();
        createApparentTemps();
        createFreezingLevels();
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
        }
        if (dayOfWeek.equals("Tue")) {
            button_next.setText("Wed");
            button_3.setText("Thu");
            button_4.setText("Fri");
            button_5.setText("Sat");
            button_6.setText("Sun");
            button_7.setText("Mon");
        }
        if (dayOfWeek.equals("Wed")) {
            button_next.setText("Thu");
            button_3.setText("Fri");
            button_4.setText("Sat");
            button_5.setText("Sun");
            button_6.setText("Mon");
            button_7.setText("Tue");
        }
        if (dayOfWeek.equals("Thu")) {
            button_next.setText("Fri");
            button_3.setText("Sat");
            button_4.setText("Sun");
            button_5.setText("Mon");
            button_6.setText("Tue");
            button_7.setText("Wed");
        }
        if (dayOfWeek.equals("Fri")) {
            button_next.setText("Sat");
            button_3.setText("Sun");
            button_4.setText("Mon");
            button_5.setText("Tue");
            button_6.setText("Wed");
            button_7.setText("Thu");
        }
        if (dayOfWeek.equals("Sat")) {
            button_next.setText("Sun");
            button_3.setText("Mon");
            button_4.setText("Tue");
            button_5.setText("Wed");
            button_6.setText("Thu");
            button_7.setText("Fri");
        }
        if (dayOfWeek.equals("Sun")) {
            button_next.setText("Mon");
            button_3.setText("Tue");
            button_4.setText("Wed");
            button_5.setText("Thu");
            button_6.setText("Fri");
            button_7.setText("Sat");
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

    @FXML
    private void createTemperatures() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetTemperatures();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("temperature_80m");
        createRow(datas, temperature, units,1, hoursLeft() + 1);
    }

    @FXML
    private void createRainfall() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetPrecipitations();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("precipitation");
        createRow(datas, rainfall, units, 1, hoursLeft() + 1);
    }

    @FXML
    private void createVisibility() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetVisibilities();

        for (int i = 0; i < datas.length; i++) {
            datas[i] = datas[i] / 1000;
        }

        String units = "km";
        createRow(datas, visibility, units, 1, hoursLeft() + 1);
    }

    @FXML
    private void createSnowfall() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowfalls();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snowfall");
        createRow(datas, snowfall, units, 1, hoursLeft() + 1);
    }

    @FXML
    private void createSnowDepths() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowDepths();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snow_depth");
        createRow(datas, snowdepth, units, 1, hoursLeft() + 1);
    }

    @FXML
    private void createFreezingLevels() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetFreezingHeights();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("freezing_level_height");
        createRow(datas, freezing_level, units, 1, hoursLeft() + 1);
    }

    @FXML
    private void createApparentTemps() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetApparentTemps();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("apparent_temperature");
        createRow(datas, apparent_temp, units, 1, hoursLeft() + 1);
    }

    private int hoursLeft() {
        String[] times = WeatherAndLocationManager.CurrentData.GetStringData("time");
        int hoursLeft = 0;
        String time = times[hoursLeft];
        String hour = LocalDateTime.parse(time, dataFormatter).format(DateTimeFormatter.ofPattern("HH"));

        while (!hour.equals("23")) {
            hoursLeft++;
            time = times[hoursLeft];
            hour = LocalDateTime.parse(time, dataFormatter).format(DateTimeFormatter.ofPattern("HH"));
        }

        return hoursLeft;
    }


    private void createRow(float[] datas, HBox parent, String units, int start, int end) {

        String[] times = WeatherAndLocationManager.CurrentData.GetStringData("time");

        for (int i = start; i < end; i++) {
            String time = times[i];
            float data = datas[i];
            String hour = LocalDateTime.parse(time, dataFormatter).format(resultFormatter);

            VBox vbox = new VBox();
            vbox.setPrefSize(100, 100);
            vbox.setSpacing(5);
            vbox.setStyle("-fx-background-color: #DDDDDD; -fx-background-radius: 3px;");
            vbox.setPadding(new Insets(3));

            Text hourText = new Text(hour);
            vbox.setPrefSize(60, 60);
            vbox.setSpacing(5);
            hourText.setTextAlignment(TextAlignment.CENTER);
            hourText.setFont(new Font(12));

            Text valueText = new Text(String.format("%.1f%s", data, units));
            valueText.idProperty().setValue("box");

            vbox.getChildren().addAll(hourText, valueText);
            parent.getChildren().add(vbox);
        }
    }

    // day 2 update
    @FXML
    private void createTemperatures2() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetTemperatures();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("temperature_80m");
        createRow(datas, temperature, units,hoursLeft() + 1, hoursLeft() + 1 + 24);
    }


    @FXML
    private void createRainfall2() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetPrecipitations();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("precipitation");
        createRow(datas, rainfall, units, hoursLeft() + 1, hoursLeft() + 1 + 24);
    }

    @FXML
    private void createVisibility2() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetVisibilities();

        for (int i = 0; i < datas.length; i++) {
            datas[i] = datas[i] / 1000;
        }

        String units = "km";
        createRow(datas, visibility, units, hoursLeft() + 1, hoursLeft() + 1 + 24);
    }

    @FXML
    private void createSnowfall2() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowfalls();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snowfall");
        createRow(datas, snowfall, units, hoursLeft() + 1, hoursLeft() + 1 + 24);
    }

    @FXML
    private void createSnowDepths2() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowDepths();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snow_depth");
        createRow(datas, snowdepth, units, hoursLeft() + 1, hoursLeft() + 1 + 24);
    }

    @FXML
    private void createFreezingLevels2() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetFreezingHeights();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("freezing_level_height");
        createRow(datas, freezing_level, units, hoursLeft() + 1, hoursLeft() + 1 + 24);
    }

    @FXML
    private void createApparentTemps2() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetApparentTemps();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("apparent_temperature");
        createRow(datas, apparent_temp, units, hoursLeft() + 1, hoursLeft() + 1 + 24);
    }

    @FXML
    private void nextDayUpdate() {
        temperature.getChildren().clear();
        rainfall.getChildren().clear();
        visibility.getChildren().clear();
        snowfall.getChildren().clear();
        snowdepth.getChildren().clear();
        freezing_level.getChildren().clear();
        apparent_temp.getChildren().clear();

        createTemperatures2();
        createRainfall2();
        createVisibility2();
        createSnowfall2();
        createSnowDepths2();
        createApparentTemps2();
        createFreezingLevels2();
    }


    // day 3 update
    @FXML
    private void createTemperatures3() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetTemperatures();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("temperature_80m");
        createRow(datas, temperature, units,hoursLeft() + 1 + 24, hoursLeft() + 1 + 2 * 24);
    }

    @FXML
    private void createRainfall3() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetPrecipitations();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("precipitation");
        createRow(datas, rainfall, units, hoursLeft() + 1 + 24, hoursLeft() + 1 + 2 * 24);
    }

    @FXML
    private void createVisibility3() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetVisibilities();

        for (int i = 0; i < datas.length; i++) {
            datas[i] = datas[i] / 1000;
        }

        String units = "km";
        createRow(datas, visibility, units, hoursLeft() + 1 + 24, hoursLeft() + 1 + 2 * 24);
    }

    @FXML
    private void createSnowfall3() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowfalls();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snowfall");
        createRow(datas, snowfall, units, hoursLeft() + 1 + 24, hoursLeft() + 1 + 2 * 24);
    }

    @FXML
    private void createSnowDepths3() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowDepths();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snow_depth");
        createRow(datas, snowdepth, units, hoursLeft() + 1 + 24, hoursLeft() + 1 + 2 * 24);
    }

    @FXML
    private void createFreezingLevels3() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetFreezingHeights();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("freezing_level_height");
        createRow(datas, freezing_level, units, hoursLeft() + 1 + 24, hoursLeft() + 1 + 2 * 24);
    }

    @FXML
    private void createApparentTemps3() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetApparentTemps();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("apparent_temperature");
        createRow(datas, apparent_temp, units, hoursLeft() + 1 + 24, hoursLeft() + 1 + 2 * 24);
    }

    @FXML
    private void day3Update() {
        temperature.getChildren().clear();
        rainfall.getChildren().clear();
        visibility.getChildren().clear();
        snowfall.getChildren().clear();
        snowdepth.getChildren().clear();
        freezing_level.getChildren().clear();
        apparent_temp.getChildren().clear();

        createTemperatures3();
        createRainfall3();
        createVisibility3();
        createSnowfall3();
        createSnowDepths3();
        createApparentTemps3();
        createFreezingLevels3();
    }

    // day 4 update
    @FXML
    private void createTemperatures4() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetTemperatures();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("temperature_80m");
        createRow(datas, temperature, units,hoursLeft() + 1 + 2 * 24, hoursLeft() + 1 + 3 * 24);
    }

    @FXML
    private void createRainfall4() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetPrecipitations();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("precipitation");
        createRow(datas, rainfall, units, hoursLeft() + 1 + 2 * 24, hoursLeft() + 1 + 3 * 24);
    }

    @FXML
    private void createVisibility4() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetVisibilities();

        for (int i = 0; i < datas.length; i++) {
            datas[i] = datas[i] / 1000;
        }

        String units = "km";
        createRow(datas, visibility, units, hoursLeft() + 1 + 2 * 24, hoursLeft() + 1 + 3 * 24);
    }

    @FXML
    private void createSnowfall4() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowfalls();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snowfall");
        createRow(datas, snowfall, units, hoursLeft() + 1 + 2 * 24, hoursLeft() + 1 + 3 * 24);
    }

    @FXML
    private void createSnowDepths4() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowDepths();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snow_depth");
        createRow(datas, snowdepth, units, hoursLeft() + 1 + 2 * 24, hoursLeft() + 1 + 3 * 24);
    }

    @FXML
    private void createFreezingLevels4() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetFreezingHeights();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("freezing_level_height");
        createRow(datas, freezing_level, units, hoursLeft() + 1 + 2 * 24, hoursLeft() + 1 + 3 * 24);
    }

    @FXML
    private void createApparentTemps4() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetApparentTemps();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("apparent_temperature");
        createRow(datas, apparent_temp, units, hoursLeft() + 1 + 2 * 24, hoursLeft() + 1 + 3 * 24);
    }

    @FXML
    private void day4Update() {
        temperature.getChildren().clear();
        rainfall.getChildren().clear();
        visibility.getChildren().clear();
        snowfall.getChildren().clear();
        snowdepth.getChildren().clear();
        freezing_level.getChildren().clear();
        apparent_temp.getChildren().clear();

        createTemperatures4();
        createRainfall4();
        createVisibility4();
        createSnowfall4();
        createSnowDepths4();
        createApparentTemps4();
        createFreezingLevels4();
    }


    // day 5 update
    @FXML
    private void createTemperatures5() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetTemperatures();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("temperature_80m");
        createRow(datas, temperature, units,hoursLeft() + 1 + 3 * 24, hoursLeft() + 1 + 4 * 24);
    }

    @FXML
    private void createRainfall5() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetPrecipitations();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("precipitation");
        createRow(datas, rainfall, units, hoursLeft() + 1 + 3 * 24, hoursLeft() + 1 + 4 * 24);
    }

    @FXML
    private void createVisibility5() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetVisibilities();

        for (int i = 0; i < datas.length; i++) {
            datas[i] = datas[i] / 1000;
        }

        String units = "km";
        createRow(datas, visibility, units, hoursLeft() + 1 + 3 * 24, hoursLeft() + 1 + 4 * 24);
    }

    @FXML
    private void createSnowfall5() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowfalls();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snowfall");
        createRow(datas, snowfall, units, hoursLeft() + 1 + 3 * 24, hoursLeft() + 1 + 4 * 24);
    }

    @FXML
    private void createSnowDepth5() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowDepths();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snow_depth");
        createRow(datas, snowdepth, units, hoursLeft() + 1 + 3 * 24, hoursLeft() + 1 + 4 * 24);
    }

    @FXML
    private void createFreezingLevels5() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetFreezingHeights();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("freezing_level_height");
        createRow(datas, freezing_level, units, hoursLeft() + 1 + 3 * 24, hoursLeft() + 1 + 4 * 24);
    }

    @FXML
    private void createApparentTemps5() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetApparentTemps();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("apparent_temperature");
        createRow(datas, apparent_temp, units, hoursLeft() + 1 + 3 * 24, hoursLeft() + 1 + 4 * 24);
    }

    @FXML
    private void day5Update() {
        temperature.getChildren().clear();
        rainfall.getChildren().clear();
        visibility.getChildren().clear();
        snowfall.getChildren().clear();
        snowdepth.getChildren().clear();
        freezing_level.getChildren().clear();
        apparent_temp.getChildren().clear();

        createTemperatures5();
        createRainfall5();
        createVisibility5();
        createSnowfall5();
        createSnowDepth5();
        createApparentTemps5();
        createFreezingLevels5();
    }


    // day 6 update
    @FXML
    private void createTemperatures6() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetTemperatures();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("temperature_80m");
        createRow(datas, temperature, units,hoursLeft() + 1 + 4 * 24, hoursLeft() + 1 + 5 * 24);
    }

    @FXML
    private void createRainfall6() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetPrecipitations();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("precipitation");
        createRow(datas, rainfall, units, hoursLeft() + 1 + 4 * 24, hoursLeft() + 1 + 5 * 24);
    }

    @FXML
    private void createVisibility6() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetVisibilities();

        for (int i = 0; i < datas.length; i++) {
            datas[i] = datas[i] / 1000;
        }

        String units = "km";
        createRow(datas, visibility, units, hoursLeft() + 1 + 4 * 24, hoursLeft() + 1 + 5 * 24);
    }

    @FXML
    private void createSnowfall6() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowfalls();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snowfall");
        createRow(datas, snowfall, units, hoursLeft() + 1 + 4 * 24, hoursLeft() + 1 + 5 * 24);
    }

    @FXML
    private void createSnowDepths6() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowDepths();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snow_depth");
        createRow(datas, snowdepth, units, hoursLeft() + 1 + 4 * 24, hoursLeft() + 1 + 5 * 24);
    }

    @FXML
    private void createFreezingLevels6() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetFreezingHeights();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("freezing_level_height");
        createRow(datas, freezing_level, units, hoursLeft() + 1 + 4 * 24, hoursLeft() + 1 + 5 * 24);
    }

    @FXML
    private void createApparentTemps6() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetApparentTemps();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("apparent_temperature");
        createRow(datas, apparent_temp, units, hoursLeft() + 1 + 4 * 24, hoursLeft() + 1 + 5 * 24);
    }

    @FXML
    private void day6Update() {
        temperature.getChildren().clear();
        rainfall.getChildren().clear();
        visibility.getChildren().clear();
        snowfall.getChildren().clear();
        snowdepth.getChildren().clear();
        freezing_level.getChildren().clear();
        apparent_temp.getChildren().clear();

        createTemperatures6();
        createRainfall6();
        createVisibility6();
        createSnowfall6();
        createSnowDepths6();
        createApparentTemps6();
        createFreezingLevels6();
    }


    // day 7 update
    @FXML
    private void createTemperatures7() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetTemperatures();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("temperature_80m");
        createRow(datas, temperature, units,hoursLeft() + 1 + 5 * 24, hoursLeft() + 1 + 6 * 24);
    }

    @FXML
    private void createRainfall7() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetPrecipitations();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("precipitation");
        createRow(datas, rainfall, units, hoursLeft() + 1 + 5 * 24, hoursLeft() + 1 + 6 * 24);
    }

    @FXML
    private void createVisibility7() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetVisibilities();

        for (int i = 0; i < datas.length; i++) {
            datas[i] = datas[i] / 1000;
        }

        String units = "km";
        createRow(datas, visibility, units, hoursLeft() + 1 + 5 * 24, hoursLeft() + 1 + 6 * 24);
    }

    @FXML
    private void createSnowfall7() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowfalls();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snowfall");
        createRow(datas, snowfall, units, hoursLeft() + 1 + 5 * 24, hoursLeft() + 1 + 6 * 24);
    }

    @FXML
    private void createSnowDepths7() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetSnowDepths();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("snow_depth");
        createRow(datas, snowdepth, units, hoursLeft() + 1 + 5 * 24, hoursLeft() + 1 + 6 * 24);
    }

    @FXML
    private void createFreezingLevels7() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetFreezingHeights();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("freezing_level_height");
        createRow(datas, freezing_level, units, hoursLeft() + 1 + 5 * 24, hoursLeft() + 1 + 6 * 24);
    }

    @FXML
    private void createApparentTemps7() {
        float[] datas = WeatherAndLocationManager.CurrentData.GetApparentTemps();
        String units = WeatherAndLocationManager.CurrentData.JSON.getJSONObject("hourly_units").getString("apparent_temperature");
        createRow(datas, apparent_temp, units, hoursLeft() + 1 + 5 * 24, hoursLeft() + 1 + 6 * 24);
    }

    @FXML
    private void day7Update() {
        temperature.getChildren().clear();
        rainfall.getChildren().clear();
        visibility.getChildren().clear();
        snowfall.getChildren().clear();
        snowdepth.getChildren().clear();
        freezing_level.getChildren().clear();
        apparent_temp.getChildren().clear();

        createTemperatures7();
        createRainfall7();
        createVisibility7();
        createSnowfall7();
        createSnowDepths7();
        createApparentTemps7();
        createFreezingLevels7();
    }

}