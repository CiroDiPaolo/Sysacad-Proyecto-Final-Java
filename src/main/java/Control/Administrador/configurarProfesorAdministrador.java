package Control.Administrador;

import Control.EscenaControl;
import ControlArchivos.manejoArchivosCarrera;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

import static Path.Path.pathCarreras;

public class configurarProfesorAdministrador {

    @FXML
    private Button btnCargarProfesor;

    @FXML
    private Button btnEditarProfesor;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnCargarProfesor(ActionEvent event) {

        escena.cambiarEscena(Path.cargaProfesorAdministrador, stage, "Cargar Profesor");

    }

    @FXML
    void clickBtnEditarProfesor(ActionEvent event) {

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        stage = (Stage) btnVolver.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.menuPrincipalAdministrador, stage, "Menu Principal");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }

}
