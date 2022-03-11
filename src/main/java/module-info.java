module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;

    opens org.example to javafx.fxml;
    exports org.example;
}
