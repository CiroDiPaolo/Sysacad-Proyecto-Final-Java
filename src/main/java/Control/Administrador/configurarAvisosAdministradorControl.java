package Control.Administrador;

import Control.EscenaControl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static Path.Path.*;

public class configurarAvisosAdministradorControl {

    @FXML
    private Button btnVolver;

    private Stage stage;

    @FXML
    void clickBtnEditar(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(buscarAvisosAdministrador,stage,"Buscar aviso");
    }

    @FXML
    void clickBtnOp1(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(cargarAvisoAdministrador,stage,"Cargar aviso");
    }

    @FXML
    void clickBtnVer(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(verAvisosAdministrador,stage,"Ver avisos");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalAdministrador,stage,"Menú principal");
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });
    }

}