module org.example.proyekbd {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.proyekbd to javafx.fxml;
    exports org.example.proyekbd;
}