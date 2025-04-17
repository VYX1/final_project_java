module teamc {
    requires javafx.controls;
    requires javafx.fxml;

    opens teamc to javafx.fxml;
    exports teamc;
}
