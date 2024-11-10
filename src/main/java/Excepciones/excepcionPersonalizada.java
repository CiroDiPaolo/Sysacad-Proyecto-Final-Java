package Excepciones;

import ControlArchivos.manejoArchivos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static Path.Path.icono;

public class excepcionPersonalizada {

    /**
     * Metodo que muestra una alerta de excepcion
     * @param mensaje
     */
    public static void excepcion(String mensaje){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(excepcionPersonalizada.class.getResourceAsStream(icono));
        stage.getIcons().add(icon);

        alert.showAndWait();

    }

    /**
     * Metodo que muestra una alerta de confirmacion
     * @param mensaje
     */
    public static void alertaConfirmacion(String mensaje){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Operación exitosa!");
        alert.setHeaderText("");
        alert.setContentText(mensaje);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(manejoArchivos.class.getResourceAsStream(icono));
        stage.getIcons().add(icon);

        alert.showAndWait();

    }

    public static void alertaAtencion(String mensaje){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("¡Atencion!");
        alert.setHeaderText("");
        alert.setContentText(mensaje);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(manejoArchivos.class.getResourceAsStream(icono));
        stage.getIcons().add(icon);

        alert.showAndWait();

    }

}
