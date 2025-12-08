module com.example.forexconverter {
    requires javafx.controls;

    exports Forex;
    opens Forex to javafx.graphics, javafx.fxml;
}