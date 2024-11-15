package Control.Administrador;

import Control.EscenaControl;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import static Path.Path.*;

public class configurarProfesorAdministrador {

    @FXML
    private Button btnVolver;

    private Stage stage;

    @FXML
    void clickBtnCargarProfesor(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.cargaProfesorAdministrador, stage, "Cargar Profesor");

    }

    @FXML
    void clickBtnEditarProfesor(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(buscarProfesorAdministrador,stage,"Editar profesor");
    }

    @FXML
    void clickBtnVerProfesor(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(verProfesorAdministrador,stage,"Ver profesor");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
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
