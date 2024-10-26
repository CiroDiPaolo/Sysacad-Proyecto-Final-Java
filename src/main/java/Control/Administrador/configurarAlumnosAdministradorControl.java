package Control.Administrador;

import Control.EscenaControl;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public final class configurarAlumnosAdministradorControl {

    @FXML
    private Button btnOp1;

    @FXML
    private Button btnOp2;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnOp1(ActionEvent event) {

        escena.cambiarEscena(Path.cargarAlumnoAdministrador, stage, "Cargar Alumno");

    }

    @FXML
    void clickBtnOp2(ActionEvent event) {

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        escena.cambiarEscena(Path.menuPrincipalAdministrador, stage, "Menu Principal");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) tctMenuPrincipal.getScene().getWindow();

        });

    }

}
