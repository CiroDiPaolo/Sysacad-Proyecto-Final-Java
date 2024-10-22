package Control.Estudiante;

import Control.EscenaControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static Path.Path.menuPrincipalAlumnos;

public class inscripcionCursadaControl {

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    @FXML
    void clickBtnVolver(ActionEvent event) {

        stage = (Stage) btnVolver.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalAlumnos, stage, "Menu Principal");

    }

}
