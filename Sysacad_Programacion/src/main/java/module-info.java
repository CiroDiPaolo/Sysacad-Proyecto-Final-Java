module com.example.sysacad_programacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.json;


    opens App to javafx.fxml;
    exports App;
    exports Control;
    opens Control to javafx.fxml;
}