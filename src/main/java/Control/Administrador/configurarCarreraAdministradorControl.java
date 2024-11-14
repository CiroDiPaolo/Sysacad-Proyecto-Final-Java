package Control.Administrador;

import Control.EscenaControl;
import Excepciones.excepcionPersonalizada;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static Path.Path.*;

public class configurarCarreraAdministradorControl {

    @FXML
    private Button btnVolver;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnEditar(ActionEvent event) {
        escena.cambiarEscena(editarCarreraAdministrador,stage,"Editar carrera");
    }

    @FXML
    void clickBtnOp1(ActionEvent event) {
        excepcionPersonalizada.alertaAtencion("NO TIENES EL PAQUETE SYSACAD PRO GOLD.");
    }

    @FXML
    void clickBtnVer(ActionEvent event) {
        escena.cambiarEscena(verCarreraAdministrador,stage,"Ver carrera");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(opcionEditarCarreraAdministrador,stage,"Menú principal");
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });
    }
}
