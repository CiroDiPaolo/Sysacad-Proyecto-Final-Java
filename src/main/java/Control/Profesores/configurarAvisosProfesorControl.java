package Control.Profesores;

import Control.EscenaControl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static Path.Path.*;

public class configurarAvisosProfesorControl {

    @FXML
    private Button btnVolver;

    private Stage stage;

    @FXML
    void clickBtnEditar(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(buscarAvisosProfesor,stage,"Buscar aviso");
    }

    @FXML
    void clickBtnOp1(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(cargarAvisosProfesor,stage,"Cargar aviso");
    }

    @FXML
    void clickBtnVer(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(verAvisosProfesor,stage,"Ver avisos");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(gestionarComisionProfesor,stage,"Gestionar Comision");
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });
    }
}
