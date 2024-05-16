module weather.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens weather.app to javafx.fxml;
    exports weather.app;
}
