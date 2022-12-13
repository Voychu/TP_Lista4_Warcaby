module com.example.warcabydobre {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.warcabydobre to javafx.fxml;
    exports com.example.warcabydobre;
}