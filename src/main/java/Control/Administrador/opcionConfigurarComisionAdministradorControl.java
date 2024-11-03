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
    private Button btnCargar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnCargar(ActionEvent event) {
        escena.cambiarEscena(cargarComisionAdministrador, stage, "Cargar comision");
    }

    @FXML
    void clickBtnActualizar(ActionEvent event) {

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
