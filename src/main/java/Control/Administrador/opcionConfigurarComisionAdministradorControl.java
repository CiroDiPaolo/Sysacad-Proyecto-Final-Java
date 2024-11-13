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
    private Button btnVer;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnCargar(ActionEvent event) {
        escena.cambiarEscena(cargarComisionAdministrador, stage, "Cargar comisión");
    }

    @FXML
    void clickBtnActualizar(ActionEvent event) {
        escena.cambiarEscena(busquedaComisionAdministrador,stage,"Elegir comisión");
    }

    @FXML
    void clickBtnVer(ActionEvent event)
    {
        escena.cambiarEscena(verComisionAdministrador,stage,"Ver comisión");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(elegirOpcionInscripcionAdministrador, stage, "Elegir opción");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }
}
