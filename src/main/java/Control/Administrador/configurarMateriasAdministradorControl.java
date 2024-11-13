package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Excepciones.CamposVaciosException;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static Path.Path.*;

public final class configurarMateriasAdministradorControl {

    @FXML
    private Button btnOp1;

    @FXML
    private Button btnOp2;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnVerMateria;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnOp1(ActionEvent event) {
        escena.cambiarEscena(Path.cargarMateriasAdministrador,stage,"Cargar materia");
    }

    @FXML
    void clickBtnOp2(ActionEvent event) {
        escena.cambiarEscena(elegirMateriaAdministrador, stage, "Configurar materias");
    }

    @FXML
    void clickBtnVerMateria(ActionEvent event) {
        escena.cambiarEscena(verMateriaAdministrador,stage,"Ver materia");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(Path.opcionEditarCarreraAdministrador, stage, "Configurar carrera");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnOp1.getScene().getWindow();

        });

    }

}
