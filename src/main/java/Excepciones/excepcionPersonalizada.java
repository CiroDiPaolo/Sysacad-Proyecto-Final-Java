package Excepciones;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static Path.Path.icono;

public class excepcionPersonalizada {

    public static void excepcion(String mensaje){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(mensaje);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(excepcionPersonalizada.class.getResourceAsStream(icono));
        stage.getIcons().add(icon);

        alert.showAndWait();

    }

}
