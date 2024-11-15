package Control.Administrador;

import Control.EscenaControl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static Path.Path.*;

public class opcionConfigurarComisionAdministradorControl {

    @FXML
    private Button btnVolver;

    private Stage stage;

    @FXML
    void clickBtnCargar(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(cargarComisionAdministrador, stage, "Cargar comisión");
    }

    @FXML
    void clickBtnActualizar(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(busquedaComisionAdministrador,stage,"Elegir comisión");
    }

    @FXML
    void clickBtnVer(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(verComisionAdministrador,stage,"Ver comisión");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(elegirOpcionInscripcionAdministrador, stage, "Elegir opción");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }
}
