package Control.Administrador;

import Control.EscenaControl;
import Excepciones.excepcionPersonalizada;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static Path.Path.*;

public class opcionEditarCarreraAdministradorControl {
    @FXML
    private Button btnCarrera;

    @FXML
    private Button btnMaterias;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnCarrera(ActionEvent event) {
        escena.cambiarEscena(configurarCarreraAdministrador,stage,"Configurar carrera");
    }

    @FXML
    void clickBtnMaterias(ActionEvent event) {
        escena.cambiarEscena(configurarMateriasAdministrador, stage, "Configurar materias");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(busquedaCarreraAdministrador, stage, "Configurar carrera");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }
}
