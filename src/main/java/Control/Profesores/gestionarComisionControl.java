package Control.Profesores;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Modelo.Comision;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.HashSet;

public class gestionarComisionControl {

    @FXML
    private Button btnAviso;

    @FXML
    private Button btnGestionarNotas;

    @FXML
    private Button btnVerEstudiantes;

    @FXML
    private Button btnVolver;

    private Stage stage;

    @FXML
    void clickBtnAviso(ActionEvent event) {

    }

    @FXML
    void clickBtnGestionarNotas(ActionEvent event) {

    }

    @FXML
    void clickBtnVerEstudiantes(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.verComisionProfesor, stage, "Ver comision");

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.elegirComisionProfesor, stage, "Elegir comision");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }


}
