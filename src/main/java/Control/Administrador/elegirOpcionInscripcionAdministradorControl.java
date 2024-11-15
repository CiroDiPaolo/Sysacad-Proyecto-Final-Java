package Control.Administrador;

import Control.EscenaControl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static Path.Path.*;

public class elegirOpcionInscripcionAdministradorControl {

    private Stage stage;

    @FXML
    private Button btnVolver;

    @FXML
    void clickBtnComisiones(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(opcionConfigurarComisionAdministrador,stage,"Opcion comisión");
    }

    @FXML
    void clickBtnMesaExamen(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(opcionEditarMesaExamenAdministrador,stage,"Mesa de examen");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(busquedaCarrera2Administrador,stage,"Buscar carrera");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }
}
