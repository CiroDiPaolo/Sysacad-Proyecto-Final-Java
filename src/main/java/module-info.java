module com.example.sysacad_programacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.json;
    requires org.apache.poi.ooxml;
    requires java.desktop;


    opens Modelo to javafx.base;
    exports Modelo;
    opens App to javafx.fxml;
    exports App;
    exports Control;
    opens Control to javafx.fxml;
    exports Control.Estudiante;
    opens Control.Estudiante to javafx.fxml;
    exports Control.Administrador;
    opens Control.Administrador to javafx.fxml;
    exports Control.Profesores to javafx.fxml;
    opens Control.Profesores to javafx.fxml;
    exports Control.InicioSesion;
    opens Control.InicioSesion to javafx.fxml;
}