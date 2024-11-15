package Control.Profesores;

import Control.EscenaControl;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class gestionarComisionControl {

    @FXML
    private Button btnVolver;


    private Stage stage;

    @FXML
    void clickBtnAviso(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.configurarAvisosProfesor, stage, "Configurar avisos");
    }

    @FXML
    void clickBtnBuscarAlumno(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.buscarAlumnoProfesor, stage, "Buscar alumno");

    }

    @FXML
    void clickBtnGestionarNotas(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.gestionarNotasAlumnosProfesor, stage, "Gestionar notas");

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
