module weather.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens weather.app to javafx.fxml;
    exports weather.app;
}
