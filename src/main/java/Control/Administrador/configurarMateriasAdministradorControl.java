package Control.Administrador;

import Control.EscenaControl;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static Path.Path.*;

public final class configurarMateriasAdministradorControl {

    @FXML
    private Button btnOp1;

    private Stage stage;

    @FXML
    void clickBtnOp1(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.cargarMateriasAdministrador,stage,"Cargar materia");
    }

    @FXML
    void clickBtnOp2(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(elegirMateriaAdministrador, stage, "Configurar materias");
    }

    @FXML
    void clickBtnVerMateria(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(verMateriaAdministrador,stage,"Ver materia");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.opcionEditarCarreraAdministrador, stage, "Configurar carrera");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnOp1.getScene().getWindow();

        });

    }

}
