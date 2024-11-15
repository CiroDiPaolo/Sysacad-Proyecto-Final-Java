package Control.Administrador;

import Control.EscenaControl;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static Path.Path.buscarAlumnoAdministrador;

public final class configurarAlumnosAdministradorControl {

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    @FXML
    void clickBtnOp1(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.cargarAlumnoAdministrador, stage, "Cargar Alumno");

    }

    @FXML
    void clickBtnOp2(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.busquedaAlumnoAdministrador, stage, "Busqueda Alumno");

    }

    @FXML
    void clickBtnVerAlumno(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(buscarAlumnoAdministrador,stage,"Busqueda alumno");
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.menuPrincipalAdministrador, stage, "Menu Principal");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) tctMenuPrincipal.getScene().getWindow();

        });

    }

}
