module com.example.csc_311_mod_6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.csc_311_mod_6 to javafx.fxml;
    exports com.example.csc_311_mod_6;
}