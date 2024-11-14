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
    private Button btnVer;

    @FXML
    private Button btnVolver;

    @FXML
    private Button clickBtnCargar;

    @FXML
    private Button clickBtnEditar;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnEditar(ActionEvent event) {
        escena.cambiarEscena(buscarAvisosAdministrador,stage,"Buscar aviso");
    }

    @FXML
    void clickBtnOp1(ActionEvent event) {
        escena.cambiarEscena(cargarAvisoAdministrador,stage,"Cargar aviso");
    }

    @FXML
    void clickBtnVer(ActionEvent event) {
        escena.cambiarEscena(verAvisosAdministrador,stage,"Ver avisos");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(menuPrincipalAdministrador,stage,"Menú principal");
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });
    }

}