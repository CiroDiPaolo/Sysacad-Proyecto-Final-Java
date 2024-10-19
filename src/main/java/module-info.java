module com.example.sysacad_programacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.json;
    requires java.logging;

    opens App to javafx.fxml;
    exports App;
    exports Control;
    opens Control to javafx.fxml;
    exports Control.Profesores to javafx.fxml;
    exports Control.Estudiante;
    opens Control.Estudiante to javafx.fxml;
    exports Control.Administrador;
    opens Control.Administrador to javafx.fxml;
    exports Control.InicioSesion;
    opens Control.InicioSesion to javafx.fxml;
}