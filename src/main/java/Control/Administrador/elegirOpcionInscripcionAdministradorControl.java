package Control.Administrador;

import Control.EscenaControl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static Path.Path.*;

public class elegirOpcionInscripcionAdministradorControl {

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    private Button btnComisiones;

    @FXML
    private Button btnMesaExamen;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    void clickBtnComisiones(ActionEvent event) {
        escena.cambiarEscena(opcionConfigurarComisionAdministrador,stage,"Opcion comisión");
    }

    @FXML
    void clickBtnMesaExamen(ActionEvent event) {
        escena.cambiarEscena(opcionEditarMesaExamenAdministrador,stage,"Mesa de examen");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(busquedaCarrera2Administrador,stage,"Buscar carrera");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }
}
