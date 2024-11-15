package Control.Administrador;

import Control.EscenaControl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static Path.Path.*;

public class opcionEditarMesaExamenAdministradorControl {

    @FXML
    private Button btnVolver;

    private Stage stage;


    @FXML
    void clickBtnActualizar(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(buscarMesaExamenAdministrador,stage,"Elegir mesa de examen");
    }

    @FXML
    void clickBtnCargar(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(cargarMesaExamenAdministrador,stage,"Cargar mesa de examen");
    }

    @FXML
    void clickBtnVer(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(verMesaExamenAdministrador,stage,"Ver mesa de examen");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(elegirOpcionInscripcionAdministrador,stage,"Elegir opción");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });
    }

}

