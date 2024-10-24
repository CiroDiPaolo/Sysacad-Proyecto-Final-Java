package Excepciones;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static Path.Path.icono;

public class excepcionPersonalizada {

    public static void excepcion(String mensaje){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Datos Incorrectos");
        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}
