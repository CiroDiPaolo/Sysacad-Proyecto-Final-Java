package Control;

import Path.Path;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import domain.inicioSesion;
import javafx.stage.Stage;

public class inicioSesionController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btnAcceder;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtLegajo;

    @FXML
    public void clicBtnAcceder(ActionEvent event) {

        //Si el inicion de sesion es correcto, cambiar de escena
        if (inicioSesionLegajo() && inicioSesionContrasenia()) {//Verifica si el legajo y la contraseña son correctos

            System.out.println("correcto");

            // Obtener el Stage actual
            Stage stage = (Stage) btnAcceder.getScene().getWindow();

            // Cambiar la escena
            EscenaControl escenaControl = new EscenaControl();

            escenaControl.cambiarEscena("/App/menuPrincipal.fxml", stage);

        } else {

            System.out.println("incorrecto");

        }

    }

    @FXML
    boolean inicioSesionContrasenia() {//Verifica si la contraseña es correcta, si es correcta retorna true

        boolean flag = false;

        //Busca en el JSON si se encuentra la contasenia y si es correcta
        if (inicioSesion.buscarClave(Path.fileNameAlumnos, txtContrasenia.getText(), "contrasenia") == true) {

            flag = true;

        }

        return flag;
    }

    @FXML
    boolean inicioSesionLegajo() {//Verifica si el legajo es correcto, si es correcto retorna true

        boolean flag = false;

        //Busca en el JSON si se encuentra el legajo
        if (inicioSesion.buscarClave(Path.fileNameAlumnos, txtLegajo.getText(), "legajo") == true) {

                flag = true;

        }


        return flag;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}